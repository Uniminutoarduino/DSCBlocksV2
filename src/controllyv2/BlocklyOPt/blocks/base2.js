/**
 * @license
 * Visual Blocks Editor
 *
 * Copyright 2012 Fred Lin.
 * https://github.com/gasolin/BlocklyDuino
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
 * @fileoverview Helper functions for generating Arduino blocks.
 * @author gasolin@gmail.com (Fred Lin)
 */
'use strict';

//To support syntax defined in http://arduino.cc/en/Reference/HomePage

goog.provide('Blockly.Blocks.base2');

goog.require('Blockly.Blocks');


Blockly.Blocks['osc'] = {//Bloque de oscilador
  init: function() {
    this.appendDummyInput()
        .appendField("Oscillator configuration");
    this.appendValueInput("M")
        .setCheck("Number")
        .appendField("M value ");
    this.appendValueInput("N1")
        .setCheck("Number")
        .appendField("N1 value");
    this.appendValueInput("N2")
        .setCheck(null)
        .appendField("N2 value ");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(150);
    this.setTooltip('Oscillator configuration');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['negate_output'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("Negate Output")
        .appendField(new Blockly.FieldDropdown([["LED1", "LED1"], ["LED2", "LED2"], ["1", "0"], ["2", "1"], ["3", "2"], ["4", "3"]]), "Pin");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['salidaled_alto_bajo'] = {//Salida del sistema tipo LED
  init: function() {
    this.setColour(230);
    this.appendDummyInput()
	    .appendField("Led board")
        .appendField(new Blockly.FieldDropdown([["LED1", "LED1"], ["LED2", "LED2"]]),"Leds");
	this.appendDummyInput()
		.appendField("Estado")
		.appendField(new Blockly.FieldDropdown([["HIGH", "1"], ["LOW", "0"]]),"Estado");
		this.setPreviousStatement(true, null);
     this.setNextStatement(true, null);
    this.setTooltip('Put LED1 or LED2 of the Board in 1 or 0');
  }
};

Blockly.Blocks['salidaalto'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("High Pin")
        .appendField(new Blockly.FieldDropdown([["1", "0"], ["2", "1"], ["3", "2"], ["4", "3"]]), "Pin");
    this.setInputsInline(false);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(210);
    this.setTooltip('Put pin in logic 1');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['leerpuerto'] = {//Funcion para leer puerto
  init: function() {
    this.appendDummyInput()
        .appendField("Readpin")
        .appendField(new Blockly.FieldDropdown([["1", "0"], ["2", "1"], ["3", "2"], ["4", "3"], ["5", "4"], ["6", "5"]]), "Pin");
    this.setInputsInline(false);
    this.setOutput(true, null);
    this.setColour(120);
    this.setTooltip('Read pin selected');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['salidabajo'] = {
  init: function() {
     this.appendDummyInput()
        .appendField("Low Pin")
        .appendField(new Blockly.FieldDropdown([["1", "0"], ["2", "1"], ["3", "2"], ["4", "3"]]), "Pin");
    this.setInputsInline(false);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(210);
    this.setTooltip('Put pin in logic 0');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['delay'] = {//Block de delay milisegundos
  init: function() {
    this.appendValueInput("Time")
        .setCheck("Number")
        .appendField("Delay(ms)");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('Delay in milliseconds');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['delayusb'] = {//Block de delay microseconds
  init: function() {
    this.appendValueInput("Time")
        .setCheck("Number")
        .appendField("Delay(us)");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('Delay in microseconds');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['nop'] = {//Block de NOP operacion
  init: function() {
    this.appendDummyInput()
         .appendField("NOP");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('NOP operation');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['cycledelay'] = {//Block de NOP operacion
  init: function() {
    this.appendValueInput("Cycle")
        .setCheck("Number")
        .appendField("Delay(Cycle)");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('Delay in instruction cycles');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['toggle'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("LED")
        .appendField(new Blockly.FieldDropdown([["LED1", "LED1"], ["LED2", "LED2"]]), "Pin");
    this.appendValueInput("Osc")
        .setCheck("Number")
        .appendField("Time osc (ms)");
    this.setInputsInline(false);
    this.setColour(195);
    this.setTooltip('Builtin LED with oscillator with frequency defined by you');
	this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['adcb'] = {//ADC block
  init: function() {
    this.appendValueInput("ADCchannel")
        .setCheck("Number")
        .appendField("ADC Single(Channel)");
    this.setInputsInline(false);
	this.setColour(100);
    this.setOutput(true, "Number");
    this.setTooltip('ADC Read one channel');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['pwmotuputb'] = {//PWM block
  init: function() {
    this.appendDummyInput()
        .appendField("PWM output");
    this.appendValueInput("Dutty")
        .setCheck("Number")
        .appendField("Duty cycle");
    this.appendValueInput("Freq")
        .appendField("Frecuency (Hz)");
	this.appendDummyInput()
	    .appendField("Preescale")
        .appendField(new Blockly.FieldDropdown([["1", "1"], ["8", "8"], ["64", "64"],["256", "256"]]), "Prescale");
    this.appendDummyInput()
	    .appendField("PWM output")
        .appendField(new Blockly.FieldDropdown([["PWM1", "1"], ["PWM2", "2"], ["PWM3", "3"], ["PWM4", "4"]]), "PWM");
    this.setInputsInline(false);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(230);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};


Blockly.Blocks['spiwritedac'] = {//SPI DAC block, este escribe el valor analogo hacia la salida
  init: function() {
    this.appendDummyInput()
        .appendField("DAC Channel (A,B)")
        .appendField(new Blockly.FieldDropdown([["A", "0"], ["B", "1"]]), "channel");
    this.appendValueInput("input")
        .setCheck(null)
        .appendField("SPI DAC write value");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(20);
    this.setTooltip('This block writes an analog value on MCP4822');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['adcmultiple'] = {//Block ADC Multiple channels
  init: function() {
    this.appendDummyInput()
        .appendField("ADC multiple (channels)")
        .appendField(new Blockly.FieldDropdown([["2", "2"], ["3", "3"], ["4", "4"], ["5", "5"], ["6", "6"]]), "channels");
    //this.appendValueInput("samples")
        //.setCheck("Number")
        //.appendField("Samples number (Max 16)");
    this.appendValueInput("time")
        .setCheck("Number")
        .appendField("Time between conversions (ms)");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(0);
    this.setTooltip('ADC for multiple read simultaneously');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['ostaskcreate'] = {//Block de creacion de tarea para RTOS
  init: function() {
    this.appendValueInput("Prior")
        .setCheck("Number")
        .appendField("Priority");
    this.appendValueInput("Nombre")
        .setCheck("String")
        .appendField("Task name");
    this.appendStatementInput("Task")
        .setCheck(null)
        .appendField("OSTaskCreate");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('OS task for RTOS');
    this.setColour(210);
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['os_init'] = {//OS Init
  init: function() {
    this.appendDummyInput()
        .appendField("OS_init()");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('Os init function');
    this.setColour(210);
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['os_run'] = {//Os_Run
  init: function() {
    this.appendDummyInput()
        .appendField("OS_Run()");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('Os Run function');
    this.setColour(210);
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['os_timer'] = {//Os Timer
  init: function() {
    this.appendDummyInput()
        .appendField("OS_Timer()");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('OS Timer function');
    this.setColour(210);
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['os_yield'] = {//funccion OS_Yield que libera RTOS
  init: function() {
    this.appendDummyInput()
        .appendField("OS_Yield()");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('OS Yield function for RTOS');
    this.setColour(210);
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['os_delay'] = {//Delay in cycles para RTOS
  init: function() {
    this.appendDummyInput()
        .appendField("OS_Delay()");
    this.appendValueInput("Tick")
        .setCheck("Number")
        .appendField("delay in ticks (cycles)");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('Os Delay in cycles');
    this.setColour(210);
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['timerinterrupt'] = {//Bloque de interrupcion para timers
  init: function() {
    this.appendDummyInput()
        .appendField("Timer interrupt (timer)")
        .appendField(new Blockly.FieldDropdown([["1", "1"], ["2", "2"], ["3", "3"], ["4", "4"], ["5", "5"]]), "timer");
    this.appendDummyInput()
        .appendField("Preescale")
        .appendField(new Blockly.FieldDropdown([["1", "1"], ["8", "8"], ["64", "64"], ["256", "256"]]), "preescale");
    this.appendValueInput("timeinterrupt")
        .setCheck("Number")
        .appendField("Time interrupt (ms)");
	this.appendDummyInput()
        .appendField("Priority (0...7)")
        .appendField(new Blockly.FieldDropdown([["0", "0"],["1", "1"], ["2", "2"], ["3", "3"], ["4", "4"], ["5", "5"], ["6", "6"], ["7", "7"]]), "priority");
    this.appendStatementInput("bucle")
        .setCheck(null);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('Timer interruption');
    this.setColour(200);
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['alldig'] = {//Funcion para todos los pines digitales
  init: function() {
    this.appendDummyInput()
        .appendField("All Pin Digital (ADPCFG = 0xffff)");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setTooltip('All digital pin');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['uart'] = {//Funtion sends integer to UART
  init: function() {
    this.appendValueInput("VarUart")
        .setCheck(null)
        .appendField("Uart[1] value to send(integer)");
    this.setInputsInline(false);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(165);
    this.setTooltip('UART value to send to app');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['uarttext'] = {//Funtion sends character of text
  init: function() {
    this.appendValueInput("Text")
        .setCheck("String")
        .appendField("Uart(1) write text");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(330);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['uartfloat'] = {//Funtion sends float to UART
  init: function() {
    this.appendValueInput("Text")
        .setCheck("String")
        .appendField("Uart(1) write float");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(100);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['readuart'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("ReadUart()");
    this.setOutput(true, null);
    this.setTooltip('Read UART block');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['uartready'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("DataRdyUART1()");
    this.setOutput(true, "");
    this.setTooltip('Read UART block');
    this.setHelpUrl('http://www.example.com/');
  }
};
