#include <iostream>
#include <iomanip>
#include <cmath>
using namespace std;

// Функция для вычисления значения y1
double y1(double x) {
    return -x - 2;
}

// Функция для вычисления значения y2
double y2(double x) {
    return pow(x + 1, 2) + pow(y1(x), 2) - 1;
}

// Функция для вычисления значения y3
double y3(double x) {
    return 1;
}

// Функция для вычисления значения y4
double y4(double x) {
    return -2 * x + 3;
}

// Функция для вычисления значения y5
double y5(double x) {
    return -1;
}

int main() {
    // Заголовок таблицы
    cout      << setw(11) << "Значения X"
              << setw(12) << "y=-x-2 "
              << setw(10) << "y=(x+1)^2+y^2=1"
              << setw(10) << "y=1"
              << setw(10) << "y=-2x+3"
              << setw(10) << "y=-1" << endl;

    // Шапка таблицы
    cout      << setw(11) << "---------"
              << setw(10) << "---------"
              << setw(10) << "---------"
              << setw(10) << "---------"
              << setw(10) << "---------"
              << setw(10) << "---------"
              << setw(10) << "---------" << endl;
    // Начальное и конечное значения x
    double xStart = -3;
    double xEnd = 5;

    // Шаг для изменения x
    double xStep = 0.1;

    // Вычисление и вывод значений функций для каждого x в заданном диапазоне
    for (double x = xStart; x <= xEnd; x += xStep) {
        cout      << fixed << setprecision(2)
                  << setw(10) << x
                  << setw(10) << y1(x)
                  << setw(10) << y2(x)
                  << setw(10) << "    "
                  << setw(10) << y3(x)
                  << setw(10) << y4(x)
                  << setw(10) << y5(x) << endl;
    }

    return 0;
}
