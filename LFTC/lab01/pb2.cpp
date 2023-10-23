#include <iostream>

using namespace std;


void cmmdc() {
    int a, b;
    cin >> a >> b;
    while (a != b) {
        if (a > b) {
            a -= b;
        }
        else {
            b -= a;
        }
    }
    cout << "Cmmdc: " << a << "\n";
}

int main() {
    cmmdc();
    return 0;
}
