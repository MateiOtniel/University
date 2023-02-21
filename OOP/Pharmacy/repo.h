#pragma once
#include "domain.h"
#include <vector>
#include <random>
#include "vectorClass.h"



class RepoMedicamente{
public:
	RepoMedicamente(){};
	RepoMedicamente(const RepoMedicamente&){}
	size_t getSize();
	const Medicament& getMedicamentind(int ind);
	vector <Medicament>& getAll();
	vector <Medicament>& getRec();
	void addMedicament(const Medicament&);
	void deleteMedicament(int id);
	void modifyMedicament(const Medicament&);
	void addMedRec(const string& nume);
	void delRec();
	void rand(int nr);
	~RepoMedicamente();
private:
	vector <Medicament> RepoMed;
	vector <Medicament> Reteta;
};

class Exception{

public:
	Exception(const string& msg): msg_(msg) {}
	~Exception() {}

	string getMessage() const {return(msg_);}
private:
	string msg_;
};