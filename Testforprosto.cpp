#include <iostream>
#include <vector>
#include <cmath>
#include <cstdlib>
#include <ctime>

using namespace std;

// Функция для возведения в степень по модулю
long long modPow(long long a, long long x, long long p) {
    long long res = 1;
    a = a % p;
    while (x > 0) {
        if (x & 1) res = (res * a) % p;
        x = x >> 1;
        a = (a * a) % p;
    }
    return res;
}

// Функция для проверки простоты числа тестом Миллера-Рабина
bool Miller(long long n, int k) {
    if (n <= 1 || n == 4) return false;
    if (n <= 3) return true;
    long long d = n - 1;
    while (d % 2 == 0) d /= 2;
    for (int i = 0; i < k; i++) {
        long long a = 2 + rand() % (n - 4);
        long long x = modPow(a, d, n);
        if (x == 1 || x == n - 1) continue;
        while (d != n - 1) {
            x = (x * x) % n;
            d *= 2;
            if (x == 1) return false;
            if (x == n - 1) break;
        }
        if (x != n - 1) return false;
    }
    return true;
}

// Функция для проверки простоты числа тестом Поклингтона
bool Pock(long long n) {
    if (n <= 1) return false;
    if (n <= 3) return true;
    if (n % 2 == 0 || n % 3 == 0) return false;
    long long x = (n - 1) / 2;
    if (modPow(2, x, n) == n - 1) return true;
    return false;
}

// Функция для проверки простоты числа тестом ГОСТ Р 34.10-94
bool Gost(long long n) {
    if (n <= 1) return false;
    if (n <= 3) return true;
    if (n % 2 == 0 || n % 3 == 0) return false;
    long long x = (n - 1) / 2;
    if (modPow(2, x, n) == 1) return true;
    return false;
}

// Алгоритм решета Эратосфена для построения таблицы простых чисел
vector<bool> Eratosthen(int n) {
    vector<bool> prime(n + 1, true);
    prime[0] = prime[1] = false;
    for (int p = 2; p * p <= n; p++) {
        if (prime[p]) {
            for (int i = p * p; i <= n; i += p) {
                prime[i] = false;
            }
        }
    }
    return prime;
}

// Генерация простого числа заданной длины на основе теста Миллера-Рабина
long long generatePrimeMillerRabin(int length, int k) {
    srand(time(0));
    while (true) {
        long long num = rand() % (int)pow(10, length) + (int)pow(10, length - 1);
        if (Miller(num, k)) {
            return num;
        }
    }
}

// Генерация простого числа заданной длины на основе теста Поклингтона
long long generatePrimePocklington(int length) {
    srand(time(0));
    while (true) {
        long long num = rand() % (int)pow(10, length) + (int)pow(10, length - 1);
        if (Pock(num)) {
            return num;
        }
    }
}

// Генерация простого числа заданной длины на основе теста ГОСТ Р 34.10-94
long long generatePrimeGost(int length) {
    srand(time(0));
    while (true) {
        long long num = rand() % (int)pow(10, length) + (int)pow(10, length - 1);
        if (Gost(num)) {
            return num;
        }
    }
}

// Проверка числа на простоту вероятностным тестом
bool isPrimeProbabilisticTest(long long n, int iterations) {
    srand(time(0));
    for (int i = 0; i < iterations; i++) {
        long long a = rand() % (n - 1) + 1;
        if (modPow(a, n - 1, n) != 1) {
            return false;
        }
    }
    return true;
}

int main() {
    int length = 5; // Длина числа в цифрах
    int iterations = 5; // Количество итераций вероятностного теста
    int k = 5; // Количество итераций теста Миллера-Рабина
    int countRejected = 0; // Количество отвергнутых чисел

    // Построение таблицы простых чисел
    vector<bool> primeTable = Eratosthen(500);

    // Генерация и проверка 10 простых чисел
    cout << "№\tP\t\tTest\t\tK" << endl;
    for (int i = 1; i <= 10; i++) {
        // Генерация простого числа заданной длины
        long long primeMillerRabin = generatePrimeMillerRabin(length, k);
        long long primePocklington = generatePrimePocklington(length);
        long long primeGost = generatePrimeGost(length);

        // Проверка на простоту вероятностным тестом
        bool isPrimeMillerRabinTest = isPrimeProbabilisticTest(primeMillerRabin, iterations);
        bool isPrimePocklingtonTest = isPrimeProbabilisticTest(primePocklington, iterations);
        bool isPrimeGostTest = isPrimeProbabilisticTest(primeGost, iterations);

        // Подсчет количества отвергнутых чисел
        if (!isPrimeMillerRabinTest) countRejected++;
        if (!isPrimePocklingtonTest) countRejected++;
        if (!isPrimeGostTest) countRejected++;

        // Вывод результатов с названиями тестов
        cout << i << "\t" << primeMillerRabin << "\tMiller-Rabin\t" << (isPrimeMillerRabinTest ? "+" : "-") << "\t" << countRejected << endl;
        cout << i << "\t" << primePocklington << "\tPocklington\t" << (isPrimePocklingtonTest ? "+" : "-") << "\t" << countRejected << endl;
        cout << i << "\t" << primeGost << "\tGOST\t\t" << (isPrimeGostTest ? "+" : "-") << "\t" << countRejected << endl;
    }

    return 0;
}