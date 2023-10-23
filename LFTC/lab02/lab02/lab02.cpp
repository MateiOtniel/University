#include <fstream>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

void readFromInputAndCreateLexicalAtoms() {
    ifstream fin("program.txt");
    ofstream fout("atoms.txt");
    if (!fin.is_open() || !fout.is_open()) {
        cerr << "Failed to open files." << std::endl;
        return;
    }
    vector<string> atoms;
    string line;
    while (getline(fin, line)) {
        istringstream iss(line);
        string element;
        while (iss >> element) {
            atoms.push_back(element);
        }
    }
    fin.close();
    for (const string& element : atoms) {
        fout << element << '\n';
    }
    fout.close();
}

int main() {
    readFromInputAndCreateLexicalAtoms();
    return 0;
}
