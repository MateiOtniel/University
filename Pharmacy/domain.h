#pragma once
#include <string>

using namespace std;

class Medicament{
public:
	Medicament(): id(0), denumire(""), pret(0), producator(""), substanta("") {}
	Medicament(int, const std::string&, double, const std::string&, const std::string&);
	
	int getId() const;
	const string& getDenumire() const;
	double getPret() const;
	const string& getProducator() const;
	const string& getSubstanta() const;

	bool operator==(const Medicament&);

	void setDenumire(const string&);
	void setPret(double);
	void setProducator(const string&);
	void setSubstanta(const string&);

	~Medicament();

private:
	int id;
	string denumire;
	double pret;
	string producator;
	string substanta;
};
