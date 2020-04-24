/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   app.h
 * Author: maxos
 *
 * Created on 22. April 2020, 08:32
 */
#ifndef APP_H_INCLUDED
#define APP_H_INCLUDED


// declarations

enum AppState {
    IDLE = 0,
    OK1, OK2, OPEN,
    ERR1, ERR2, ERROR
};

enum AppButtonState {
    NOTPRESSED = 0, SHORTPRESSED, LONGPRESSED
};

struct ErrorCounters {
    uint16_t cntWrongState;  
    uint16_t cntWrongButtState;
};


struct App {
  enum AppState state;
  enum AppButtonState buttonState;
  struct ErrorCounters err;
};



extern volatile struct App app;


// defines

#define APP_EVENT_BUTTSTATE_CHANGED 0x01
#define APP_EVENT_SW1_SHORT_PRESSED 0x02
#define APP_EVENT_SW1_LONG_PRESSED  0x04

#define APP_EVENT_STATE_CHANGED     0x08
#define APP_EVENT_4   0x10
#define APP_EVENT_5   0x20
#define APP_EVENT_6   0x40
#define APP_EVENT_7   0x80


// functions

void app_init ();
void app_main ();

void app_task_1ms   ();
void app_task_2ms   ();
void app_task_4ms   ();
void app_task_8ms   ();
void app_task_16ms  ();
void app_task_32ms  ();
void app_task_64ms  ();
void app_task_128ms ();

#endif // APP_H_INCLUDED