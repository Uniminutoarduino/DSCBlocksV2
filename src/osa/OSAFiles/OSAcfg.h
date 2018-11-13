/******************************************************************************/
//
// This file was generated by OSAcfg_Tool utility.
// Do not modify it to prevent data loss on next editing.
//
// PLATFORM:     Microchip C30
//
/******************************************************************************/


#ifndef _OSACFG_H
#define _OSACFG_H

//------------------------------------------------------------------------------
// SYSTEM
//------------------------------------------------------------------------------

#define OS_TASKS                5   // Number of tasks that can be active at one time
#define OS_PRIORITY_LEVEL  OS_PRIORITY_EXTENDED 

//------------------------------------------------------------------------------
// ENABLE CONSTANTS
//------------------------------------------------------------------------------

#define OS_ENABLE_TTIMERS           // Enable task timers (OS_Delay and OS_xxx_Wait_TO)


//------------------------------------------------------------------------------
// TYPES
//------------------------------------------------------------------------------

#define OS_TIMER_SIZE           4   // Size of all system timers (1, 2 or 4)
#define OS_TTIMER_SIZE          4   // Size of task timers (1, 2 or 4)




#endif
