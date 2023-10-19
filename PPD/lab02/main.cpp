#include <iostream>
#include <thread>
#include <chrono>
#include <cmath>

using namespace std;

//int a[n], b[n], c[n];

void populateArray(int *a, int *b, const int n) {
    srand(time(nullptr));
    for (int i = 0; i < n; i++) {
        a[i] = rand() % 1000;
        b[i] = rand() % 1000;
    }
}

void run(int id, const int *a, const int *b, int *c, const int n, const int l) {
    int start = id * n / l;
    for (int i = start; i < start + n / l; i++)
        c[i] = sqrt(a[i]) - (sqrt(b[i]) / pow(sqrt(a[i]), 2) * pow(sqrt(b[i]), 2));
    int lastPos = start + 1 - n % l;
    if (lastPos < n)
        c[lastPos] = a[lastPos] + b[lastPos];
}

void runThreads(int *a, int *b, int *c, const int n) {
    const int l = 8;
    thread th[n];
    for (int i = 0; i < l; i++) {
        th[i] = thread(run, i, a, b, c, n, l);
    }
    for (int i = 0; i < l; i++) {
        th[i].join();
    }
}

void showArrays(int *a, int *b, int *c, const int n) {
    for (int i = 0; i < n; i++) {
        cout << a[i] << " " << b[i] << " " << c[i] << "\n";
    }
}

int main() {
    const int n = 1000000;
    int *a = new int[n];
    int *b = new int[n];
    int *c = new int[n];
    populateArray(a, b, n);
    chrono::steady_clock::time_point begin = chrono::steady_clock::now();
    runThreads(a, b, c, n);
    chrono::steady_clock::time_point end = chrono::steady_clock::now();
    cout << "Time difference = " << chrono::duration_cast<chrono::microseconds>(end - begin).count() << endl;
//    showArrays(a, b, c, n);
    return 0;
}

