#include "ui.h"

void Console::meniu(){
	cout << "Alegeti una din functionalitatile de mai jos:\n";
	cout << "add - adauga medicament\n";
	cout << "mod - modifica medicament\n";
	cout << "del - sterge medicament\n";
	cout << "fil - filtreaza lista medicamente\n";
	cout << "sort - sorteaza lista medicamente\n";
	cout << "man - afisarea functionalitatilor\n";
	cout << "show - afisarea listei de medicamente\n";
	cout << "append - adaugare medicament dupa nume in reteta\n";
	cout << "erase - sterge reteta\n";
	cout << "rand - adauga random n medicamente existente in reteta\n";
	cout << "exp - exporteaza reteta intr-un fisier dat de la tastatura\n";
	cout << "batch - batch mode\n";
	cout << "exit - iesire program\n";
}

void Console::task1(){
	string denumire, producator, substanta, pret;
	cout << "Denumirea: ";
	getline(cin, denumire);
	cout << "Pretul: ";
	getline(cin, pret);
	cout << "Producator: ";
	getline(cin, producator);
	cout << "Substanta activa: ";
	getline(cin, substanta);
	string x;
	try{
		validator.validateMedicine(denumire, pret, producator, substanta);
		Medicament med(this->id, denumire, stoi(pret),
			producator, substanta);
		this->id++;
		srv.addMedSrv(med);
		cout << "Medicament adaugat cu succes!\n";
	}
	catch(Exception& err){
		cout << err.getMessage();
	}
	catch(string x){
		cout << x;
	}
}

void Console::task2(){
	string idstr;
	string denumire, producator, substanta, pret;
	cout << "Dati id-ul produsului ce se va modifica: ";
	getline(cin, idstr);
	cout << "Noua denumire: ";
	getline(cin, denumire);
	cout << "Noul pret: ";
	getline(cin, pret);
	cout << "Noul producator: ";
	getline(cin, producator);
	cout << "Noua substanta activa: ";
	getline(cin, substanta);
	string x;
	try{
		validator.validateMedicineWithId(idstr, denumire, pret,
			producator, substanta);
		Medicament med(stoi(idstr), denumire, stoi(pret),
			producator, substanta);
		srv.modMedSrv(med);
		cout << "Medicament modificat cu succes!\n";
	}
	catch(Exception& err){
		cout << err.getMessage();
	}
	catch(string x){
		cout << x;
	}
}

void Console::task3(){
	string idstr;
	string denumire, producator, substanta, pret;
	cout << "Dati id-ul produsului ce se va sterge: ";
	getline(cin, idstr);
	string x;
	try{
		validator.validateId(idstr);
		srv.delMedSrv(stoi(idstr));
		cout << "Medicament sters cu succes!\n";
	}
	catch(Exception& err){
		cout << err.getMessage();
	}
	catch(string x){
		cout << x;
	}
}

void Console::task5(){
	cout << "Alegeti dupa ce se va filtra lista:\n";
	cout << "1 - pret\n";
	cout << "2 - substanta activa\n";
	string cmd, filvar;
	getline(cin, cmd);
	try{
		if(cmd == "1"){
			cout << "Dati pretul:";
			getline(cin, filvar);
			validator.validateMedicine("ok", filvar, "da", "da");
			showRez(srv.filter(1, filvar));
		}
		else if(cmd == "2"){
			cout << "Dati substanta:";
			getline(cin, filvar);
			validator.validateMedicine("ok", "123", "da", filvar);
			showRez(srv.filter(2, filvar));
		}
		else
			cout << "Comanda invalida!\n";
	}
	catch(string x){
		cout << x;
	}
}

void Console::task6(){
	cout << "Alegeti dupa ce se va sorta lista:\n";
	cout << "1 - denumire\n";
	cout << "2 - producator\n";
	cout << "3 - substanta + pret\n";
	string cmd, filvar;
	getline(cin, cmd);
	if(cmd == "1"){
		showRez(srv.sort(1));
	}
	else if(cmd == "2"){
		showRez(srv.sort(2));
	}
	else if(cmd == "3"){
		showRez(srv.sort(3));
	}
	else{
		cout << "Comanda invalida!\n";
	}
}

void Console::task7(){
	string nume;
	cout << "Dati numele: ";
	getline(cin, nume);
	try{
		validator.validateMedicine(nume, "123", "a", "a");
		srv.addMedRecSrv(nume);
		cout << "Medicament adaugat cu succes in reteta!\n";
	}
	catch(Exception& err){
		cout << err.getMessage();
	}
	catch(string x){
		cout << x;
	}
}

void Console::task8(){
	srv.delRecSrv();
	cout << "Reteta stearsa cu succes!\n";
}

void Console::task9(){
	string nr;
	cout << "Introduceti numarul: ";
	getline(cin, nr);
	try{
		for(int i = 0; i < nr.size(); i++)
			if(nr[i] < '0' || nr[i] > '9')
				throw string("Numar invalid!\n");
		if(nr.size() > 4)
			throw string("Numar prea mare!\n");
		if(nr == "0")
			throw string("Numar invalid!\n");
		srv.randSrv(stoi(nr));
		cout << "Medicamente adaugate cu succes in reteta!\n";
	}
	catch(Exception& err){
		cout << err.getMessage();
	}
	catch(string x){
		cout << x;
	}
}

void Console::task10(){
	string file;
	cout << "Dati fisierul: ";
	getline(cin, file);
	try{
		validator.validateMedicine(file, "123", "a", "a");
		file += ".csv";
		srv.exportSrv(file);
	}
	catch (string x){
		cout << x;
	}
}

void Console::batch()
{
	vector<string> comenzi;
	string delim = " ";

	comenzi.push_back("add nurofen 123 ducfarm activa ");
	comenzi.push_back("mod 0 nurofen 123 ducfarm pasiva ");
	comenzi.push_back("del 1 ");
	comenzi.push_back("del 0 1 ");
	comenzi.push_back("add asd nurofem 12 sad asd ");
	comenzi.push_back("del 0 ");
	comenzi.push_back("mod ");
	comenzi.push_back("add nu 123123 dsad das");
	comenzi.push_back("del 123 ");

	vector<string> words;
	for(auto& comanda : comenzi) {
		size_t pos = 0;
		words.clear();

		while((pos = comanda.find(delim)) != string::npos) {
			words.push_back(comanda.substr(0, pos));
			comanda.erase(0, pos + delim.length());
		}

		if(words[0] == "add"){
			if(words.size() == 5){
				try{
					validator.validateMedicine(words[1], words[2],
						words[3], words[4]);
					Medicament med(this->id, words[1],
						stoi(words[2]), words[3], words[4]);
					srv.addMedSrv(med);
					this->id++;
					cout << "Medicament adaugat cu succes!\n";
				}
				catch(Exception& err){
					cout << err.getMessage();
				}
				catch(string& x){
					cout << x;
				}
			}
			else
				cout << "Comanda invalida!\n";
		}
		else if(words[0] == "mod") {
			if(words.size() == 6){
				try {
					validator.validateMedicineWithId(words[1], words[2],
						words[3], words[4], words[5]);
					Medicament med(stoi(words[1]), words[2],
						stoi(words[3]), words[4], words[5]);
					srv.modMedSrv(med);
					cout << "Medicament modificat cu succes!\n";
				}
				catch(Exception& err){
					cout << err.getMessage();
				}
				catch(string& ex){
					cout << ex;
				}
			}
			else
				cout << "Comanda invalida!\n";
		}
		else if(words[0] == "del"){
			if(words.size() == 2){
				try{
					validator.validateId(words[1]);
					srv.delMedSrv(stoi(words[1]));
					cout << "Medicament sters cu succes!\n";
				}
				catch(Exception& err){
					cout << err.getMessage();
				}
				catch(string& ex){
					cout << ex;
				}
			}
			else cout << "Comanda invalida!\n";
		}
		else cout << "Comanda invalida!\n";
	}
}

void Console::showRez(vector <Medicament>* rez){
	if(rez->size() == 0)
		cout << "Nu exista medicamente!\n";
	for(int i = 0; i < rez->size(); i++)
		showMed(rez->at(i));
	delete rez;
}

void Console::showMed(Medicament& m){
	cout << "Id: " << m.getId() << " Denumire: " << m.getDenumire() << " Pret: " << m.getPret() <<
		" Producator: " << m.getProducator() << " Substanta activa: " << m.getSubstanta() << '\n';
}

void Console::showAll(){
	if(srv.srvgetAll().size() == 0)
		cout << "Nu exista medicamente!\n";
	for(int i = 0; i < srv.srvgetAll().size(); i++)
		showMed(srv.srvgetAll().at(i));
}

void Console::consola(){
	this->meniu();
	string cmd;
	while(true){
		cout << ">>>";
		getline(cin, cmd);
		if(cmd == "add")
			this->task1();
		else if(cmd == "mod")
			this->task2();
		else if(cmd == "del")
			this->task3();
		else if(cmd == "fil")
			this->task5();
		else if(cmd == "sort")
			this->task6();
		else if(cmd == "man")
			this->meniu();
		else if(cmd == "show")
			this->showAll();
		else if(cmd == "append")
			this->task7();
		else if(cmd == "erase")
			this->task8();
		else if(cmd == "rand")
			this->task9();
		else if(cmd == "exp")
			this->task10();
		else if(cmd == "batch")
			this->batch();
		else if(cmd == "exit"){
			cout << "Ati iesit din program!\n";
			return;
		}
		else
			cout << "Comanda invalida!\n";
	}
}

Console::~Console(){
}