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
package com.effektif.workflow.api.form;

import java.util.List;


/**
 * A form for collecting data in a {@link com.effektif.workflow.api.activities.UserTask} or
 * {@link com.effektif.workflow.api.triggers.FormTrigger}, used to construct an user-interface.
 *
 * @author Tom Baeyens
 */
public class Form {

  protected String description;
  protected List<String> buttons;
  protected List<FormField> fields;

  public List<FormField> getFields() {
    return this.fields;
  }
  public void setFields(List<FormField> fields) {
    this.fields = fields;
  }
  public Form fields(List<FormField> fields) {
    this.fields = fields;
    return this;
  }

  public List<String> getButtons() {
    return this.buttons;
  }
  public void setButtons(List<String> buttons) {
    this.buttons = buttons;
  }
  public Form buttons(List<String> buttons) {
    this.buttons = buttons;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Form description(String description) {
    this.description = description;
    return this;
  }
}