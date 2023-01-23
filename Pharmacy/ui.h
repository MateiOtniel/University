#pragma once
#include "service.h"
#include "validator.h"
#include "repo.h"
#include "vectorClass.h"
#include <iostream>

class Console{
	friend class Service;
	friend class Validator;
public:
	Console(Service& srv, Validator& validator):srv{srv}, validator{validator} {}
	void consola();
	void meniu();
	void task1();
	void task2();
	void task3();
	void task5();
	void task6();
	void task7();
	void task8();
	void task9();
	void task10();
	void batch();
	void showRez(vector <Medicament>*);
	void showMed(Medicament& m);
	void showAll();
	~Console();
private:
	int id = 0;
	Service& srv;
	Validator& validator;
};