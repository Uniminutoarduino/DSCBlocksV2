#line 1 "C:/Users/Jonathan/Documents/Robot Social/osa/osa.c"
#line 1 "c:/users/jonathan/documents/robot social/osa/osa.h"
*
 ************************************************************************************************
 *
 * OSA cooperative RTOS for microcontrollers PIC, AVR and STM8
 *
 * OSA is distributed under BSD license (see license.txt)
 *
 * URL: http:
 *
 *----------------------------------------------------------------------------------------------
 *
 * File: osa.h
 *
 * Compilers: HT-PICC STD
 * HT-PICC PRO
 * HT-PICC18 STD
 * Mplab C18
 * Mplab C30
 * MikroC PRO
 * CCS
 * WinAVR
 * IAR
 * Cosmic
 * Raisonance
 *
 * Programmer: Timofeev Victor
 * osa@pic24.ru, testerplus@mail.ru
 *
 * Description: This file contains all prototypes of functions and variables,
 * data types definitions, system constants.
 * This file must be included in all modules that use OSA services.
 *
 * History: 10.00.2010 - Removed all modification marks
 * History: 10.07.2010 - Added ports for STM8: IAR and Raisonance
 *
 * 25.10.2010 - Bug fixed: OS_Qtimer_Break did not deleted timer from the list
 * - Bug fixed: interrupt became disables after calling OS_Qtimer_Run for timer already
 * presented in queue
 * - (osa_qtimer.c)
 * - (osa_pic16_htpicc.c)
 * - Definition of OS_Ttimer_Delay fixed (osa_ttimer.h)
 *
 * 30.10.2010 - Another bug in qtimers fixed (osa_qtimer.c, osa_pic16_htpicc.c)
 *
 * 22.11.2010 - IAR for AVR port bugs fixed (osa_avr_iar.h)
 *
 * 08.12.2010 - osa_pic18_htpicc.h
 *
 * 26.12.2010 - Bug fixed for PIC18: _OS_RETURN_NO_SAVE
 *
 * 06.03.2011 - osa_avr_winavr.c
 * osa_avr_winavr.h
 * osa_pic18_mikroc.c
 *
 *
 ************************************************************************************************
 */
#line 1 "c:/users/jonathan/documents/robot social/robot social/osacfg.h"
#line 1068 "c:/users/jonathan/documents/robot social/osa/osa.h"
typedef  unsigned char  _OST_SMSG;
#line 1 "c:/users/jonathan/documents/robot social/osa/port/osa_include.h"
#line 1 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.h"
#line 45 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.h"
typedef unsigned char OST_UINT8;
typedef unsigned int OST_UINT16;
typedef unsigned long OST_UINT32;
typedef OST_UINT8 OST_UINT;
typedef bit OST_BOOL;
#line 91 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.h"
static volatile char _fsr @ 0x04;
static volatile char _status @ 0x03;
static volatile char _indf @ 0x00;
static volatile char _pcl @ 0x02;
static volatile char _pclath @ 0x0A;
static volatile char _intcon @ 0x0B;

static volatile bit _carry @((unsigned)&_status*8)+0;
static volatile bit _zero @((unsigned)&_status*8)+2;
static bit _irp @((unsigned)&_status*8)+7;
static bit _gie @((unsigned)&_intcon*8)+7;
#line 123 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.h"
typedef OST_UINT8 OS_FSR_TYPE;


typedef OST_UINT16 OST_CODE_POINTER;
#line 434 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.h"
OST_BOOL _OS_CheckEvent (char);
#line 448 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.h"
 asm("_goto__OS_ReturnSave3              macro   ");
 asm("                                           ");
 asm("   global  __OS_ReturnSave3                ");
 asm("   ljmp    __OS_ReturnSave3                ");
 asm("                                           ");
 asm("   endm                                    ");







 asm("_call__OS_EnterWaitMode            macro   ");
 asm("                                           ");
 asm("   global  __OS_EnterWaitMode              ");
 asm("   local   task_ret_point                  ");
 asm("                                           ");
 asm("   movlw   low(task_ret_point)             ");
 asm("   movwf   0x04                            ");
 asm("   movlw   0x80|high(task_ret_point)       ");
 asm("   lcall   __OS_EnterWaitMode              ");
 asm("                                           ");
 asm("task_ret_point:                            ");
 asm("                                           ");
 asm("   endm                                    ");







 asm("_call__OS_EnterWaitModeTO    macro         ");
 asm("                                           ");
 asm("   global  __OS_EnterWaitModeTO            ");
 asm("   local   task_ret_point_                 ");
 asm("                                           ");
 asm("   movlw   low(task_ret_point_)            ");
 asm("   movwf   0x04                            ");
 asm("   movlw   0x80|high(task_ret_point_)      ");
 asm("   lcall   __OS_EnterWaitModeTO            ");
 asm("                                           ");
 asm("task_ret_point_:                           ");
 asm("                                           ");
 asm("   endm                                    ");
#line 637 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.h"
char OS_DI (void);
#line 1092 "c:/users/jonathan/documents/robot social/osa/osa.h"
typedef  unsigned char  OST_SMSG;
#line 1110 "c:/users/jonathan/documents/robot social/osa/osa.h"
typedef  void *  OST_MSG;







typedef

 volatile

 struct _OST_MSG_CB
{
 OST_UINT status;
 OST_MSG msg;

} OST_MSG_CB;
#line 1139 "c:/users/jonathan/documents/robot social/osa/osa.h"
typedef struct
{
 OST_UINT cSize;
 OST_UINT cFilled;
 OST_UINT cBegin;

} OST_QUEUE_CONTROL;




typedef struct
{
 OST_QUEUE_CONTROL Q;
 OST_MSG *pMsg;

} OST_QUEUE;




typedef struct
{
 OST_QUEUE_CONTROL Q;
 OST_SMSG *pSMsg;

} OST_SQUEUE;
#line 1190 "c:/users/jonathan/documents/robot social/osa/osa.h"
typedef struct
{
 OST_UINT bEventError : 1;
 OST_UINT bError : 1;

 OST_UINT bInCriticalSection : 1;

 OST_UINT bCheckingTasks : 1;


 OST_UINT bBestTaskFound : 1;





  OST_UINT bTimeout : 1; OST_UINT bGIE_CTemp : 1; 



 OST_UINT bEventOK : 1;







} OST_SYSTEM_FLAGS;
#line 1235 "c:/users/jonathan/documents/robot social/osa/osa.h"
typedef struct
{
 OST_UINT cPriority : 3;
 OST_UINT bReady : 1;
 OST_UINT bDelay : 1;


 OST_UINT bCanContinue: 1;
 OST_UINT bEnable : 1;
 OST_UINT bPaused : 1;


  


} OST_TASK_STATE;
#line 1270 "c:/users/jonathan/documents/robot social/osa/osa.h"
typedef struct
{
 OST_TASK_STATE State;
 OST_CODE_POINTER pTaskPointer;




  



  OST_UINT32  Timer;


} OST_TCB;



typedef  near  OST_TCB * OST_TASK_POINTER;
#line 1477 "c:/users/jonathan/documents/robot social/osa/osa.h"
typedef OST_UINT8 OST_CSEM;
#line 1506 "c:/users/jonathan/documents/robot social/osa/osa.h"
extern volatile  near  OST_SYSTEM_FLAGS _OS_Flags;
extern  near  OST_UINT _OS_Temp;


extern volatile  near  OST_UINT _OS_TempH;
#line 1527 "c:/users/jonathan/documents/robot social/osa/osa.h"
extern OST_TASK_POINTER  near  volatile _OS_CurTask;
#line 1563 "c:/users/jonathan/documents/robot social/osa/osa.h"
 extern  near  OST_UINT _OS_Best_Priority;
 extern  near  OST_UINT _OS_Worst_Priority;
 extern  near  OST_UINT _OS_Best_n;
 extern  near  OST_UINT _OS_Cur_Pos;
 extern  near  OST_UINT _OS_n;
 extern  near  OST_UINT8 _OS_TaskQueue[ 4 ];
 extern  near  OST_UINT8 _OS_TaskLevel[ 4 ];
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/osa_oldnames.h"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_system.h"
#line 48 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_system.h"
extern void OS_Init (void);



extern void OS_EnterCriticalSection (void);
extern void OS_LeaveCriticalSection (void);
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_tasks.h"
#line 37 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_tasks.h"
extern  near  OST_TCB _OS_Tasks[ 4 ];






void _OS_Task_Create(OST_UINT priority, OST_CODE_POINTER TaskAddr);
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_bsem.h"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_csem.h"
#line 54 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_csem.h"
extern void _OS_Csem_Signal(OST_CSEM *pCSem);
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_flag.h"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_msg.h"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_queue.h"
#line 91 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_queue.h"
extern void _OS_Queue_Send (OST_QUEUE *pQueue, OST_MSG Msg);
extern OST_MSG _OS_Queue_Get (OST_QUEUE *pQueue);
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_smsg.h"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_squeue.h"
#line 76 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_squeue.h"
 extern void _OS_Squeue_Send (OST_SQUEUE * pSQueue, OST_SMSG SMsg);
 extern OST_SMSG _OS_Squeue_Get (OST_SQUEUE *pSQueue);
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/timers/osa_stimer.h"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/timers/osa_stimer_old.h"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/timers/osa_dtimer.h"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/timers/osa_qtimer.h"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/timers/osa_ttimer.h"
#line 53 "c:/users/jonathan/documents/robot social/osa/kernel/timers/osa_ttimer.h"
void _OS_InitDelay( OST_UINT32  Delay);
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/timers/osa_timer.h"
#line 55 "C:/Users/Jonathan/Documents/Robot Social/osa/osa.c"
 volatile  near  OST_SYSTEM_FLAGS _OS_Flags;
  near   OST_UINT  _OS_Temp;
#line 63 "C:/Users/Jonathan/Documents/Robot Social/osa/osa.c"
 volatile  near   OST_UINT  _OS_TempH;
#line 74 "C:/Users/Jonathan/Documents/Robot Social/osa/osa.c"
 OST_TASK_POINTER  near  volatile _OS_CurTask;
#line 120 "C:/Users/Jonathan/Documents/Robot Social/osa/osa.c"
  near  OST_UINT _OS_Best_Priority;
  near  OST_UINT _OS_Worst_Priority;
  near  OST_UINT _OS_Best_n;
  near  OST_UINT _OS_Cur_Pos;
  near  OST_UINT _OS_n;
  near  OST_UINT8 _OS_TaskQueue[ 4 ];
  near  OST_UINT8 _OS_TaskLevel[ 4 ];
#line 1 "c:/users/jonathan/documents/robot social/osa/port/osa_include.c"
#line 1 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.c"
#line 137 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.c"
void _OS_Csem_Signal (OST_CSEM *pCSem)
{
 _OS_Flags.bEventError = 0;




 _fsr = (OS_FSR_TYPE)pCSem;
 _status &= 0x7F;
 _indf++;
 if (!_indf)
 {
 _indf--;
 _OS_Flags.bEventError = 1;
 }
#line 183 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.c"
}
#line 306 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.c"
void OS_EnterCriticalSection (void)
{
 OST_UINT temp;
 temp = OS_DI();
 _OS_Flags.bInCriticalSection = 1;

 _OS_Flags.bGIE_CTemp = 0;
 if (temp & 0x80) _OS_Flags.bGIE_CTemp = 1;

}
#line 343 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.c"
void OS_LeaveCriticalSection (void)
{
 char temp;
 _OS_Flags.bInCriticalSection = 0;
 temp = 0;
 if (_OS_Flags.bGIE_CTemp) temp |= 0x80;
  do  { if (temp & 0x80) _gie = 1; } while (0) ;
}
#line 405 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.c"
void _OS_InitDelay ( OST_UINT32  Delay)
{

  {_fsr = (OS_FSR_TYPE)_OS_CurTask ; _status &= ~0x80 ;} ;
  (( near  OST_TASK_STATE*)(&_indf))->bCanContinue  = 0;
  (( near  OST_TASK_STATE*)(&_indf))->bDelay  = 0;

 if (Delay)
 {

 Delay ^= -1;
 Delay ++;
 _OS_CurTask->Timer = Delay;

  {_fsr = (OS_FSR_TYPE)_OS_CurTask ; _status &= ~0x80 ;} ;
  (( near  OST_TASK_STATE*)(&_indf))->bDelay  = 1;
  (( near  OST_TASK_STATE*)(&_indf))->bReady  = 1;

 }
}
#line 473 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.c"
 asm("   global _OS_DI           ");
 asm("_OS_DI:                    ");
 asm("   clrf    __status        ");
 asm("   movf    __intcon, w     ");
 asm("   andlw   0x80            ");







 asm("   bcf     __intcon, 7     ");
 asm("   return                  ");
#line 575 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.c"
OST_BOOL _OS_CheckEvent (char bEvent)
{
#line 613 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.c"
 _OS_Flags.bTimeout = 0;
  {_fsr = (OS_FSR_TYPE)_OS_CurTask ; _status &= ~0x80 ;} ;
 _carry = 0;

 if (bEvent)
 {
 if ( (( near  OST_TASK_STATE*)(&_indf))->bReady )
 {

 EXIT_OK:


 _carry = 1;
 }


 _OS_Flags.bEventOK = 1;


  (( near  OST_TASK_STATE*)(&_indf))->bReady  = 1;


 if (_carry)  (( near  OST_TASK_STATE*)(&_indf))->bDelay  = 0;


 asm("   return     ");
 }

  (( near  OST_TASK_STATE*)(&_indf))->bReady  = 0;



 asm("   btfss   __indf, 4     ");
 asm("   btfss   __indf, 5     ");
 asm("   return                ");
 _OS_Flags.bTimeout = 1;
 goto EXIT_OK;
#line 661 "c:/users/jonathan/documents/robot social/osa/port/pic16/osa_pic16_htpicc.c"
}
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/timers/osa_dtimer.c"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/timers/osa_qtimer.c"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/timers/osa_stimer.c"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/timers/osa_ttimer.c"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_squeue.c"
#line 77 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_squeue.c"
 void _OS_Squeue_Send (OST_SQUEUE * pSQueue, OST_SMSG SMsg)
 {
 OST_QUEUE_CONTROL q;
 OST_UINT16 temp;

 q = pSQueue->Q;
 _OS_Flags.bEventError = 0;






 if (q.cSize == q.cFilled)
 {
 pSQueue->pSMsg[q.cBegin] = SMsg;
 q.cBegin++;
 if (q.cBegin == q.cSize) q.cBegin = 0;

 _OS_Flags.bEventError = 1;

 goto EXIT;
 }





 temp = (OST_UINT16)q.cBegin + q.cFilled;
 if (temp >= q.cSize) temp -= q.cSize;
 pSQueue->pSMsg[temp] = SMsg;
 q.cFilled++;

 EXIT:
 pSQueue->Q = q;

 }
#line 224 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_squeue.c"
 OST_SMSG _OS_Squeue_Get (OST_SQUEUE *pSQueue)
 {
 OST_QUEUE_CONTROL q;
 OST_UINT temp;
 OST_SMSG smsg_temp;

 q = pSQueue->Q;
 temp = q.cBegin;
 q.cBegin++;

 if (q.cBegin >= q.cSize) q.cBegin = 0;

 q.cFilled--;
 pSQueue->Q = q;

 smsg_temp = pSQueue->pSMsg[temp];

 return smsg_temp;
 }
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_queue.c"
#line 64 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_queue.c"
 void _OS_Queue_Send (OST_QUEUE *pQueue, OST_MSG Msg)
 {

 OST_QUEUE_CONTROL q;
 OST_UINT16 temp;

 q = pQueue->Q;
 _OS_Flags.bEventError = 0;





 if (q.cSize == q.cFilled)
 {
 pQueue->pMsg[q.cBegin] = Msg;
 q.cBegin++;
 if (q.cBegin == q.cSize) q.cBegin = 0;

 _OS_Flags.bEventError = 1;
 goto EXIT;
 }






 temp = (OST_UINT16)q.cBegin + q.cFilled;
 if (temp >= q.cSize) temp -= q.cSize;
 pQueue->pMsg[temp] = Msg;
 q.cFilled++;

 EXIT:

 pQueue->Q = q;
 }
#line 213 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_queue.c"
 OST_MSG _OS_Queue_Get (OST_QUEUE *pQueue)
 {
 OST_QUEUE_CONTROL q;
 OST_UINT temp;
 OST_MSG msg_temp;


 q = pQueue->Q;
 temp = q.cBegin;
 q.cBegin++;

 if (q.cBegin >= q.cSize) q.cBegin = 0;

 q.cFilled--;
 pQueue->Q = q;

 msg_temp = pQueue->pMsg[temp];

 return msg_temp;
 }
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_csem.c"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/events/osa_bsem.c"
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_system.c"
#line 67 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_system.c"
void OS_Init (void)
{



 *( near   OST_UINT *)&_OS_Flags = 0;


  ;
#line 326 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_system.c"
 _OS_Tasks[0].State.bEnable = 0;


 _OS_Tasks[1].State.bEnable = 0;



 _OS_Tasks[2].State.bEnable = 0;



 _OS_Tasks[3].State.bEnable = 0;
#line 379 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_system.c"
  ;
 _OS_CurTask = (OST_TASK_POINTER) _OS_Tasks;
 _OS_Temp =  4 ;
 do
 {
 _OS_TaskQueue[_OS_Temp-1] = _OS_Temp-1;
 _OS_TaskLevel[_OS_Temp-1] = 0;
 } while (--_OS_Temp);
  ;






}
#line 1 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_tasks.c"
#line 36 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_tasks.c"
 near  OST_TCB _OS_Tasks[ 4 ]  ;
#line 75 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_tasks.c"
 void _OS_Task_Create( OST_UINT  priority, OST_CODE_POINTER TaskAddr)
 {
 OST_TASK_POINTER Task;

 _OS_Flags.bError = 0;
#line 88 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_tasks.c"
 Task = (OST_TASK_POINTER)_OS_Tasks;
 _OS_Temp = 0;

 do {

 if (!Task->State.bEnable)
 {
 ((OST_TASK_STATE*)&priority)->bEnable = 1;
 ((OST_TASK_STATE*)&priority)->bReady = 1;

 Task->pTaskPointer = TaskAddr;


 Task->Timer = 0;
#line 108 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_tasks.c"
 *(( near  char*)&Task->State) = priority;
#line 126 "c:/users/jonathan/documents/robot social/osa/kernel/system/osa_tasks.c"
 _OS_Flags.bError = 0;

 return ;

 }

 Task ++;

 } while (++_OS_Temp <  4 );


 _OS_Flags.bError = 1;

 return ;
 }
