#pragma once
typedef struct{
	int zi;
	int suma;
	char* tip;
}Expense;

typedef struct{
	Expense* expenses;
	int* list;
	int length;
	int capacity;
}DynamicVector;

DynamicVector* v;