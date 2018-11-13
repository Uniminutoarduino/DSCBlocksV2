/**
 * Visual Blocks Language
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
 * @fileoverview Helper functions for generating Dart blocks.
 * @author gasolin@gmail.com (Fred Lin)
 */
'use strict';

goog.provide('Blockly.Dart.base2');

goog.require('Blockly.Dart');
var FreqMulms=0; //Variable global de multiplicacion de frecuencia
var FreqMulus=0; //Variale global de multiplicacion en microsegundos
var FreqBaseint=1;//Frecuencia base seleccionada
//Las variables se declaran globales con la finalidad de ser usadas por multiples funciones

Blockly.Dart['osc'] = function(block) {
  //Blockly.Dart.libraries_['LibreriadsPICG']='#include <xc.h> //DSC global library \n'
  Blockly.Dart.libraries_['HardwareProfile']='#include "Hardwareprofile.h" //C Library that contains the I/O of the board, see inside the folder\n'
  var value_m = Blockly.Dart.valueToCode(block, 'M', Blockly.Dart.ORDER_ATOMIC) || '1000'
  var value_n1 = Blockly.Dart.valueToCode(block, 'N1', Blockly.Dart.ORDER_ATOMIC) || '1000'
  var value_n2 = Blockly.Dart.valueToCode(block, 'N2', Blockly.Dart.ORDER_ATOMIC) || '1000'
  // TODO: Assemble Dart into code variable.
  var valN1=parseInt(value_n1);
  valN1=valN1-2; //Restar N1 con 2 decuerdo a datasheet, por ejemplo un valor N1 de 2 en el registro PLLPRE equivalente es 0.
  var valN2=parseInt(value_n2);
  switch (valN2){//Valor de N2 de acuerdo al indicado para PLLPOST.(ver datasheet) (Valor por defecto 2)
  case 2:
  valN2=0; 
  break; 
  case 4:
  valN2=1; 
  break; 
  case 8:
  valN2=3; 
  break;
  default:
  valN2=0; 
  break; 
  }
  var FreqBase=7.37e6*((value_m)/(value_n1*value_n2));
  FreqBase=FreqBase/2;
  FreqBaseint=parseInt(FreqBase);//Convertir frecuencia a entero
  var Freqmil=FreqBaseint/1000;
  var Freqmicro=Freqmil/1000;
  Blockly.Dart.setups_['N1']=' //Values for Internal Oscillator FRC(7.37MHz/w PLL)\n CLKDIVbits.PLLPRE='+valN1.toString()+';//N1 value of '+value_n1+' for oscillator (PLLPRE)'
  Blockly.Dart.setups_['N2']=' CLKDIVbits.PLLPOST='+valN2.toString()+';//N2 value value of '+value_n2+' for oscillator (PLLPOST)'
  Blockly.Dart.setups_['M']=' PLLFBDbits.PLLDIV='+value_m+';//M value for oscillator (PLLDIV)\n'
  Blockly.Dart.frecuencia_['0']=FreqBaseint;//Guardar frecuencia para ser usada por Delays
  Blockly.Dart.frecuencia_['1']=Freqmil;//Guardar frecuencia para ser usada por Delays milisegundos
  Blockly.Dart.frecuencia_['2']=Freqmicro;//Guardar frecuencia para ser usada por Delays microsegundos

  FreqMulms=parseInt(Freqmil);//Asinacion de multiplicador de frecuencia em milisegundos
  FreqMulus=parseInt(Freqmicro);
  var code = '//The Freq Base for the DSC will be '+FreqBase/1000000+'MHz.\n';
  return code;
};

Blockly.Dart['negate_output'] = function(block) {
  var dropdown_pin = block.getFieldValue('Pin');
  // TODO: Assemble JavaScript into code variable.
  var code;
  var numpin=parseInt(dropdown_pin);//Pin value to number
  numpin=numpin+1;//Increase number pin for comment
  var value_pinText=numpin.toString();//Convert number pin to equivalent in String
  if(dropdown_pin=="LED1"||dropdown_pin=="LED2"){
  Blockly.Dart.setups_['DefConfigIOB'+dropdown_pin]=' TRIS'+dropdown_pin+'=0;//Configure'+dropdown_pin+' of the board as output'
  code = dropdown_pin+'= ~'+dropdown_pin+';\n';
  }else{
  Blockly.Dart.setups_['DefConfigIOC'+dropdown_pin]=' TRISPIN'+dropdown_pin+'=0;//Pin '+value_pinText+' of the board as output'
  code = 'PIN'+dropdown_pin+'= ~PIN'+dropdown_pin+';\n';
  }

  return code;
};

Blockly.Dart.salidaalto = function() {
  //Blockly.Dart.libraries_['LibreriadsPICG']='#include <xc.h> //DSC global library \n'
  Blockly.Dart.libraries_['HardwareProfile']='#include "Hardwareprofile.h" //C Library that contains the I/O of the board, see inside the folder\n'
  var value_pin = this.getFieldValue('Pin');
  var numpin=parseInt(value_pin);//Pin value to number
  numpin=numpin+1;//Increase number pin for comment
  var value_pinText=numpin.toString();//Convert number pin to equivalent in String
  Blockly.Dart.setups_['DefConfigIOC'+value_pin]=' TRISPIN'+value_pin+'=0;//Pin '+value_pinText+' of the board as output'
  // TODO: Assemble JavaScript into code variable.
  var code = 'PIN'+value_pin+'=1;//Output '+value_pin+' of the board in high or 1\n'
  return code;
};

Blockly.Dart.salidabajo = function() {
  var value_pin = this.getFieldValue('Pin');
  var numpin=parseInt(value_pin);//Pin value to number
  numpin=numpin+1;//Increase number pin for comment
  var value_pinText=numpin.toString();//Convert number pin to equivalent in String
  Blockly.Dart.setups_['DefConfigIOC'+value_pin]=' TRISPIN'+value_pin+'=0;//Pin '+value_pinText+' of the board as output'
  // TODO: Assemble JavaScript into code variable.
  var code = 'PIN'+value_pin+'=0;//Output '+value_pin+' of the board in Low or 0\n'
  return code;
};

Blockly.Dart.salidaled_alto_bajo = function() {//Led de salida del sistema para comprobaciones.
  /*var led = this.getFieldValue('Leds');
  var estado = this.getFieldValue('Estado');
  Blockly.Dart.setups_['setup_output_'] = 'ModoPin('+led+', OUTPUT);'; //Modo de pin especificado
  var code = 'IOWrite('+led+','+Estado+');\n'
  return code;*/
  //Blockly.Dart.definitions_['librarydef'] = '#include <pps.h>\n#include <stdio.h>\n#include <xc.h>\n#include <stdlib.h>\n#include <libpic30.h>\n#include <uart.h>\n#define _ISR_AUTO_PSV __attribute__((__interrupt__, __auto_psv__))\n#define FCY 36850000 //Fcy = (Fosc/2) Fosc = 36.85Mhz\n';
  //Blockly.Dart.libraries_['LibreriadsPICG']='#include <xc.h>//DSC global library \n'
  Blockly.Dart.libraries_['HardwareProfile']='#include "Hardwareprofile.h" //C Library that contains the I/O of the board, see inside the folder\n'
  var dropdown_pin = this.getFieldValue('Leds');
  var dropdown_stat = this.getFieldValue('Estado');
   var PinValueLED=0;
  if(dropdown_pin=="LED1"){
	PinValueLED=1;
	Blockly.Dart.setups_['DefConfigIOB'+dropdown_pin]=' TRISLED1=0;//Configure LED1 of the board as output'
  }else{
    Blockly.Dart.setups_['DefConfigIOB'+dropdown_pin]=' TRISLED2=0;//Configure LED2 of the board as output'
    PinValueLED=2;
  }

  var code = 'LED'+PinValueLED+'='+dropdown_stat+';//Selected board LED'+PinValueLED+' in state '+dropdown_stat+'\n'
  return code;
};

Blockly.Dart.delay = function() {//Esta funcion genera un delay variable
  //ver la formula...
  var Freqfactor=0;
  //Freqfactor=parseInt(Blockly.Dart.frecuencia_['1']);//Multiplicdor para generar retraso de milisegundos
  var OSCVal = Blockly.Dart.valueToCode(this, 'Time', Blockly.Dart.ORDER_ATOMIC) || '1000' //Valor de oscilacion asignado
  OSCVal=OSCVal*FreqMulms;//Operacion de generacion de tiempo de acuerdo a frecuencia establecida.
  var code = '__delay32('+OSCVal+'); //delay of '+OSCVal/FreqMulms+'ms.\n'
  //Blockly.Dart.definitions_['define_profileH'] = '#include <HProfile.h>\n';
  //var code='var=';&lt;&gt;
  Blockly.Dart.libraries_['delay']='#include <libpic30.h>//Library for delays\n'
  return code;
};

Blockly.Dart.delayusb = function() {//Esta funcion genera un delay variable
  //ver la formula...
  var OSCVal = Blockly.Dart.valueToCode(this, 'Time', Blockly.Dart.ORDER_ATOMIC) || '1000' //Valor de oscilacion asignado
  OSCVal=OSCVal*FreqMulus;//Operacion de generacion de tiempo de acuerdo a frecuencia establecida.
  var code = '__delay32('+OSCVal+'); //delay of '+OSCVal/FreqMulus+'us.\n'
  //Blockly.Dart.definitions_['define_profileH'] = '#include <HProfile.h>\n';
    Blockly.Dart.definitions_['delay']='#include <libpic30.h>//Library for delays\n'
  return code;
};

Blockly.Dart.cycledelay = function() {//Esta funcion genera un delay de ;los ciclos que el usuario quiera
  //ver la formula...
  var OSCVal = Blockly.Dart.valueToCode(this, 'Cycle', Blockly.Dart.ORDER_ATOMIC) || '1000' //Valor de oscilacion asignado
  //OSCVal=OSCVal;//Operacion de generacion de tiempo de acuerdo a frecuencia establecida.
  var code = '__delay32('+OSCVal+'); //delay of '+OSCVal+' instruction cycles.\n'
  //Blockly.Dart.definitions_['define_profileH'] = '#include <HProfile.h>\n';
  Blockly.Dart.libraries_['delay']='#include <libpic30.h>//Library for delays\n'
  return code;
};

Blockly.Dart.nop = function() {//Esta funcion genera un delay variable

  var code = 'asm ("NOP");//NOP operation, delay one instruction cycle\n'//Bloque de operacion NOP.
  //Blockly.Dart.definitions_['define_profileH'] = '#include <HProfile.h>\n';
  return code;
};

Blockly.Dart.leerpuerto = function() {//Funcion para leer estado de pin 1 o 0
  var value_pin = this.getFieldValue('Pin');
  // TODO: Assemble JavaScript into code variable.
  Blockly.Dart.setups_['DefConfigIOD'+value_pin]=' TRISINPIN'+value_pin+'=1;//Pin '+value_pin+' as input \n'
   var code = 'PININ'+value_pin;
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Dart.ORDER_ATOMIC];
};

Blockly.Dart.toggle = function() {//Led de salida del sistema para comprobaciones. En este caso recordar que daly32 funciona con el doble del oscilador configurado
  //ver la formula...
  //Blockly.Dart.definitions_['librarydef'] = '#include <pps.h>\n#include <stdio.h>\n#include <xc.h>\n#include <stdlib.h>\n#include <libpic30.h>\n#include <uart.h>\n#define _ISR_AUTO_PSV __attribute__((__interrupt__, __auto_psv__))\n#define FCY 36850000 //Fcy = (Fosc/2) Fosc = 36.85Mhz\n';
  Blockly.Dart.libraries_['delay']='#include <libpic30.h>//Library for delays\n'
  var dropdown_pin = this.getFieldValue('Pin');//Get value of LED on Board.
  var OSCVal = Blockly.Dart.valueToCode(this, 'Osc', Blockly.Dart.ORDER_ATOMIC) || '1000' //Valor de oscilacion asignado
  OSCVal=OSCVal*40000;//Operacion de generacion de tiempo de acuerdo a frecuencia establecida.
  //var OSCVal=500;//Definicion de valor de oscilador, este se basa en la frecuencia del oscilador.
  //Blockly.Dart.setups_['setup_output_' + dropdown_pin] = 'ModoPin(' + dropdown_pin + ', OUTPUT);';
  var PinValueLED=0;
  if(dropdown_pin=="LED1"){
	PinValueLED=1;
	Blockly.Dart.setups_['DefConfigIO'+dropdown_pin]='TRISLED1=0;//LED0 config\n'
  }else{
    Blockly.Dart.setups_['DefConfigIO'+dropdown_pin]='TRISLED2=0;//LED1 config\n'
    PinValueLED=2;
  }
  var code = '\nLED'+PinValueLED+'=1;\n__delay32('+OSCVal+'); // '+OSCVal/40000+'ms. \nLED'+PinValueLED+'=0;\n__delay32('+OSCVal+'); // '+OSCVal/40000+'ms.\n'
  //Blockly.Dart.definitions_['define_profileH'] = '#include <HProfile.h>\n';
  return code;
};


Blockly.Dart.adcb = function() {//Funcion ADC unico canal
  var value_ref = Blockly.Dart.valueToCode(this, 'ADCchannel', Blockly.Dart.ORDER_ATOMIC) || '0'
  // TODO: Assemble JavaScript into code variable.
  //Blockly.Dart.definitions_['librarydef'] = '#include <pps.h>\n#include <stdio.h>\n#include <xc.h>\n#include <stdlib.h>\n#include <libpic30.h>\n#include <uart.h>\n#define _ISR_AUTO_PSV __attribute__((__interrupt__, __auto_psv__))\n#define FCY 36850000 //Fcy = (Fosc/2) Fosc = 36.85Mhz\n';
  var code = 'readADC('+value_ref+')'
  //Blockly.Dart.configadc_['definitioninit']='AD1CHS0bits.CH0SA = channel;          //Assign Analog port to read.\n'
  //Blockly.Dart.configadc_['definitioninit']+='IFS0bits.AD1IF = 0;\n'
  Blockly.Dart.configadc_['definitioninit']='void readADC(char channel){//Funtion for ReadADC selected\n'
  Blockly.Dart.configadc_['definitioninit']+=' AD1CHS0bits.CH0SA = channel;	// Select the channel to convert\n'
  Blockly.Dart.configadc_['definitioninit']+=' IFS0bits.AD1IF = 0; 					// Clear ADC interrupt flag\n'
  Blockly.Dart.configadc_['definitioninit']+=' AD1CON1bits.DONE=0;				    // Clear Done Flag\n'
  Blockly.Dart.configadc_['definitioninit']+=' AD1CON1bits.SAMP=1; 					// Start sampling\n'
  Blockly.Dart.configadc_['definitioninit']+=' while(IFS0bits.AD1IF == 0);		// Wait for conversion complete\n'
  Blockly.Dart.configadc_['definitioninit']+=' AD1CON1bits.ASAM = 0; 				// Then stop sample/convert...\n'
  Blockly.Dart.configadc_['definitioninit']+=' return(ADC1BUF0);\n}\n'
  Blockly.Dart.setups_['DefConfigIOK'+value_ref]=' //Configuration for ADC\n TRISINPIN'+value_ref+' = 1; //TRISXPIN input.\n'
  Blockly.Dart.setups_['DefConfigIOK'+value_ref]+=' AD1PCFGLbits.PCFG'+value_ref+' = 0; //AN'+value_ref+' as analog channel.\n'
  Blockly.Dart.setups_['DefConfigIOK'+value_ref]+=' AD1CON1bits.FORM   = 0;      // Data Output Format: Integer.\n'
  Blockly.Dart.setups_['DefConfigIOK'+value_ref]+=' AD1CON1bits.SSRC   = 0b111;  // AutoClock Conversion.\n'
  Blockly.Dart.setups_['DefConfigIOK'+value_ref]+=' AD1CON1bits.ASAM   = 1;      // ADC Sample Control: Sampling begins immediately after conversion\n'
  Blockly.Dart.setups_['DefConfigIOK'+value_ref]+=' AD1CON1bits.AD12B  = 1;      // 12-bit ADC operation\n'
  Blockly.Dart.setups_['DefConfigIOK'+value_ref]+=' AD1CON2bits.ALTS=0;          // Alternate Input Sample Mode Select Bit\n'
  Blockly.Dart.setups_['DefConfigIOK'+value_ref]+=' AD1CON2bits.CHPS  = 0;       // Converts CH0\n'
  Blockly.Dart.setups_['DefConfigIOK'+value_ref]+=' AD1CON1bits.ADON = 1;        // Turn on the A/D converter\n'
  return [code, Blockly.Dart.ORDER_ATOMIC];
};

Blockly.Dart.pwmotuputb = function() {//PWM output function.
  var value_dutty = Blockly.Dart.valueToCode(this, 'Dutty', Blockly.Dart.ORDER_ATOMIC)  || '1000'
  var value_freq = Blockly.Dart.valueToCode(this, 'Freq', Blockly.Dart.ORDER_ATOMIC)  || '1000'
  var dropdown_pwm = this.getFieldValue('PWM');
  var Prescale=this.getFieldValue('Prescale'); //get preescale
  var code='H';//Code return application.
  var Duty=' ';//Code retun dutty.
  var Period=((1/value_freq)*FreqBaseint)/Prescale; //The period value is calculated as follows: ((1/freq)*FCY)/Pre for this case Pre=64.
  var PeriodInteger=parseInt(Period);
  if(PeriodInteger>65535){//validar que el valor de perido no sea mayor a 65535 numero maximo 16 bits.
	PeriodInteger=65535;
  }
  // TODO: Assemble JavaScript into code variable.
  Blockly.Dart.libraries_['pwm']='#include <timer.h>//Library for timer\n'
  Blockly.Dart.libraries_['compare']='#include <outcompare.h> //Library for PWM\n'
  Blockly.Dart.libraries_['pps']='#include <pps.h> //Library for PPS\n'
  Blockly.Dart.setups_['TimerConfig']='//Configure PWM peripheral\nOpenTimer2(T2_ON &      //Timer2 ON-Timer 2 is the timer base of the PWM\n'
  Blockly.Dart.setups_['TimerConfig']+='T2_GATE_OFF &   //Gated mode OFF\n'
  Blockly.Dart.setups_['TimerConfig']+='T2_IDLE_STOP &  //Stop when controller is in sleep\n'
  Blockly.Dart.setups_['TimerConfig']+='T2_PS_1_'+Prescale+' &   //Prescale of '+Prescale+', the preescale provides more control resolution for low frequencies\n'
  Blockly.Dart.setups_['TimerConfig']+='T2_SOURCE_INT, '+PeriodInteger+');//Config timer 2 for OCX module\n'
  if(dropdown_pwm=="1"){
	Blockly.Dart.setups_['DefConfigPWM'+dropdown_pwm ]='TRISPIN0=0;//RC0 pin output of PWM1\nPPSUnLock;//Unlock programming Pin Periphal\nPPSOutput(OUT_FN_PPS_OC1,OUT_PIN_PPS_RP16);  //PWM otuput on RP16\nPPSLock;\n'
	Blockly.Dart.setups_['OCx'+dropdown_pwm ]='unsigned int OC1Config1=OC_IDLE_STOP & // Stop when controller sleeps\n'
  Blockly.Dart.setups_['OCx'+dropdown_pwm ]+='			   OC_TIMER2_SRC & // Timer 2 is the source of the PWM\n'
  Blockly.Dart.setups_['OCx'+dropdown_pwm ]+='         OC_PWM_FAULT_PIN_DISABLE; // not using PWM fault\n'
	Blockly.Dart.setups_['OCx'+dropdown_pwm ]+='OpenOC1(OC1Config1, 0x00, 0x00);//Configuration OCX module\n'
  Blockly.Dart.setups_['OCx'+dropdown_pwm ]+='SetDCOC1PWM(0); //Configure OCx duty\n'
	code = 'SetDCOC1PWM('+value_dutty+'); //Configure OCx duty\n'
	}else if(dropdown_pwm=="2"){
	Blockly.Dart.setups_['DefConfigPWM'+dropdown_pwm ]='TRISPIN1=0;//RC1 pin output of PWM2\nPPSUnLock;//Unlock programming Pin Periphal\nPPSOutput(OUT_FN_PPS_OC1,OUT_PIN_PPS_RP17);  //PWM otuput on RP17\n PPSLock;\n'
	Blockly.Dart.setups_['OCx'+dropdown_pwm]='unsigned int OC2Config1=OC_IDLE_STOP & // Stop when controller sleeps\n'
  Blockly.Dart.setups_['OCx'+dropdown_pwm]+='					 OC_TIMER2_SRC & // Timer 2 es el source del modulo PWM\n'
  Blockly.Dart.setups_['OCx'+dropdown_pwm]+='          OC_PWM_FAULT_PIN_DISABLE; // not using PWM fault\n'
	Blockly.Dart.setups_['OCx'+dropdown_pwm]+='OpenOC2(OC2Config1, 0x00, 0x00);//Configuration OCX module\n'
  Blockly.Dart.setups_['OCx'+dropdown_pwm]+='SetDCOC2PWM(0); //Configure OCx duty\n'
	code = 'SetDCOC2PWM('+value_dutty+'); //Configure OCx duty\n'
	}else if(dropdown_pwm=="3"){
	Blockly.Dart.setups_['DefConfigPWM'+dropdown_pwm ]='TRISPIN2=0;//RC2 pin output of PWM3\nPPSUnLock;//Unlock programming Pin Periphal\nPPSOutput(OUT_FN_PPS_OC1,OUT_PIN_PPS_RP18);  //PWM otuput on RP18\n PPSLock;\n'
	Blockly.Dart.setups_['OCx'+dropdown_pwm]='unsigned int OC3Config1=OC_IDLE_STOP & // Stop when controller sleeps\n'
  Blockly.Dart.setups_['OCx'+dropdown_pwm]+='					  OC_TIMER2_SRC & // Timer 2 es el source del modulo PWM\n'
  Blockly.Dart.setups_['OCx'+dropdown_pwm]+='           OC_PWM_FAULT_PIN_DISABLE; // not using PWM fault\n'
	Blockly.Dart.setups_['OCx'+dropdown_pwm]+='OpenOC3(OC3Config1, 0x00, 0x00);//Configuration OCX module\n'
  Blockly.Dart.setups_['OCx'+dropdown_pwm]+='SetDCOC3PWM(0); //Configure OCx duty\n'
	code = 'SetDCOC3PWM('+value_dutty+'); //Configure OCx duty\n'
	}else{
	Blockly.Dart.setups_['DefConfigPWM'+dropdown_pwm ]='TRISPIN3=0;//RC3 pin output of PWM4\nPPSUnLock;//Unlock programming Pin Periphal\nPPSOutput(OUT_FN_PPS_OC1,OUT_PIN_PPS_RP19);  //PWM otuput on RP19\n PPSLock;\n'
	Blockly.Dart.setups_['OCx'+dropdown_pwm]='unsigned int OC4Config1=OC_IDLE_STOP & // Stop when controller sleeps\n'
  Blockly.Dart.setups_['OCx'+dropdown_pwm]+='					  OC_TIMER2_SRC & // Timer 2 es el source del modulo PWM\n'
  Blockly.Dart.setups_['OCx'+dropdown_pwm]+='           OC_PWM_FAULT_PIN_DISABLE; // not using PWM fault\n'
	Blockly.Dart.setups_['OCx'+dropdown_pwm]+='OpenOC4(OC4Config1, 0x00, 0x00);//Configuration OCX module\n'
	Blockly.Dart.setups_['OCx'+dropdown_pwm]+='SetDCOC4PWM(0); //Configure OCx duty\n'
	code = 'SetDCOC4PWM('+value_dutty+'); //Configure OCx duty\n'
	}
  return code;
};

Blockly.Dart.spiwritedac = function() {//Bloque de escritura para DAC MCP4822, este chip no tiene retorno solo se escribe el valor
  //de acuerdo a la comvenci[on] final.
  var channel = this.getFieldValue('channel');
  var input = Blockly.Dart.valueToCode(this, 'input', Blockly.Dart.ORDER_ATOMIC) || '1000' //Entrada a ser escrita en el canal A o B...
  // TODO: Assemble JavaScript into code variable.
  Blockly.Dart.libraries_['SPI']='#include <spi.h> //library for SPI\n'
  Blockly.Dart.setups_['definitioninitSPI']='//SPI configuration see datasheet\nunsigned int SPICONValue2=0;\nunsigned int SPICONValue1=0;\nunsigned int SPISTATValue=0;\nCSTRIS=0;//Chip select pin for MCP4822\n'
  Blockly.Dart.setups_['definitioninitSPI']+='PPSUnLock;\n'
  Blockly.Dart.setups_['definitioninitSPI']+='PPSOutput(OUT_FN_PPS_SDO1, OUT_PIN_PPS_RP20); // Maps SPI1 output to RP20\n'
  Blockly.Dart.setups_['definitioninitSPI']+='PPSOutput(OUT_FN_PPS_SCK1, OUT_PIN_PPS_RP21); // Maps SPI1 SCK output to RP21\n'
  Blockly.Dart.setups_['definitioninitSPI']+='PPSLock;//Lock PP\n'
  Blockly.Dart.setups_['definitioninitSPI']+='SPICONValue2  =  FRAME_ENABLE_OFF & FRAME_SYNC_OUTPUT;\n'
  Blockly.Dart.setups_['definitioninitSPI']+='SPICONValue1  =  ENABLE_SCK_PIN & ENABLE_SDO_PIN & SPI_MODE16_ON &\n'
  Blockly.Dart.setups_['definitioninitSPI']+='                 SPI_SMP_OFF & SPI_CKE_ON&\n'
  Blockly.Dart.setups_['definitioninitSPI']+='                 SLAVE_ENABLE_OFF &\n'
  Blockly.Dart.setups_['definitioninitSPI']+='                 CLK_POL_ACTIVE_HIGH &\n'
  Blockly.Dart.setups_['definitioninitSPI']+='                 MASTER_ENABLE_ON &\n'
  Blockly.Dart.setups_['definitioninitSPI']+= '                SEC_PRESCAL_2_1 &\n'
  Blockly.Dart.setups_['definitioninitSPI']+='                 PRI_PRESCAL_1_1;\n'
  Blockly.Dart.setups_['definitioninitSPI']+=' SPISTATValue  = SPI_ENABLE & SPI_IDLE_CON &\n'
  Blockly.Dart.setups_['definitioninitSPI']+='                 SPI_RX_OVFLOW_CLR;\n'
  Blockly.Dart.setups_['definitioninitSPI']+='OpenSPI1(SPICONValue1,0,SPISTATValue);//Open SPI1\n'
  Blockly.Dart.SPIinit_['SPIinita']='void SPIWriteMCP(int value){//SPI write for MCP4822\n'
  //Blockly.Dart.SPIinit_['SPIinita']+='int VectMCP=0b'+channel+'001000000000000;//This variable adjusts the value to the MCP4822 see datasheet...\n'
  Blockly.Dart.SPIinit_['SPIinita']+='CS=0;//Chip select\n'
  Blockly.Dart.SPIinit_['SPIinita']+='__delay32(60);\n//Delays of 60 cycles are needed for the correct functioning of MCP4822\n'
  Blockly.Dart.SPIinit_['SPIinita']+='WriteSPI1(value);\n'
  Blockly.Dart.SPIinit_['SPIinita']+='while(SPI1STATbits.SPITBF);//Wait for word sending complete...\n'
  Blockly.Dart.SPIinit_['SPIinita']+='__delay32(30);\n'
  Blockly.Dart.SPIinit_['SPIinita']+='CS=1;//Disable chip\n}\n\n'
  // TODO: Change ORDER_NONE to the correct strength.

  var code=input+'='+input+'+0b'+channel+'001000000000000;//This variable (First declare) adjusts the value to the MCP4822 see datasheet...\nSPIWriteMCP('+input+');//WriteSPI DAC value\n';
  return code;
};


Blockly.Dart.adcmultiple = function() {//El bloque configura la interrupcion de ADC esta es la forma correcta de leer el ADC...
  var channels = this.getFieldValue('channels');
  var time=Blockly.Dart.valueToCode(this, 'time', Blockly.Dart.ORDER_ATOMIC) || '1000' //Siempre que se tengan numeros no listas se debe
  //leer todo de esta forma....
  //var sample=Blockly.Dart.valueToCode(this, 'samples', Blockly.Dart.ORDER_ATOMIC) || '1000'
  var Sampletime=(time/1000)/(1/FreqBaseint);//Esl tiempo asignado esta en ms.
  var SampletimeInteger=parseInt(Sampletime);
  Blockly.Dart.Adcinterrupt_['definitA']='//This is the configuration to read ADC channels simultaneously\n#define NUM_CH    '+channels+'//Number of channels for ADC conversion\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='#define NUM_CHS2SCAN   '+channels+'\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='int scanCounter = 0;\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='void __attribute__ ( (interrupt, no_auto_psv) ) _ADC1Interrupt( void ){//ADC interrupt generated by timer 3\n'

  Blockly.Dart.Adcinterrupt_['definitA']+='IFS0bits.AD1IF = 0;//Clear flag of ADC interrupt\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='switch( scanCounter ){//Number of channels assigned to variables L(declare this variables)\n'
  for(var i=0;i<channels;i++){
	  Blockly.Dart.Adcinterrupt_['definitA']+='case '+i+' :\n'
	  Blockly.Dart.Adcinterrupt_['definitA']+='L'+i+'=ADC1BUF0;\n'
	  Blockly.Dart.Adcinterrupt_['definitA']+='break;\n'

  }
  Blockly.Dart.Adcinterrupt_['definitA']+='default:\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='break;\n}\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='scanCounter++;\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='if( scanCounter == NUM_CH ){\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='scanCounter = 0;}\n}\n\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='void InitAdc1( void ){//Configure ADC1 to read multiple channels simultaneously\n'
  for(var k=0;k<channels;k++){//Recorrer el vector para configurar pines adecuadamente...
  Blockly.Dart.Adcinterrupt_['definitA']+='TRISINPIN'+k+'= 1; //AN'+k+' as input\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='AD1PCFGLbits.PCFG'+k+'= 0; //ANX as channels\n'
  }
  Blockly.Dart.Adcinterrupt_['definitA']+='AD1CON1bits.SSRC = 2;  // Sample Clock Source: GP Timer3 starts conversion\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='AD1CON1bits.ASAM = 1;  // ADC Sample Control: Sampling begins immediately after conversion\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='AD1CON1bits.AD12B = 0; // 10-bit ADC operation\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='AD1CON2bits.CSCNA = 1; // Scan Input Selections for CH0+ during Sample A bit\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='AD1CON2bits.CHPS = 0;  // Converts CH0\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='AD1CON3bits.ADRC = 0;  // ADC Clock is derived from Systems Clock\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='AD1CON3bits.ADCS = 63; // ADC Conversion Clock Tad=Tcy*(ADCS+1)= (1/36.85M)*64 = 1.6us (625Khz), Tc=12*Tad\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='AD1CON2bits.SMPI = ( NUM_CHS2SCAN - 1 );    // #samples between conversions here Numchannels-1\n'
  // TODO: Assemble JavaScript into code variable.
  //Blockly.Dart.definitions_['librarydef'] = '#include <pps.h>\n#include <stdio.h>\n#include <xc.h>\n#include <stdlib.h>\n#include <libpic30.h>\n#include <uart.h>\n#define _ISR_AUTO_PSV __attribute__((__interrupt__, __auto_psv__))\n#define FCY 36850000 //Fcy = (Fosc/2) Fosc = 36.85Mhz\n';
  Blockly.Dart.Adcinterrupt_['definitA']+='AD1CSSL = 0x0000;\n'
  for(var m=0;m<channels;m++){//Recorrer el vector para configurar pines adecuadamente...
  Blockly.Dart.Adcinterrupt_['definitA']+='AD1CSSLbits.CSS'+m+' = 1;//Configure ANX for SCAN\n'
  }

  Blockly.Dart.Adcinterrupt_['definitA']+='IFS0bits.AD1IF = 0;     // Clear the A/D interrupt flag bit\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='IEC0bits.AD1IE = 1;     // Enable A/D interrupt\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='AD1CON1bits.ADON = 1;   // Turn on the A/D converter\n}\n\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='void InitTmr3( void ){\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='TMR3 = 0x0000;\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='PR3 = '+SampletimeInteger+';  // Trigger ADC1 every '+time+'msec\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='IFS0bits.T3IF = 0;  // Clear Timer 3 interrupt\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='IEC0bits.T3IE = 0;  // Disable Timer 3 interrupt\n'
  Blockly.Dart.Adcinterrupt_['definitA']+='T3CONbits.TON = 1; //Starts timer3\n}\n\n'
  Blockly.Dart.Adcdefin_['ADC']=' InitAdc1();//Initialise ADC1\n'
  Blockly.Dart.Adcdefin_['ADC']+=' InitTmr3();//Initialise Timer 3 as trigger of ADC\n'
  var code=" "
  return code;
};


Blockly.Dart['ostaskcreate'] = function(block) {//Rutina de creacion de tarea para RTOS.
  var value_prior = Blockly.Dart.valueToCode(block, 'Prior', Blockly.Dart.ORDER_ATOMIC);
  var value_nombre = Blockly.Dart.valueToCode(block, 'Nombre', Blockly.Dart.ORDER_ATOMIC);
  value_nombre=value_nombre.replace(/["']/g, "");
  var statements_task = Blockly.Dart.statementToCode(block, 'Task');
  // TODO: Assemble Dart into code variable.
  Blockly.Dart.libraries_['OSA']='#include <osa.h> //library for RTOS OSA\n'
  Blockly.Dart.RTOSTask_['Task'+value_nombre]='\nvoid '+value_nombre+'(void){//RTOS task\n'+statements_task+'\n}\n'
  var code ='OS_Task_Create('+value_prior+','+value_nombre+');//RTOS task with priority '+value_prior+' \n';
  return code;
};

Blockly.Dart['os_init'] = function(block) {//Funcion para iniciar RTOS
  // TODO: Assemble Dart into code variable.
  Blockly.Dart.libraries_['OSA']='#include <osa.h> //library for RTOS OSA\n'
  var code = 'OS_Init();//Start RTOS\n';
  return code;
};

Blockly.Dart['os_run'] = function(block) {//Funcion para correr RTOS
  // TODO: Assemble Dart into code variable.
  Blockly.Dart.libraries_['OSA']='#include <osa.h> //library for RTOS OSA\n'
  var code = 'OS_Run();//Run RTOS\n';
  return code;
};

Blockly.Dart['os_timer'] = function(block) {//Funcion para Tick de Timer
  // TODO: Assemble Dart into code variable.
  Blockly.Dart.libraries_['OSA']='#include <osa.h> //library for RTOS OSA\n'
  var code = 'OS_Timer();//OS Tick\n';
  return code;
};

Blockly.Dart['os_yield'] = function(block) {//Funcion para liberar rtos
  // TODO: Assemble Dart into code variable.
  Blockly.Dart.libraries_['OSA']='#include <osa.h> //library for RTOS OSA\n'
  var code = 'OS_Yield();//RTOS sets free\n';
  return code;
};

Blockly.Dart['os_delay'] = function(block) {//function de delay para RTOS
  var value_tick = Blockly.Dart.valueToCode(block, 'Tick', Blockly.Dart.ORDER_ATOMIC);
  // TODO: Assemble Dart into code variable.
  Blockly.Dart.libraries_['OSA']='#include <osa.h> //library for RTOS OSA\n'
  var code = 'OS_Delay('+value_tick+');//Delay RTOS in ticks or timer cycles\n';
  return code;
};

Blockly.Dart.timerinterrupt = function() {//Timer interrupt funcion
  Blockly.Dart.libraries_['Timer']='#include <timer.h>//library for timers\n'
  var timer = this.getFieldValue('timer');
  var timertime = Blockly.Dart.valueToCode(this, 'timeinterrupt', Blockly.Dart.ORDER_ATOMIC) || '1000' //Timer interruption time...
  var preescale = this.getFieldValue('preescale');
  var priority = this.getFieldValue('priority');//Prioridad 0 to 7.... para timer....
  var Sampletime=(timertime/1000)/(preescale*(1/FreqBaseint));//Esl tiempo asignado esta en ms.
  var SampletimeInt=parseInt(Sampletime);//Esl tiempo asignado esta en ms.
  var branch = Blockly.Dart.statementToCode(this, 'bucle');
  var valueIF;//Esta variable ajusta el valor del resgistro IFS: IFS0 para timer 1,2,3 y IFS1 para 4 y 5...
  var IFS=0;
  if(timer<4){
	 valueIF='IFS0bits.T'+timer+'IF=0;//Clear Timer flag\nWriteTimer'+timer+'(0);//Restart Timer\n'
  }else{
	 valueIF='IFS1bits.T'+timer+'IF=0;//Clear Timer flag\nWriteTimer'+timer+'(0);//Restart Timer\n'
  }
  Blockly.Dart.functionconfig_['Timer'+timer]='void __attribute__((interrupt,no_auto_psv)) _T'+timer+'Interrupt( void ) //Interruption Service Routine (ISR) for Timer'+timer+'\n{\n'+valueIF+ '\n' + branch + '}\n\n';
  Blockly.Dart.functionconfig_['Timer'+timer]+='void ConfigTimer'+timer+'(void){//Routine to config timer selected\n T2CONbits.T32=0;//Timer register only allows 16 bit mode...\n T4CONbits.T32=0;\n ConfigIntTimer'+timer+'(T1_INT_PRIOR_'+priority+' & T'+timer+'_INT_ON );\n'
  Blockly.Dart.functionconfig_['Timer'+timer]+=' WriteTimer'+timer+'(0);//Write 0 as starting value...\n'
  Blockly.Dart.functionconfig_['Timer'+timer]+=' OpenTimer'+timer+'(T'+timer+'_ON & T'+timer+'_GATE_OFF & T'+timer+'_PS_1_'+preescale+' & T'+timer+'_SYNC_EXT_OFF &T'+timer+'_SOURCE_INT,'+SampletimeInt+');//'+SampletimeInt+' Timer value for '+timertime+'ms\n}\n'
  var code= 'ConfigTimer'+timer+'();//Routine to configure Timer\n'
  return code;
};

Blockly.Dart['alldig'] = function(block) {//Todos los pines digitales
  // TODO: Assemble Dart into code variable.
  Blockly.Dart.setups_['ADCDig']='ADPCFG = 0xffff;//All analog pins in digital mode\n';
  var code = '';
  return code;
};

Blockly.Dart.uart = function() {//Envio de valor entero a UART
  var value_ref = Blockly.Dart.valueToCode(this, 'VarUart', Blockly.Dart.ORDER_ATOMIC) || '1000'
  // TODO: Assemble JavaScript into code variable.
  //Blockly.Arduino.definitions_['librarydef'] = '#include <pps.h>\n#include <stdio.h>\n#include <xc.h>\n#include <stdlib.h>\n#include <libpic30.h>\n#include <uart.h>\n#define _ISR_AUTO_PSV __attribute__((__interrupt__, __auto_psv__))\n#define FCY 36850000 //Fcy = (Fosc/2) Fosc = 36.85Mhz\n';
  var code = 'Inttocharsend('+value_ref+');//Send integer to UART\n'
  //Blockly.Arduino.configadc_['definitioninit']='AD1CHS0bits.CH0SA = channel;          //Assign Analog port to read.\n'
  //Blockly.Arduino.configadc_['definitioninit']+='IFS0bits.AD1IF = 0;\n'
  // TODO: Change ORDER_NONE to the correct strength.
  return code
};

Blockly.Dart.uarttext = function() {//Envio de texto a UART
  var value_ref = Blockly.Dart.valueToCode(this, 'Text', Blockly.Dart.ORDER_ATOMIC) || '1000'
  // TODO: Assemble JavaScript into code variable.
  //Blockly.Arduino.definitions_['librarydef'] = '#include <pps.h>\n#include <stdio.h>\n#include <xc.h>\n#include <stdlib.h>\n#include <libpic30.h>\n#include <uart.h>\n#define _ISR_AUTO_PSV __attribute__((__interrupt__, __auto_psv__))\n#define FCY 36850000 //Fcy = (Fosc/2) Fosc = 36.85Mhz\n';
  var textuart=value_ref.replace(/'/g, '"');
  var code = 'charsend('+textuart+');//Send text to UART\n'
  //Blockly.Arduino.configadc_['definitioninit']='AD1CHS0bits.CH0SA = channel;          //Assign Analog port to read.\n'
  //Blockly.Arduino.configadc_['definitioninit']+='IFS0bits.AD1IF = 0;\n'
  // TODO: Change ORDER_NONE to the correct strength.
  return code;
};

Blockly.Dart.uartfloat = function() {//Envio de float value to uart
  var value_ref = Blockly.Arduino.valueToCode(this, 'Text', Blockly.Dart.ORDER_ATOMIC) || '1000'
  // TODO: Assemble JavaScript into code variable.
  //Blockly.Arduino.definitions_['librarydef'] = '#include <pps.h>\n#include <stdio.h>\n#include <xc.h>\n#include <stdlib.h>\n#include <libpic30.h>\n#include <uart.h>\n#define _ISR_AUTO_PSV __attribute__((__interrupt__, __auto_psv__))\n#define FCY 36850000 //Fcy = (Fosc/2) Fosc = 36.85Mhz\n';
  var code = 'Floattocharsend('+value_ref+');//Send float to UART\n' //Send float value to UART.
  //Blockly.Arduino.configadc_['definitioninit']='AD1CHS0bits.CH0SA = channel;          //Assign Analog port to read.\n'
  //Blockly.Arduino.configadc_['definitioninit']+='IFS0bits.AD1IF = 0;\n'
  // TODO: Change ORDER_NONE to the correct strength.
  return code;
};

Blockly.Dart['readuart'] = function() {
  // TODO: Assemble Dart into code variable.
  Blockly.Dart.libraries_['Uart']='#include <uart.h>//library for uart\n'
  var code = 'ReadUART()'
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Dart.ORDER_ATOMIC];
};

Blockly.Dart['uartready'] = function(block) {
  // TODO: Assemble Dart into code variable.
  Blockly.Dart.libraries_['Uart']='#include <uart.h>//library for uart\n'
  var code = 'DataRdyUART()';
  // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Dart.ORDER_NONE];
};
