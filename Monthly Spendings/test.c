#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <stdlib.h>
#include "test.h"
#include "validator.h"
#include "repo.h"
#include "service.h"
#include "enitities.h"
#include "ui.h"

void test_validator(){
    /*
    Functia testeaza validatorul pentru comanda de intrare.
    Input: cmd
    Output: 1 - valid / 0 - invalid
    */
    assert(validate_cmd("123") == 0);
    assert(validate_cmd("1") == 1);
    assert(validate_cmd("sa") == 0);
    assert(validate_cmd("-21") == 0);
    assert(validate_cmd("") == 0);
    assert(validate_cmd("1a") == 0);
    assert(validate_cmd("a") == 0);
}

void test_validate_expense(){
    /*
    Functia testeaza validatorul pentru cheltuieli.
    Input: char zi, char suma, char mancare.
    Output: 1 - valid / 0 - invalid
    */
    assert(validate_expense("150", "300", "mancare") == 0);
    assert(validate_expense("12", "300", "mancare") == 1);
    assert(validate_expense("31", "2", "mancare") == 1);
    assert(validate_expense("12a", "300", "transport") == 0);
    assert(validate_expense("a12", "300", "mancare") == 0);
    assert(validate_expense("-3", "300", "mancare") == 0);
    assert(validate_expense("145", "300", "transport") == 0);
    assert(validate_expense("12", "-2", "mancar") == 0);
    assert(validate_expense("12", "", "mancare") == 0);
    assert(validate_expense("12", "100", "transpo") == 0);
}

void test_create_expense(){
    /*
    Functia valideaza creatorul de cheltuieli.
    Input: Expense ex
    Output: Expense ex
    */
    Expense ex = create_expense(10, 120, "mancare", 0);
    assert(ex.suma == 120);
    assert(ex.zi == 10);
    assert(strcmp(ex.tip, "mancare") == 0);
    free(ex.tip);
    ex = create_expense(15, 130, "telefon", 1);
    assert(v->length == 16);
    free(ex.tip);
    ex = create_expense(15, 140, "telefon", 2);
    free(ex.tip);
    ex = create_expense(15, 0, "telefon", 3);
    assert(v->length == 15);
    free(ex.tip);
}

void test_add_expense(){
    /*
    Functia testeaza adaugarea cheltuielilor.
    Input: Expense ex
    Output: -
    */
    Expense ex1 = create_expense(10, 120, "mancare", 0);
    Expense ex2 = create_expense(10, 130, "telefon", 0);
    add_expense(ex1);
    assert(v->length == 16);
    add_expense(ex1);
    assert(v->length == 16);
    add_expense(ex2);
    assert(v->length == 17);
    free(ex1.tip);
    free(ex2.tip);
}

void test_modify_expense(){
    /*
    Functia testeaza modificarea cheltuielilor.
    Input: Expense ex
    Output: -
    */
    Expense ex = create_expense(10, 199, "mancare", 0);
    modifify_expense(ex);
    assert(v->expenses[15].suma == ex.suma);
    Expense ex1 = create_expense(10, 100, "telefon", 0);
    modifify_expense(ex1);
    assert(v->expenses[15].suma != ex1.suma);
    free(ex.tip);
    free(ex1.tip);
}

void test_delete_expense(){
    /*
    Functia testeaza modificarea cheltuielilor.
    Input: -
    Output: -
    */
    Expense ex1 = create_expense(10, 0, "mancare", 0);
    Expense ex2 = create_expense(10, 0, "telefon", 0);
    delete_expense(ex1);
    assert(v->length == 16);
    delete_expense(ex1);
    assert(v->length == 16);
    delete_expense(ex2);
    assert(v->length == 15);
    free(ex1.tip);
    free(ex2.tip);
}

void test_filter(){
    /*
    Se testeaza functia de filtare.
    Input: -
    Output: -
    */
    int cerinta = 1;
    filter("20", cerinta);
    assert(v->list[0] == 1);
    assert(v->list[2] == 0);
    assert(v->list[3] == 0);

    cerinta = 2;
    filter("1000", cerinta);
    assert(v->list[0] == 1);
    assert(v->list[2] == 0);
    assert(v->list[3] == 0);

    cerinta = 3;
    filter("telefon", cerinta);
    assert(v->list[0] == 1);
    assert(v->list[3] == 1);
    assert(v->list[4] == 1);
    assert(v->list[5] == 0);
}

void test_sort_expenses(){
    sort_expenses("zi", "d", compara_cantitate);
    assert(v->expenses[0].zi == 24 && v->expenses[0].suma == 1040);
    sort_expenses("zi", "a", compara_cantitate);
    assert(v->expenses[0].zi == 20 && v->expenses[0].suma == 1000);
    sort_expenses("suma", "d", compara_cantitate);
    assert(v->expenses[0].zi == 24 && v->expenses[0].suma == 1040);
    sort_expenses("suma", "a", compara_cantitate);
    assert(v->expenses[0].zi == 20 && v->expenses[0].suma == 1000);
}

void test_all(){
    /*
    Se testeaza toate functionalitatile.
    */
    printf("Am inceput testele...\n");
    test_validator();
    test_validate_expense();
    test_create_expense();
    test_add_expense();
    test_modify_expense();
    test_delete_expense();
    test_filter();
    test_sort_expenses();
    printf("Am terminat testele...\n");
}