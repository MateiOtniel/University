#include <stdlib.h> 
#include <string.h>
#include <stdio.h>
#include "service.h"
#include "repo.h"

Expense create_expense(int zi, int suma, char* tip, int cerinta){
	/*
	Functia creeaza cheltuiala si o trimite spre adaugare, modificare, stergere, in functie de cerinta.
	Input: int* zi, int* suma, char* tip, int* cerinta.
	Output: Expense expense.
	*/
	Expense expense;
	expense.zi = zi;
	expense.suma = suma;
	expense.tip = calloc(strlen(tip) + 1, sizeof(char));
	if(expense.tip != NULL)
		strcpy_s(expense.tip, strlen(tip) + 1, tip);
	if(cerinta == 1)
		add_expense(expense);
	else if(cerinta == 2)
		modifify_expense(expense);
	else if(cerinta == 3)
		delete_expense(expense);
	return expense;
}

DynamicVector* createDynamicVector(){
	/*
	Functia creeaza vectorul dinamic v si ii aloca spatiu necesar.
	Input: -
	Output: -
	*/
	v = malloc(sizeof(DynamicVector));
	if(v != NULL){
		v->expenses = malloc(1 * sizeof(Expense));
		if (NULL != v->expenses){
			v->expenses[0].tip = NULL;
		}
		v->list = malloc(1 * sizeof(int));
		v->capacity = 1;
		v->length = 0;
	}
	return v;
}

void destruct(){
	/*
	Functia distruge vectorul dinamic v si curata memoria.
	Input: DynamicVector* v
	Output: -
	*/
	for (int index = 0; index < v->length; index = index + 1){
		free(v->expenses[index].tip);
	}
	free(v->list);
	free(v->expenses);
	free(v);
}

void clear(){
	/*
	Functia curata vectorul pt. filtare / sortare pt. a putea fi refolosit.
	Input: DynamicVector* v
	Outpuy: -
	*/
	for(int i = 0; i < v->length; i++)
		v->list[i] = 0;
}

void filter(char* var, int cerinta){
	/*
	Functia filtreaza lista in functie de zi, suma sau tipul cheltuielii.
	Input: char* var, int* cerinta
	Output: -
	*/
	clear();
	int ivar = 0;
	if(cerinta < 3)
		ivar = atoi(var);
	for(int i = 0; i < v->length; i++)
		if((cerinta == 1 && v->expenses[i].zi == ivar) || (cerinta == 2 && v->expenses[i].suma == ivar) || (cerinta == 3 && strcmp(v->expenses[i].tip, var) == 0))
			v->list[i] = 1;
}

void generate_expenses(){
	/*
	Functia genereaza 15 cheltuieli diferite.
	Input: -
	Output: -
	*/
	int zi = 20, suma = 1000;
	Expense aux;
	for(int i = 0; i < 5; i++){
		aux = create_expense(zi, suma, "telefon", 1);
		free(aux.tip);
		zi++;
		suma += 10;
	}
	zi = 20, suma = 1000;
	for(int i = 0; i < 5; i++){
		aux = create_expense(zi, suma, "mancare", 1);
		free(aux.tip);
		zi++;
		suma += 10;
	}
	zi = 20, suma = 1000;
	for(int i = 0; i < 5; i++){
		aux = create_expense(zi, suma, "altele", 1);
		free(aux.tip);
		zi++;
		suma += 10;
	}
}

int compara_cantitate(char* criteriu, char* mod, int i, int j)
{
	/*
	Functia compara cheltuiala in functie de criteriul dat de la tastatura.
	Input: char* criteriu, char* mod, int i, int j
	Output: -
	*/
	if(!strcmp(criteriu, "zi")){
		if(!strcmp(mod, "a") && v->expenses[i].zi > v->expenses[j].zi)
			return 1;
		else if(!strcmp(mod, "d") && v->expenses[i].zi < v->expenses[j].zi)
			return 1;
	}
	else{
		if(!strcmp(mod, "a") && v->expenses[i].suma > v->expenses[j].suma)
			return 1;
		else if(!strcmp(mod, "d") && v->expenses[i].suma < v->expenses[j].suma)
			return 1;
	}
	return 0;
}

void swap(Expense* ex1, Expense* ex2){
	/*
	Functia schimba cele 2 cheltuieli pt sortare.
	Input: Expense* ex1, Expense* ex2
	Output: -
	*/
	Expense* aux = malloc(sizeof(Expense));
	if(aux != NULL){
		*aux = *ex1;
		*ex1 = *ex2;
		*ex2 = *aux;
	}
	free(aux);
}

void sort_expenses(char* criteriu, char* mod, cmp_function cmp){
	/*
	Functia sorteaza lista dupa un anumit criteriu.
	Input: char* criteriu, char* mod
	Output: -
	*/
	for(int i = 0; i < v->length - 1; i++)
		for(int j = i + 1; j < v->length; j++)
			if(cmp(criteriu, mod, i, j) == 1)
				swap(&v->expenses[i], &v->expenses[j]);
}