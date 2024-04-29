import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Coffee {

    static double Temp;
    static double EnvT;
    static double CoolC;
    static int timeLimit;
    static List<Double> apoxVector = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter start coffee temperature(Celsius): ");
        Temp = scanner.nextDouble();
        System.out.println("Enter temperature of environment(Celsius): ");
        EnvT = scanner.nextDouble();
        System.out.println("Enter cooling coefficient(dC): ");
        CoolC = scanner.nextDouble();
        System.out.println("Enter time of full cool(Seconds): ");
        timeLimit = scanner.nextInt();

        List<Pair<Double, Double>> results = coffee(Temp, EnvT, CoolC, timeLimit);

        System.out.println("Coefficients of the approximating line a - " + apoxVector.get(0) + ", b - " + apoxVector.get(1));
        System.out.println("+------+" + "------------+" + "---------------+");
        System.out.println("| Time |" + " Temperature|" + "CorrelationCoef|");
        System.out.println("+------+" + "------------+" + "---------------+");

        int time = 0;

        for (Pair<Double, Double> result : results) {
            System.out.printf("|%6d|%12.2f|%15.2f|%n", time, result.getFirst(), result.getSecond());
            time++;
            System.out.println("+------+" + "------------+" + "---------------+");
        }
    }

    static double korrel(List<Double> temperatures, double meanY, int time) {
        double sumXY = 0, sumX2 = 0, sumY2 = 0;
        double meanX = (timeLimit - 1) / 2.0;
        for (int i = 0; i <= time; i++) {
            sumXY += (i - meanX) * (temperatures.get(i) - meanY);
            sumX2 += (i - meanX) * (i - meanX);
            sumY2 += (temperatures.get(i) - meanY) * (temperatures.get(i) - meanY);
        }
        return sumXY / Math.sqrt(sumX2 * sumY2);
    }

    static Pair<Double, Double> aprox(List<Double> xVector, List<Double> yVector) {
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        int n = xVector.size();

        for (int i = 0; i < n; i++) {
            sumX += xVector.get(i);
            sumY += yVector.get(i);
            sumXY += xVector.get(i) * yVector.get(i);
            sumX2 += xVector.get(i) * xVector.get(i);
        }

        double a = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double b = (sumY - a * sumX) / n;

        return new Pair<>(a, b);
    }

    static List<Pair<Double, Double>> coffee(double Temp, double EnvT, double CoolC, int timeLimit) {
        List<Double> temperatures = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Pair<Double, Double>> tempCorr = new ArrayList<>();

        for (int time = 0; time <= timeLimit; time++) {
            double temperature = EnvT + (time - EnvT) * Math.exp(-CoolC * time);
            temperatures.add(temperature);
            times.add((double) time);
        }

        Pair<Double, Double> aproxCoeffs = aprox(times, temperatures);
        double a = aproxCoeffs.getFirst();
        double b = aproxCoeffs.getSecond();

        apoxVector.add(a);
        apoxVector.add(b);

        double meanY = temperatures.stream().mapToDouble(Double::doubleValue).sum() / temperatures.size();
        for (int time = 0; time <= timeLimit; time++) {
            double corr = korrel(temperatures, meanY, time);
            tempCorr.add(new Pair<>(temperatures.get(time), corr));
        }

        return tempCorr;
    }

    static class Pair<T, U> {
        private final T first;
        private final U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public U getSecond() {
            return second;
        }
    }
}