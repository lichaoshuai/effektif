/*
 * Copyright 2014 Effektif GmbH.
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
 * limitations under the License.
 */
package com.effektif.adapter.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.effektif.adapter.ActivityAdapter;
import com.effektif.adapter.ActivityContext;
import com.effektif.workflow.impl.activity.ActivityDescriptor;


public class HelloWorldActivityAdapter implements ActivityAdapter {
  
  private static final Logger log = LoggerFactory.getLogger(HelloWorldActivityAdapter.class);

  public static final String NAME = "name";
  public static final String GREETING = "greeting";

  @Override
  public ActivityDescriptor getDescriptor() {
    return new ActivityDescriptor()
      .key("hello")
      .inputParameterString(NAME, "Name")
      .outputDescriptorString(GREETING, "Message");
  }

  @Override
  public void execute(ActivityContext activityContext) {
    String name = (String) activityContext.getInputParameterValue(NAME);
    log.debug("NAME: "+name);
    
    activityContext.setOutputParameterValue(GREETING, "Hi, "+name);
    activityContext.onwards();
  }
}
