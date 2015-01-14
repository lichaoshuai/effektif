/* Copyright 2014 Effektif GmbH.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. */
package com.effektif.workflow.impl.activitytypes;

import java.util.ArrayList;
import java.util.List;

import com.effektif.workflow.api.activities.ParallelGateway;
import com.effektif.workflow.api.workflow.Activity;
import com.effektif.workflow.api.workflow.Transition;
import com.effektif.workflow.impl.plugin.AbstractActivityType;
import com.effektif.workflow.impl.workflow.ActivityImpl;
import com.effektif.workflow.impl.workflow.WorkflowParser;
import com.effektif.workflow.impl.workflowinstance.ActivityInstanceImpl;


public class ParallelGatewayImpl extends AbstractActivityType<ParallelGateway> {
  
  int nbrOfIncomingTransitions = -1;
  boolean hasOutgoingTransitions = false;
  
  public ParallelGatewayImpl() {
    super(ParallelGateway.class);
  }
  
  @Override
  public void parse(ActivityImpl activityImpl, Activity activityApi, WorkflowParser validator) {
    // at least one in, at least one out
    List<Transition> incomingTransitions = activityImpl.getIncomingTransitions();
    if (incomingTransitions==null || incomingTransitions.isEmpty()) {
      validator.addWarning("Parallel gateway '%s' does not have incoming transitions", activityImpl.id);
    } else {
      nbrOfIncomingTransitions = incomingTransitions.size();
    }
    List<Transition> outgoingTransitions = activityImpl.getOutgoingTransitions();
    if (outgoingTransitions==null || outgoingTransitions.isEmpty()) {
      validator.addWarning("Parallel gateway '%s' does not have outgoing transitions", activityImpl.id);
    } else {
      hasOutgoingTransitions = true;
    }
  }

  @Override
  public void execute(ActivityInstanceImpl activityInstance) {
    activityInstance.end();
    boolean hasOtherUnfinishedActivities = false;

    List<ActivityInstanceImpl> otherJoiningActivityInstances = new ArrayList<>();
    for (ActivityInstanceImpl siblingActivityInstance: activityInstance.parent.activityInstances) {
      if (!siblingActivityInstance.isEnded()) {
        hasOtherUnfinishedActivities = true;
      }
      
      if ( siblingActivityInstance!=activityInstance
           && siblingActivityInstance.getActivity()==activityInstance.getActivity()
           && activityInstance.isJoining(siblingActivityInstance) ) {
        otherJoiningActivityInstances.add(siblingActivityInstance);
      }
    }
    
    if ( hasOutgoingTransitions
         && ( otherJoiningActivityInstances.size()==(nbrOfIncomingTransitions-1)
              || !hasOtherUnfinishedActivities
            )
       ) {
      if (log.isDebugEnabled())
        log.debug("Firing parallel gateway");
      for (ActivityInstanceImpl otherJoiningActivityInstance: otherJoiningActivityInstances) {
        activityInstance.removeJoining(otherJoiningActivityInstance);
      }
      activityInstance.onwards();
    } else {
      activityInstance.setJoining();
    }
  }

  @Override
  public void message(ActivityInstanceImpl activityInstance) {
  }

  @Override
  public void ended(ActivityInstanceImpl activityInstance, ActivityInstanceImpl nestedEndedActivityInstance) {
  }
}