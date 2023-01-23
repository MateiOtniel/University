#pragma once
#include "repo.h"
#include <iostream>
#include <fstream>

class Service{
public:
	Service(RepoMedicamente& repo): repo{repo} {}
	void addMedSrv(const Medicament&);
	void modMedSrv(const Medicament&);
	void delMedSrv(int id);
	vector <Medicament>* filter(int cmd, const string&);
	void swap(Medicament* a, Medicament* b);
	bool ComparaDenumire(vector <Medicament>* out, int i, int j);
	bool ComparaProducator(vector <Medicament>* out, int i, int j);
	bool ComparaSubstPret(vector <Medicament>* out, int i, int j);
	vector <Medicament>* sort(int);
	vector <Medicament>& srvgetAll();

	void addMedRecSrv(const string& nume);
	void delRecSrv();
	void randSrv(int nr);
	void exportSrv(const string& file);
	~Service();
private:
	RepoMedicamente& repo;
};