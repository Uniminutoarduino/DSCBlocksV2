/**
 * @license
 * Visual Blocks Language
 *
 * Copyright 2014 Google Inc.
 * https://developers.google.com/blockly/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @fileoverview Generating Dart for variable blocks.
 * @author fraser@google.com (Neil Fraser)
 */
'use strict';

goog.provide('Blockly.Dart.variables');

goog.require('Blockly.Dart');


Blockly.Dart['variables_get'] = function(block) {
  // Variable getter.
  var code = Blockly.Dart.variableDB_.getName(block.getFieldValue('VAR'),
      Blockly.Variables.NAME_TYPE);
  return [code, Blockly.Dart.ORDER_ATOMIC];
};

Blockly.Dart.variables_declare = function() {
  // Variable setter.
  var dropdown_type = this.getFieldValue('TYPE');
  //TODO: settype to variable
  var argument0 = Blockly.Dart.valueToCode(this, 'VALUE',
      Blockly.Dart.ORDER_ASSIGNMENT) || '0';
  var varName = Blockly.Dart.variableDB_.getName(this.getFieldValue('VAR'), //getName
      Blockly.Variables.NAME_TYPE);
   if(dropdown_type=="int"){	  //Preguntar por el tipo de variable.
  Blockly.Dart.variableType_['setup_var' + varName]='int';
  Blockly.Dart.variables_['setup_var' + varName] ='int '+varName + ' = ' + argument0 + ';\n'; //int declare
  }else if(dropdown_type=="float"){
	  Blockly.Dart.variableType_['setup_var' + varName]='float';
  Blockly.Dart.variables_['setup_var' + varName] ='float '+varName + ' = ' + argument0 + ';\n'; //float declare
  }else{
  Blockly.Dart.variableType_['setup_var' + varName]='char';
  Blockly.Dart.variables_['setup_var' + varName] ='char '+varName + ' = ' + argument0 + ';\n'; //char declare
  }
  return '';
};

Blockly.Dart['variables_set'] = function(block) {
  // Variable setter.
  var argument0 = Blockly.Dart.valueToCode(block, 'VALUE',
      Blockly.Dart.ORDER_ASSIGNMENT) || '0';
  var varName = Blockly.Dart.variableDB_.getName(block.getFieldValue('VAR'),
      Blockly.Variables.NAME_TYPE);
  return varName + ' = ' + argument0 + ';\n';
};
