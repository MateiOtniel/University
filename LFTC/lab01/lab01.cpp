#include <iostream>
#include <cmath>
using namespace std;

void calculate_perimeter_and_area_of_circle() {
    double radius;
    cin >> radius;
    const double perimeter = 2 * 3.14 * radius;
    const double area = 3.14 * radius * radius;
    cout << "Perimeter: " << perimeter << "\n";
    cout << "Area: " << area << "\n";
}

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

void sum_of_numbers() {
    int n;
    cin >> n;
    int sum = 0;
    while (n > 0) {
        int x;
        cin >> x;
        sum += x;
        n--;
    }
    cout << "Sum: " << sum << "\n";
}

// Programul nu compileaza
void program_gresit_1() {
    int a;
    cin >> a;
    cout << a;
}

void program_gresit_2() {
    int a;
    cin >> a;
    cout << sqrt(a) << "\n";
    cout << cbrt(a);
}

int main() {
    // calculate_perimeter_and_area_of_circle();
    // cmmdc();
    // sum_of_numbers();
    // program_gresit_1();
    program_gresit_2();
    return 0;
}
