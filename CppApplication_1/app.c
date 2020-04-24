/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



#include <stdio.h>
#include <string.h>


#include "app.h"
#include "sys.h"

// defines
// ...

#define LED_LIFE         0
#define LED_SYSTEM_READY 1
#define LED_ERROR        2
#define LED_OPENLOCK     3

#define SW1_PRESSED ( (PINC & (1 << PC7)) == 0)

#define TIMEOUT_2s 2000
#define TIMEOUT_3s 3000
#define TIMEOUT_5s 5000


// declarations and definations

volatile struct App app;


// functions

void app_init () {
    memset((void *)&app, 0, sizeof(app));
}


//--------------------------------------------------------

void app_main () {
    
    switch (app.buttonState) {
        case NOTPRESSED:   printf("%14s", "NOTPRESSED"); break;
        case LONGPRESSED:  printf("%14s", "LONGPRESSED"); break;
        case SHORTPRESSED: printf("%14s", "SHORTPRESSED"); break;
        default: printf("? (%3d)%7s", app.buttonState, ""); break;
    }
    switch (app.state) {
        case IDLE:  printf("%7s", "IDLE"); break;
        case OK1:   printf("%7s", "OK1"); break;
        case OK2:   printf("%7s", "OK2"); break;
        case OPEN:  printf("%7s", "OPEN"); break;
        case ERR1:  printf("%7s", "ERR1"); break;
        case ERR2:  printf("%7s", "ERR2"); break;
        case ERROR: printf("%7s", "ERROR"); break;
        default:    printf("? (%3d)", app.state); break;
    }
    printf("   \r");
}

//--------------------------------------------------------

void app_task_1ms () {

    enum AppState nextState = app.state;
    static uint16_t timer = 0;
    
    switch (app.state) {
        case IDLE: {
            sys_setLed(LED_SYSTEM_READY, 1);
            sys_setLed(LED_ERROR, 0);
            sys_setLed(LED_OPENLOCK, 0);
            if (sys_clearEvent(APP_EVENT_SW1_SHORT_PRESSED)) {
                nextState = OK1;
                timer = TIMEOUT_5s;
            }
            if (sys_clearEvent(APP_EVENT_SW1_LONG_PRESSED)) {
                nextState = ERR1;
                timer = TIMEOUT_5s;
            }
            break;
        }
        
        case OK1: {
            sys_setLed(LED_SYSTEM_READY, 0);
            sys_setLed(LED_ERROR, 0);
            sys_setLed(LED_OPENLOCK, 0);
            if (timer > 0) {
                timer--;
            } else {
                nextState = IDLE;
            }
            if (sys_clearEvent(APP_EVENT_SW1_SHORT_PRESSED)) {
                nextState = ERR2;
                timer = TIMEOUT_5s;
            }
            if (sys_clearEvent(APP_EVENT_SW1_LONG_PRESSED)) {
                nextState = OK2;
                timer = TIMEOUT_5s;
            }
            break;
        }
        
        case OK2: {
            sys_setLed(LED_SYSTEM_READY, 0);
            sys_setLed(LED_ERROR, 0);
            sys_setLed(LED_OPENLOCK, 0);
            if (timer > 0) {
                timer--;
            } else {
                nextState = IDLE;
            }
            if (sys_clearEvent(APP_EVENT_SW1_SHORT_PRESSED)) {
                nextState = OPEN;
                timer = TIMEOUT_3s;
            }
            if (sys_clearEvent(APP_EVENT_SW1_LONG_PRESSED)) {
                nextState = ERROR;
                timer = TIMEOUT_2s;
            }
            break;
        }
        
        case OPEN: {
            sys_setLed(LED_SYSTEM_READY, 0);
            sys_setLed(LED_ERROR, 0);
            sys_setLed(LED_OPENLOCK, 1);
            if (timer > 0) {
                timer--;
            } else {
                nextState = IDLE;
            }
            break;
        }
        
        case ERR1: {
            sys_setLed(LED_SYSTEM_READY, 0);
            sys_setLed(LED_ERROR, 0);
            sys_setLed(LED_OPENLOCK, 0);
            if (timer > 0) {
                timer--;
            } else {
                nextState = IDLE;
            }
            if (sys_clearEvent(APP_EVENT_SW1_SHORT_PRESSED) ||
                sys_clearEvent(APP_EVENT_SW1_LONG_PRESSED)) {
                nextState = ERR2;
                timer = TIMEOUT_5s;
            }
            break;
        }
        
        case ERR2: {
            sys_setLed(LED_SYSTEM_READY, 0);
            sys_setLed(LED_ERROR, 0);
            sys_setLed(LED_OPENLOCK, 0);
            if (timer > 0) {
                timer--;
            } else {
                nextState = IDLE;
            }
            if (sys_clearEvent(APP_EVENT_SW1_SHORT_PRESSED) ||
                sys_clearEvent(APP_EVENT_SW1_LONG_PRESSED)) {
                nextState = ERROR;
                timer = TIMEOUT_2s;
            }
            break;
        }
        
        case ERROR: {
            sys_setLed(LED_SYSTEM_READY, 0);
            sys_setLed(LED_ERROR, 1);
            sys_setLed(LED_OPENLOCK, 0);
            if (timer > 0) {
                timer--;
            } else {
                nextState = IDLE;
            }
            break;
        }
        
        default: {
            nextState = ERROR;
            if (app.err.cntWrongState < 0xffff) {
                app.err.cntWrongState++;
            }
            break;
        }
        
    }
    if (nextState != app.state) {
        sys_setEvent(APP_EVENT_STATE_CHANGED);
        app.state = nextState;
    }
    

}


void app_task_2ms () {

    enum AppButtonState nextState = app.buttonState;
    static uint16_t timer = 0;
    
    switch (app.buttonState) {
        
        case NOTPRESSED: {
            if (SW1_PRESSED) {
                nextState = SHORTPRESSED;
                timer = 0;
            }
            break;
        }
        
        case SHORTPRESSED:  {
            timer++;
            if (timer >= 250) {
                nextState = LONGPRESSED;
            }
            if (!SW1_PRESSED) {
                nextState = NOTPRESSED;
                sys_setEvent(APP_EVENT_SW1_SHORT_PRESSED);
            }
            break;
        }
        
        case LONGPRESSED:  {
            if (!SW1_PRESSED) {
                nextState = NOTPRESSED;
                sys_setEvent(APP_EVENT_SW1_LONG_PRESSED);
            }
            break;
        }
        
        default: {
            nextState = NOTPRESSED;
            if (app.err.cntWrongButtState < 0xffff) {
                app.err.cntWrongButtState++;
            }
            break;
        }
    }

    if (nextState != app.buttonState) {
        sys_setEvent(APP_EVENT_BUTTSTATE_CHANGED);
        app.buttonState = nextState;    
    }
}


void app_task_4ms () {}
void app_task_8ms () {}
void app_task_16ms () {}
void app_task_32ms () {}
void app_task_64ms () {}

void app_task_128ms () {
    static uint8_t timer = 0;

    uint8_t err = 0;
    if (app.err.cntWrongButtState > 0) { err = 1; }
    if (app.err.cntWrongState > 0) { err = 1; }
    
    
    timer++;
    if (err || timer >= 1) {
        timer = 0;
        sys_toggleLed(0);
    }
    
}


