#include <iostream>
#include <cmath>
#include <vector>
#include <cstdlib>
#include <ctime>

using namespace std;

// Функция для возведения в степень по модулю
long long modPow(long long base, long long exponent, long long modulus) {
    long long result = 1;
    while (exponent > 0) {
        if (exponent % 2 == 1) {
            result = (result * base) % modulus;
        }
        exponent = exponent >> 1;
        base = (base * base) % modulus;
    }
    return result;
}

// Функция для проверки простоты числа Миллера-Рабина
bool millerRabinTest(long long n, int k) {
    if (n < 2) return false;
    if (n != 2 && n % 2 == 0) return false;

    long long d = n - 1;
    while (d % 2 == 0) {
        d /= 2;
    }

    for (int i = 0; i < k; i++) {
        long long a = rand() % (n - 1) + 1;
        long long x = modPow(a, d, n);
        if (x == 1 || x == n - 1) continue;

        bool composite = true;
        for (long long r = 0; r < d - 1; r++) {
            x = modPow(x, 2, n);
            if (x == n - 1) {
                composite = false;
                break;
            }
        }

        if (composite) return false;
    }

    return true;
}


// Функция для проверки простоты числа тестом Поклингтона
bool pocklingtonTest(long long n, const vector<long long>& factors) {
    if (n < 2) return false;
    if (n != 2 && n % 2 == 0) return false;

    long long d = n - 1;
    while (d % 2 == 0) {
        d /= 2;
    }

    for (int i = 0; i < factors.size(); i++) {
        long long a = rand() % (n - 1) + 1;
        long long x = modPow(a, d, n);
        if (x != 1) return false;

        for (int j = 0; j < factors.size(); j++) {
            long long y = modPow(factors[j], d, n);
            if (modPow(a, y, n) == 1) break;
            if (j == factors.size() - 1) return false;
        }
    }

    return true;
}


// Функция для проверки простоты числа по ГОСТ Р 34.10-94
bool gostTest(long long n, int k) {
    if (n <= 1 || n == 4) return false;
    if (n <= 3) return true;

    long long d = n - 1;
    while (d % 2 == 0) {
        d /= 2;
    }

    for (int i = 0; i < k; i++) {
        long long a = 2 + rand() % (n - 4);
        long long x = modPow(a, d, n);
        if (x == 1 || x == n - 1) continue;

        bool composite = true;
        for (long long r = 0; r < d - 1; r++) {
            x = modPow(x, 2, n);
            if (x == 1) return false;
            if (x == n - 1) {
                composite = false;
                break;
            }
        }

        if (composite) return false;
    }

    return true;
}

// Функция для проверки простоты числа
bool isPrime(long long n, int testType) {
    switch (testType) {
        case 1:
            return millerRabinTest(n, 5);
        case 2:
            return pocklingtonTest(n, {2, 3, 5}); // Пример: разложение n - 1 = 2 * 3 * 5
        case 3:
            return gostTest(n, 5);
        default:
            return false;
    }
}

int main() {
    srand(static_cast<unsigned int>(time(0))); // Инициализация генератора случайных чисел

    long long number;
    int testType;

    cout << "Введите число на проверку: ";
    cin >> number;

    cout << "Тесты на простоту:\n";
    cout << "1. Миллер-Рабин\n";
    cout << "2. Поклингтон\n";
    cout << "3. ГОСТ Р 34.10-94\n";
    cout << "Выберите номер теста: ";
    cin >> testType;

    if (isPrime(number, testType)) {
        cout << number << " это число простое." << endl;
    } else {
        cout << number << " это число составное." << endl;
    }

    return 0;
}