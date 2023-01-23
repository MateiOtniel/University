#include "repo.h"

size_t RepoMedicamente::getSize(){
	return this->RepoMed.size();
}

const Medicament& RepoMedicamente::getMedicamentind(int ind){
	return RepoMed[ind];
}

vector <Medicament>& RepoMedicamente::getAll(){
	return RepoMed;
}

vector<Medicament>& RepoMedicamente::getRec(){
	return Reteta;
}

void RepoMedicamente::addMedicament(const Medicament& m){
	
	for(auto& med : RepoMed)
		if(med == m){
			throw (Exception("Medicament deja existent!\n"));
		}
	this->RepoMed.push_back(m);
}

void RepoMedicamente::deleteMedicament(int id){
	int ok = 0, i = -1;
	for(auto& med : RepoMed){
		i++;
		if(med.getId() == id){
			RepoMed.erase(RepoMed.begin() + i);
			ok = 1;
			break;
		}
	}
	if(!ok)
		throw (Exception("Medicament inexistent!\n"));
}

void RepoMedicamente::modifyMedicament(const Medicament& m){
	int ok = 0, i = -1;
	for(auto& med : RepoMed){
		i++;
		if(med.getId() == m.getId()){
			ok = 1;
			RepoMed[i] = m;
			break;
		}
	}
	if(!ok)
		throw (Exception("Medicament inexistent!\n"));
}

void RepoMedicamente::addMedRec(const string& nume){
	int ok = 0;
	for(auto& med : RepoMed)
		if(med.getDenumire() == nume){
			ok = 1;
			Reteta.push_back(med);
		}
	if(!ok)
		throw (Exception("Medicament inexistent!\n"));
}

void RepoMedicamente::delRec(){
	Reteta.clear();
}

void RepoMedicamente::rand(int nr){
	if(RepoMed.size() == 0)
		throw ((Exception)("Nu exista medicamente de adaugat in reteta!\n"));
	std::mt19937 mt{std::random_device{}()};
	std::uniform_int_distribution<> dist(0, int(RepoMed.size()) - 1);

	for(int i = 0; i < nr; i++){
		int val = dist(mt);
		Reteta.push_back(RepoMed[val]);
	}
}

RepoMedicamente::~RepoMedicamente(){
}
