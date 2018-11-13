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
 * @fileoverview Generating Dart for text blocks.
 * @author fraser@google.com (Neil Fraser)
 */
'use strict';

goog.provide('Blockly.Dart.base');

goog.require('Blockly.Dart');


Blockly.Dart['delay'] = function() {//Esta funcion genera un delay variable 
  //ver la formula...
 // var stringb=unescape("#include <avr/delay.h>")
 // Blockly.Dart.definitions_['DelayDef'] ='\u200F<\u200F\n';
  //Blockly.Dart.definitions_['DelayDef'] ='#include &lt;avr/io.h&gt;\n'; //Colocar &lt y &gt porque esta versión de Blockly no distingue entre <>
  //Blockly.Dart.definitions_['DelayDef'] ='#include &lt;avr/delay.h&gt;\n';
   Blockly.Dart.definitions_['DelayDefT'] ='#define F_CPU 16000000UL\n';
   Blockly.Dart.definitions_['DelayDef'] ='#include &lt;util/delay.h&gt;\n';
  var OSCVal = Blockly.Dart.valueToCode(this, 'Time', Blockly.Dart.ORDER_ATOMIC) || '1000' //Valor de oscilacion asignado
  //OSCVal=OSCVal*40000;//Operacion de generacion de tiempo de acuerdo a frecuencia establecida.
  var code = '_delay_ms('+OSCVal+'); // '+OSCVal+'ms.\n'
  //Blockly.Arduino.definitions_['define_profileH'] = '#include <HProfile.h>\n';
  return code;
};

Blockly.Dart['delayus'] = function() {//Esta funcion genera un delay variable de microsegundos. 
  //ver la formula...
 // var stringb=unescape("#include <avr/delay.h>")
 // Blockly.Dart.definitions_['DelayDef'] ='\u200F<\u200F\n';
  //Blockly.Dart.definitions_['DelayDef'] ='#include &lt;avr/io.h&gt;\n'; //Colocar &lt y &gt porque esta versión de Blockly no distingue entre <>
  //Blockly.Dart.definitions_['DelayDef'] ='#include &lt;avr/delay.h&gt;\n';
   Blockly.Dart.definitions_['DelayDefT'] ='#define F_CPU 16000000UL\n';
   Blockly.Dart.definitions_['DelayDef'] ='#include &lt;util/delay.h&gt;\n';
  var OSCVal = Blockly.Dart.valueToCode(this, 'Time', Blockly.Dart.ORDER_ATOMIC) || '1000' //Valor de oscilacion asignado
  //OSCVal=OSCVal*40000;//Operacion de generacion de tiempo de acuerdo a frecuencia establecida.
  var code = '_delay_us('+OSCVal+'); // '+OSCVal+'us.\n'
  //Blockly.Arduino.definitions_['define_profileH'] = '#include <HProfile.h>\n';
  return code;
};

Blockly.Dart['high_pin'] = function(block) {//Esta funcion genera la escritura de un 1 sobre un determinado pin.
  var dropdown_port = block.getFieldValue('Port');
  var dropdown_pin = block.getFieldValue('Pin');
  // TODO: Assemble Dart into code variable.
  //var code = 'DDR'+dropdown_port+'=1<<P'+dropdown_port+dropdown_pin';\n'
  Blockly.Dart.Config_['IOPorts']='DDR'+dropdown_port+'=1<&lt;P'+dropdown_port+dropdown_pin+';\n';
  Blockly.Dart.definitions_['IOPorts'] ='#include &lt;avr/io.h&gt;\n';
  var code = 'PORT'+dropdown_port+'=1<&lt;P'+dropdown_port+dropdown_pin+';\n';
  return code;
};

Blockly.Dart['low_pin'] = function(block) {//Esta funcion genera la escritura de un 1 sobre un determinado pin.
  var dropdown_port = block.getFieldValue('Port');
  var dropdown_pin = block.getFieldValue('Pin');
  // TODO: Assemble Dart into code variable.
 // var code = 'DDR'+dropdown_port+'=1<<P'+dropdown_port+dropdown_pin';\n'
  Blockly.Dart.definitions_['IOPorts'] ='#include &lt;avr/io.h&gt;\n';
  Blockly.Dart.Config_['IOPorts']='DDR'+dropdown_port+'=1<&lt;P'+dropdown_port+dropdown_pin+';\n';
  var code = 'PORT'+dropdown_port+'&=~(1<&lt;P'+dropdown_port+dropdown_pin+');\n';//Equivalente de codigo para escritura de pin en bajo.
  return code;
};

Blockly.Dart['readpinon'] = function(block) {//Esta funcion configura la lectura de pin cuando este esta en 1. Los parametros son Puerto y pin
  var dropdown_port = block.getFieldValue('Port');
  var value_pin = Blockly.Dart.valueToCode(block, 'pin', Blockly.Dart.ORDER_ATOMIC);
  Blockly.Dart.Config_['Pin'+dropdown_port+value_pin]='DDR'+dropdown_port+'&=~(1<&lt;P'+dropdown_port+value_pin+');\n';
  Blockly.Dart.definitions_['IOPorts'] ='#include &lt;avr/io.h&gt;\n';
  var code = '(PIN'+dropdown_port+' & (1<&lt;PIN'+dropdown_port+value_pin+'))';
  return [code, Blockly.Dart.ORDER_ATOMIC];
  //return code;
};

Blockly.Dart['readpinoff'] = function(block) {//Esta funcion configura la lectura de pin cuando este esta en 1. Los parametros son Puerto y pin
  var dropdown_port = block.getFieldValue('Port');
  var value_pin = Blockly.Dart.valueToCode(block, 'pin', Blockly.Dart.ORDER_ATOMIC);
  Blockly.Dart.Config_['Pin'+dropdown_port+value_pin]='DDR'+dropdown_port+'&=~(1<&lt;P'+dropdown_port+value_pin+');\n';
  var code = '(!(PIN'+dropdown_port+' &(1<&lt;PIN'+dropdown_port+value_pin+')))';
  Blockly.Dart.definitions_['IOPorts'] ='#include &lt;avr/io.h&gt;\n';
  return [code, Blockly.Dart.ORDER_ATOMIC];
};

Blockly.Dart['writeport'] = function(block) {//Escritura de puerto.
  var dropdown_port = block.getFieldValue('Port');
  var value_val = Blockly.Dart.valueToCode(block, 'val', Blockly.Dart.ORDER_ATOMIC);
  // TODO: Assemble Dart into code variable.
  Blockly.Dart.definitions_['IOPorts'] ='#include &lt;avr/io.h&gt;\n';
  var code = 'PORT'+dropdown_port+'='+value_val+';\n';
  // TODO: Change ORDER_NONE to the correct strength.
  return code;
};

Blockly.Dart['configport'] = function(block) {//Configuracion de puerto
  var dropdown_port = block.getFieldValue('Port');
  var value_val = Blockly.Dart.valueToCode(block, 'val', Blockly.Dart.ORDER_ATOMIC);
  // TODO: Assemble Dart into code variable.
  Blockly.Dart.definitions_['IOPorts'] ='#include &lt;avr/io.h&gt;\n';
  var code = 'DDR'+dropdown_port+'='+value_val+';\n';
  return code;
};

Blockly.Dart['usarttext'] = function(block) {//Enviar a texto a UART
 Blockly.Dart.Config_['Uart']='DDRD=1<&lt;PD1;\nDDRD=0<&lt;PD0;\nUSART_Init();\n' //Esta configuracion esta en el archivo user.c
   Blockly.Dart.definitions_['IOPorts'] ='#include &lt;avr/io.h&gt;\n';
  var value_texto = Blockly.Dart.valueToCode(block, 'texto', Blockly.Dart.ORDER_ATOMIC);
  // TODO: Assemble Dart into code variable.
  var code = 'charsend("'+value_texto+'");\n';//Enviar texto a UART
  return code;
};

Blockly.Dart['usartfloat'] = function(block) {//Enviar flotante a UART
Blockly.Dart.Config_['Uart']='DDRD=1<&lt;PD1;\nDDRD=0<&lt;PD0;\nUSART_Init();\n' //Esta configuracion esta en el archivo user.c
   Blockly.Dart.definitions_['IOPorts'] ='#include &lt;avr/io.h&gt;\n';
  var value_texto = Blockly.Dart.valueToCode(block, 'texto', Blockly.Dart.ORDER_ATOMIC);
  // TODO: Assemble Dart into code variable.
  var code = 'Floattocharsend('+value_texto+');\n';//Enviar flotante a UART
  return code;
};

Blockly.Dart['usartinteger'] = function(block) {//Enviar entero a UART
  Blockly.Dart.Config_['Uart']='DDRD=1<&lt;PD1;\nDDRD=0<&lt;PD0;\nUSART_Init();\n' //Esta configuracion esta en el archivo user.c
   Blockly.Dart.definitions_['IOPorts'] ='#include &lt;avr/io.h&gt;\n';
  var value_texto = Blockly.Dart.valueToCode(block, 'texto', Blockly.Dart.ORDER_ATOMIC);
  // TODO: Assemble Dart into code variable.
  var code = 'Inttocharsend('+value_texto+');\n';//Enviar Entero a UART
  return code;
};

Blockly.Dart['adc'] = function(block) {//Funcion para ADC.
  var value_channel = Blockly.Dart.valueToCode(block, 'channel', Blockly.Dart.ORDER_ATOMIC);
  Blockly.Dart.definitions_['IOPorts'] ='#include &lt;avr/io.h&gt;\n';
  Blockly.Dart.functionconfig_['adc']='\nvoid ADCConfig(char channel){//Configuracion  de ADC\n';
  Blockly.Dart.functionconfig_['adc']+='ADMUX=channel;\n';
  Blockly.Dart.functionconfig_['adc']+='ADCSRA |=1<&lt;ADEN;//Iniciar ADC\n';
  Blockly.Dart.functionconfig_['adc']+='ADCSRA&=~((1<&lt;ADPS0)|(1<&lt;ADPS1));\n';
  Blockly.Dart.functionconfig_['adc']+='ADCSRA |=(1<&lt;ADPS2);//La frecuencia del reloj del ADC es 16Mhz/16=1MHz\n';
  Blockly.Dart.functionconfig_['adc']+='ADMUX |= 1<&lt;REFS0;\n';
  Blockly.Dart.functionconfig_['adc']+='ADMUX &=~ (1<&lt;REFS1);\n';
  Blockly.Dart.functionconfig_['adc']+='ADMUX &=~(1<&lt;ADLAR);\n}\n';
  Blockly.Dart.functionconfig_['adc']+='\nint LeerCanal(){//Leer canal de conversion AD\n';
  Blockly.Dart.functionconfig_['adc']+='ADCSRA|=1<&lt;ADSC;//Iniciar conversion\n';
  Blockly.Dart.functionconfig_['adc']+='while (!(ADCSRA & (1<&lt;ADIF)));// Esperar hasta que la conversion se complete\n';
  Blockly.Dart.functionconfig_['adc']+='return ADCL+ADCH*256; //Retornar valor del conversor en el rango entre 0 y 1023.\n}\n';
  Blockly.Dart.Config_['ADCconf']='ADCConfig('+value_channel+');//Funcion para configurar ADC\n';
  var codeB = 'LeerCanal()';
  return [codeB, Blockly.Dart.ATOMIC];
};
