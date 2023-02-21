#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "validator.h"
#include "enitities.h"

int validate_cmd(char* cmd){
    if(strlen(cmd) != 1)
        return 0;
    if(cmd[0] < '1' || cmd[0] > '5')
        return 0;
    return 1;
}

int validate_expense(char* zi, char* suma, char* tip){
    if(strlen(zi) > 2 || !strcmp(zi, ""))
        return 0;
    for(int i = 0; i < strlen(zi); i++)
        if(zi[i] < '0' || zi[i] > '9')
            return 0;
    for(int i = 0; i < strlen(suma); i++)
        if(suma[i] < '0' || suma[i] > '9')
            return 0;
    if(atoi(suma) < 0 || !strcmp(suma, ""))
        return 0;
    if(!strcmp(tip, "mancare") || !strcmp(tip, "transport") || !strcmp(tip, "telefon") || !strcmp(tip, "imbracaminte") || !strcmp(tip, "altele"))
        return 1;
    else
        return 0;
}