#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
#include "validator.h"
#include "ui.h"
#include "service.h"
#include "enitities.h"

void show(){
    /*
    Functia afiseaza elementele din vectorul dinamic.
    Input: -
    Output: -
    */
    for(int i = 0; i < v->length; i++)
        printf("Zi: %d Suma: %d Tip: %s\n", v->expenses[i].zi, v->expenses[i].suma, v->expenses[i].tip);
}

void task_1(){
    char zi[20] = "", suma[20] = "", tip[20] = "";
    printf("Dati ziua (1 - 31): ");
    scanf_s("%s", &zi, 20);
    printf("Dati suma: ");
    scanf_s("%s", &suma, 20);
    printf("Dati tipul de cheltuiala: ");
    scanf_s("%s", &tip, 20);
    if(validate_expense(zi, suma, tip)){
        Expense ex = create_expense(atoi(zi), atoi(suma), tip, 1);
        free(ex.tip);
    }
    else
        printf("Date invalide!\n");
}

void task_2(){
    char zi[20] = "", suma[20] = "", tip[20] = "";
    printf("Dati ziua (1 - 31): ");
    scanf_s("%s", &zi, 20);
    printf("Dati noua suma: ");
    scanf_s("%s", &suma, 20);
    printf("Dati tipul de cheltuiala: ");
    scanf_s("%s", &tip, 20);
    if(validate_expense(zi, suma, tip)){
        Expense ex = create_expense(atoi(zi), atoi(suma), tip, 2);
        free(ex.tip);
    }
    else
        printf("Date invalide!\n");
}

void task_3(){
    char zi[20] = "", suma[20] = "0", tip[20] = "";
    printf("Dati ziua (1 - 31): ");
    scanf_s("%s", &zi, 20);
    printf("Dati tipul de cheltuiala: ");
    scanf_s("%s", &tip, 20);
    if(validate_expense(zi, suma, tip)){
        Expense ex = create_expense(atoi(zi), atoi(suma), tip, 3);
        free(ex.tip);
    }
    else
        printf("Date invalide!\n");
}

void task_4(){
    char criteriu[20];
    char zi[20] = "1", suma[20] = "1", tip[20] = "altele";
    printf("Alege criteriul de filtrare (zi/suma/tip): ");
    scanf_s("%s", &criteriu, 20);
    if(criteriu[strlen(criteriu)] == '\0'){
        if(strcmp(criteriu, "zi") == 0){
            printf("Dati ziua: ");
            scanf_s("%s", &zi, 20);
            if(validate_expense(zi, suma, tip))
                filter(zi, 1);
            else
                printf("Date invalide!\n");
        }
        else if(strcmp(criteriu, "suma") == 0){
            printf("Dati suma: ");
            scanf_s("%s", &suma, 20);
            if(validate_expense(zi, suma, tip))
                filter(suma, 2);
            else
                printf("Date invalide!\n");
        }
        else if(strcmp(criteriu, "tip") == 0){
            printf("Dati tipul: ");
            scanf_s("%s", &tip, 20);
            if(validate_expense(zi, suma, tip))
                filter(tip, 3);
            else
                printf("Date invalide!\n");
        }
        else
            printf("Date invalide!\n");
    }
}

void task_5(){
    char criteriu[20], mod[20];
    printf("Alege criteriul de sortare (zi/suma): ");
    scanf_s("%s", &criteriu, 20);
    printf("Alege modul de sortare (a/d): ");
    scanf_s("%s", &mod, 20);
    if(criteriu[strlen(criteriu)] == '\0' && mod[strlen(mod)] == '\0'){
        if((!strcmp(criteriu, "zi") || !strcmp(criteriu, "suma")) && (!strcmp(mod, "a") || !strcmp(mod, "d"))){
            sort_expenses(criteriu, mod, compara_cantitate);
            show();
        }
        else
            printf("Date invalide\n");
    }
}


void show_fil(){
    for(int i = 0; i < v->length; i++)
        if(v->list[i] == 1)
            printf("Zi: %d Suma: %d Tip: %s\n", v->expenses[i].zi, v->expenses[i].suma, v->expenses[i].tip);
}

void console(){

    printf("Alege ce sa faca programul:\n");
    printf("add - Adaugarea de cheltuieli noi (cand familia cumpara ceva sau plateste o factura)\n");
    printf("mod - Modificarea unei cheltuieli existente (ziua, tipul, suma)\n");
    printf("del - Stergere cheltuiala\n");
    printf("fil - Vizualizare lista de cheltuieli filtrat dupa o proprietate (suma, ziua, tipul)\n");
    printf("sort - Vizualizare lista de cheltuieli ordonat dupa suma sau tip (crescator/descrescator)\n");
    printf("show - afisare lista\n");
    printf("exit - iesire program\n");

    char cmd[20];
    while(true)
    {
        printf(">>>");
        scanf_s("%s", &cmd, 20);
        if(cmd[strlen(cmd)] == '\0'){
            if(!strcmp(cmd, "exit")) {
                printf("Am iesit din program!");
                return;
            }
            else if(!strcmp(cmd, "show"))
                show();
            else if(!strcmp(cmd, "add"))
                task_1();
            else if(!strcmp(cmd, "mod"))
                task_2();
            else if(!strcmp(cmd, "del"))
                task_3();
            else if(!strcmp(cmd, "fil")){
                task_4();
                show_fil();
            }
            else if(!strcmp(cmd, "sort"))
                task_5();
            else
                printf("Comanda invalida!\n");
        }
    }
    return;
}