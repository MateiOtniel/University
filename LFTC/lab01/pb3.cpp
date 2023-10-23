#include <iostream>

using namespace std;


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

int main() {
    sum_of_numbers();
    return 0;
}
