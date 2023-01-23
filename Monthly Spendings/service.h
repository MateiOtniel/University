#pragma once
#include "enitities.h"

Expense create_expense(int zi, int suma, char* tip, int cerinta);
DynamicVector* createDynamicVector();
typedef int (cmp_function)(char* criteriu, char* mod, int i, int j);
void destruct();
void clear();
void filter(char*, int);
void generate_expenses();
void swap(Expense* ex1, Expense* ex2);
int compara_cantitate(char* criteriu, char* mod, int i, int j);
void sort_expenses(char* criteriu, char* mod, cmp_function cmp);