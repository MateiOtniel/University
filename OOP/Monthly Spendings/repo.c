#include <stdlib.h>
#include <string.h>
#include "service.h"
#include "enitities.h"

void resize_vector(){
	/*
	Functia mareste dimensiunea vectorului dinamic daca spatiul alocat acestuia se ocupa.
	Input: DynamicVector* v
	Output: -
	*/
	int nCap = 2 * v->capacity, i;
	Expense* nEx = calloc(nCap, sizeof(Expense));
	if (nEx != NULL && v->capacity < nCap){
		for (i = 0; i < v->capacity; i++)
			nEx[i] = v->expenses[i];
		for (i = v->capacity; i < nCap - 1; i = i + 1){
			nEx[i].tip = NULL;
		}
	}
	free(v->expenses);
	free(v->list);
	v->list = malloc(nCap * sizeof(int));
	v->expenses = nEx;
	v->capacity = nCap;
}

void add_expense(Expense ex){
	/*
	Functia adauga cheltuiala la vectorul dinamic.
	Input: DynamicVector* v, Expense ex, int mod_afis
	Output: - 
	*/
	int ok = 0;
	for(int i = 0; i < v->length; i++)
		if(v->expenses[i].zi == ex.zi && !strcmp(v->expenses[i].tip, ex.tip)){
			v->expenses[i].suma += ex.suma;
			ok = 1;
			break;
		}
	if(!ok){
		if(v->length == v->capacity)
			resize_vector(v);
		v->expenses[v->length].zi = ex.zi;
		v->expenses[v->length].suma = ex.suma;
		v->expenses[v->length].tip = calloc(strlen(ex.tip) + 1, sizeof(char));
		if (NULL != v->expenses[v->length].tip){
			strcpy_s(v->expenses[v->length].tip, strlen(ex.tip) + 1, ex.tip);
		}
		v->length++;
	}
}

void modifify_expense(Expense ex){
	/*
	Functia modifica cheltuiala, daca exista, in vectorul dinamic.
	Input: DynamicVector* v, Expense ex, int mod_afis
	Output: -
	*/
	for(int i = 0; i < v->length; i++)
		if(v->expenses[i].zi == ex.zi && !strcmp(v->expenses[i].tip, ex.tip)){
			v->expenses[i].suma = ex.suma;
			break;
		}
}

void delete_expense(Expense ex){
	/*
	Functia sterge cheltuiala, daca exista, in vectorul dinamic.
	Input: DynamicVector* v, Expense ex, int mod_afis
	Output: -
	*/
	int j;
	for(int i = 0; i < v->length; i++)
		if(v->expenses[i].zi == ex.zi && !strcmp(v->expenses[i].tip, ex.tip)){
			for(j = i; j < v->length - 1; j++){
				v->expenses[j].zi = v->expenses[j + 1].zi;
				v->expenses[j].suma = v->expenses[j + 1].suma;
				strcpy_s(v->expenses[j].tip, strlen(v->expenses[j + 1].tip) + 1, v->expenses[j + 1].tip);
			}
			free(v->expenses[j].tip);
			v->length--;
			break;
		}
}