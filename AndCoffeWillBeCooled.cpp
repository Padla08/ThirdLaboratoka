#include <iostream>
#include <vector>
#include <numeric>
#include <cmath>
#include <stdexcept>
#include <iomanip>

using namespace std;

double Temp;
double EnvT;
double CoolC;
int time_limit;
vector<double> apox_vector;

// Функция которая вычисляет коэффициент корреляции.

double korrel(const vector<double>& temperatures, double mean_y, int Temp) {
    double sum_xy = 0, sum_x2 = 0, sum_y2 = 0;
    double mean_x = (time_limit - 1) / 2.0;
    for (int i = 0; i <= Temp; i++) {
        sum_xy += (i - mean_x) * (temperatures[i] - mean_y);
        sum_x2 += (i - mean_x) * (i - mean_x);
        sum_y2 += (temperatures[i] - mean_y) * (temperatures[i] - mean_y);
    }
    return sum_xy / sqrt(sum_x2 * sum_y2);
}

// Функция, которая вычисляет коэффициенты линейной аппроксимации для векторов температур и времени.

pair<double, double> aprox(const vector<double>& x_vector, const vector<double>& y_vector) {
    double sum_x = 0, sum_y = 0, sum_xy = 0, sum_x2 = 0;
    int n = x_vector.size();

    for (int i = 0; i < n; i++) {
        sum_x += x_vector[i];
        sum_y += y_vector[i];
        sum_xy += x_vector[i] * y_vector[i];
        sum_x2 += x_vector[i] * x_vector[i];
    }

    double a = (n * sum_xy - sum_x * sum_y) / (n * sum_x2 - sum_x * sum_x);
    double b = (sum_y - a * sum_x) / n;

    return make_pair(a, b);
}

// Функция кофе, которая вычисляет температуру кофе в зависимости от времени, используя заданные коэффициенты.

vector<pair<double, double>> coffee(double Temp, double EnvT, double CoolC, int time_limit) {
    vector<double> temperatures;
    vector<double> times;
    vector<pair<double, double>> temp_corr;

    for (int Temp = 0; Temp <= time_limit; Temp++) {
        double temperature = EnvT + (Temp - EnvT) * exp(-CoolC * Temp);
        temperatures.push_back(temperature);
        times.push_back(Temp);
    }

    pair<double, double> aprox_coeffs = aprox(times, temperatures);
    double a = aprox_coeffs.first;
    double b = aprox_coeffs.second;

    apox_vector = { a, b };


    double mean_y = accumulate(temperatures.begin(), temperatures.end(), 0.0) / temperatures.size();
    for (int Temp = 0; Temp <= time_limit; Temp++) {
        double corr = korrel(temperatures, mean_y, Temp);
        temp_corr.push_back(make_pair(temperatures[Temp], corr));
    }

    return temp_corr;
}

// Ввод и вывод параметров.

int main() {

    cout << "Enter start coffee temperature(Celsius): ";
    cin >> Temp;
    cout << "Enter temperature of environment(Celsius): ";
    cin >> EnvT;
    cout << "Enter cooling coefficient(dC): ";
    cin >> CoolC;
    cout << "Enter time of full cool(Seconds): ";
    cin >> time_limit;

    vector<pair<double, double>> results = coffee(Temp, EnvT, CoolC, time_limit);

    cout << "Coefficients of the approximating line a - " << apox_vector[0] << ", b - " << apox_vector[1] << endl;
    cout << "+------+" << "------------+" << "---------------+" << endl;
    cout << "| Time |" << " Temperature|" << "CorrelationCoef|" << endl;
    cout << "+------+" << "------------+" << "---------------+" << endl;

    int Temp = 0;

    for (const auto& result : results) {
        cout << "|" << setw(6) << Temp  << "|" << setw(12) << result.first << "|" << setw(15) << result.second << "|" << endl;
        Temp++;
        cout << "+------+" << "------------+" << "---------------+" << endl;
    }

    return 0;
}
