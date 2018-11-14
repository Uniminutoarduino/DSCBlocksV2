/* 
 * File:   Hardwareprofile.h
 * Author: Jonathan
 *
 * Created on January 28, 2017, 11:51 AM
 */
#ifndef HARDWAREPROFILE_H
#define	HARDWAREPROFILE_H
#include <xc.h>



//Built-in LEDS in the board
#define LED1 PORTBbits.RB12
#define TRISLED1 TRISBbits.TRISB12
#define LED2 PORTBbits.RB13
#define TRISLED2 TRISBbits.TRISB13
//Definition of output pin registers (TRISx and PORTx)
#define TRISPIN0 TRISCbits.TRISC0
#define TRISPIN1 TRISCbits.TRISC1
#define TRISPIN2 TRISCbits.TRISC2
#define TRISPIN3 TRISCbits.TRISC3
#define PIN0 PORTCbits.RC0
#define PIN1 PORTCbits.RC1
#define PIN2 PORTCbits.RC2
#define PIN3 PORTCbits.RC3
//Definition of input pin registers (TRISx and PORTx)
#define TRISINPIN0 TRISAbits.TRISA0
#define TRISINPIN1 TRISAbits.TRISA1
#define TRISINPIN2 TRISBbits.TRISB0
#define TRISINPIN3 TRISBbits.TRISB1
#define TRISINPIN4 TRISBbits.TRISB2
#define TRISINPIN5 TRISBbits.TRISB3
#define PININ0 PORTAbits.RA0
#define PININ1 PORTAbits.RA1
#define PININ2 PORTBbits.RB0
#define PININ3 PORTBbits.RB1
#define PININ4 PORTBbits.RB2
#define PININ5 PORTBbits.RB3
//Chip Select for SPI
#define CS PORTBbits.RB5
#define CSTRIS TRISBbits.TRISB5



#endif	/* HARDWAREPROFILE_H */

