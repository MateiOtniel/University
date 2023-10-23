#include <iostream>

using namespace std;

void calculate_perimeter_and_area_of_circle() {
    double radius;
    cin >> radius;
    double perimeter = 2 * 3.14 * radius;
    double area = 3.14 * radius * radius;
    cout << "Perimeter: " << perimeter << "\n";
    cout << "Area: " << area << "\n";
}


int main() {
    calculate_perimeter_and_area_of_circle();
    return 0;
}

