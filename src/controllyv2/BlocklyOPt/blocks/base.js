/**
 * @license
 * Visual Blocks Editor
 *
 * Copyright 2012 Google Inc.
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
 * @fileoverview Text blocks for Blockly.
 * @author fraser@google.com (Neil Fraser)
 */
'use strict';

goog.provide('Blockly.Blocks.base');

goog.require('Blockly.Blocks');


/**
 * Common HSV hue for all blocks in this category.
 */
Blockly.Blocks['delay'] = {//Block de delay
  init: function() {
    this.appendValueInput("Time")
        .setCheck("Number")
        .appendField("Delay(ms)");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['delayus'] = {//Block de delayus
  init: function() {
    this.appendValueInput("Time")
        .setCheck("Number")
        .appendField("Delay(us)");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['high_pin'] = {//High Pin
  init: function() {
    this.appendDummyInput()
        .appendField("High Pin")
        .appendField("Port")
        .appendField(new Blockly.FieldDropdown([["A", "A"], ["B", "B"], ["C", "C"], ["D", "D"]]), "Port");
    this.appendDummyInput()
        .appendField("         ")
        .appendField("# of pin")
        .appendField(new Blockly.FieldDropdown([["0", "0"], ["1", "1"], ["2", "2"], ["3", "3"], ["4", "4"], ["5", "5"], ["6", "6"], ["7", "7"]]), "Pin");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(330);
    this.setTooltip('High specified pin');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['low_pin'] = {//Low Pin
  init: function() {
    this.appendDummyInput()
        .appendField("Low Pin")
        .appendField("Port")
        .appendField(new Blockly.FieldDropdown([["A", "A"], ["B", "B"], ["C", "C"], ["D", "D"]]), "Port");
    this.appendDummyInput()
        .appendField("         ")
        .appendField("# of pin")
        .appendField(new Blockly.FieldDropdown([["0", "0"], ["1", "1"], ["2", "2"], ["3", "3"], ["4", "4"], ["5", "5"], ["6", "6"], ["7", "7"]]), "Pin");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(45);
    this.setTooltip('Low specified pin');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['readpinon'] = {//Read pin state 1, that is, whe the pin is 1 the pin is activate, the parameters are PORT and PIN number.
  init: function() {
    this.appendDummyInput()
        .appendField("Read Pin (On state)");
    this.appendDummyInput()
        .appendField("Port")
        .appendField(new Blockly.FieldDropdown([["PORTA", "A"], ["PORTB", "B"], ["PORTC", "C"], ["PORTD", "D"]]), "Port");
    this.appendValueInput("pin")
        .setCheck("Number")
        .appendField("Pin #");
    this.setOutput(true, null);
    this.setColour(135);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['readpinoff'] = {//Read pin state 0, that is, whe the pin is 1 the pin is activate, the parameters are PORT and PIN number.
  init: function() {
    this.appendDummyInput()
        .appendField("Read Pin (Off state)");
    this.appendDummyInput()
        .appendField("Port")
        .appendField(new Blockly.FieldDropdown([["PORTA", "A"], ["PORTB", "B"], ["PORTC", "C"], ["PORTD", "D"]]), "Port");
    this.appendValueInput("pin")
        .setCheck("Number")
        .appendField("Pin #");
    this.setOutput(true, null);
    this.setColour(180);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['writeport'] = {//escribir puerto
  init: function() {
    this.appendDummyInput()
        .appendField("Write PORT");
    this.appendDummyInput()
        .appendField("Port")
        .appendField(new Blockly.FieldDropdown([["PORTA", "A"], ["PORTB", "B"], ["PORTC", "C"], ["PORTD", "D"]]), "Port");
    this.appendValueInput("val")
        .setCheck("Number")
        .appendField("Value");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(150);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['configport'] = {//Configurar puerto
  init: function() {
    this.appendDummyInput()
        .appendField("Configure PORT");
    this.appendDummyInput()
        .appendField("Port")
        .appendField(new Blockly.FieldDropdown([["PORTA", "A"], ["PORTB", "B"], ["PORTC", "C"], ["PORTD", "D"]]), "Port");
    this.appendValueInput("val")
        .setCheck("Number")
        .appendField("Value");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(255);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['usarttext'] = {//Enviar texto a uart
  init: function() {
    this.appendValueInput("texto")
        .setCheck("String")
        .appendField("USART sent text");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(65);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['usartfloat'] = {//Enviar flotante
  init: function() {
    this.appendValueInput("texto")
        .setCheck("Number")
        .appendField("USART float send");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(345);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['usartinteger'] = {//Enviar entero a UART
  init: function() {
    this.appendValueInput("texto")
        .setCheck("Number")
        .appendField("USART integer send");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(270);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['adc'] = {//ADC
  init: function() {
    this.appendValueInput("channel")
        .setCheck("Number")
        .appendField("ADC channel");
    this.setOutput(true, "Number");
    this.setColour(245);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};







