import java.util.*;
import java.lang.Math;

public class ProstoTest {
    // Function to calculate power modulo
    public static long modPow(long a, long x, long p) {
        long res = 1;
        a = a % p;
        while (x > 0) {
            if ((x & 1) == 1) res = (res * a) % p;
            x = x >> 1;
            a = (a * a) % p;
        }
        return res;
    }

    // Function to check if a number is prime using Miller-Rabin test
    public static boolean isPrimeMillerRabin(long n, int k) {
        if (n <= 1 || n == 4) return false;
        if (n <= 3) return true;
        long d = n - 1;
        while (d % 2 == 0) d /= 2;
        for (int i = 0; i < k; i++) {
            long a = 2 + (long)(Math.random() % (n - 4));
            long x = modPow(a, d, n);
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

    // Function to check if a number is prime using Pocklington's test
    public static boolean isPrimePocklington(long n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        long x = (n - 1) / 2;
        if (modPow(2, x, n) == n - 1) return true;
        return false;
    }

    // Function to check if a number is prime using GOST test
    public static boolean isPrimeGost(long n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        long x = (n - 1) / 2;
        if (modPow(2, x, n) == 1) return true;
        return false;
    }

    // Sieve of Eratosthenes algorithm to build a prime table
    public static boolean[] sieveOfEratosthenes(int n) {
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);
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

    // Generate a prime number of a given length using Miller-Rabin test
    public static long generatePrimeMillerRabin(int length, int k) {
        while (true) {
            long num = (long)(Math.random() * Math.pow(10, length - 1)) + (long)Math.pow(10, length - 1);
            if (isPrimeMillerRabin(num, k)) {
                return num;
            }
        }
    }

    // Generate a prime number of a given length using Pocklington's test
    public static long generatePrimePocklington(int length) {
        while (true) {
            long num = (long)(Math.random() * Math.pow(10, length - 1)) + (long)Math.pow(10, length - 1);
            if (isPrimePocklington(num)) {
                return num;
            }
        }
    }

    // Generate a prime number of a given length using GOST test
    public static long generatePrimeGost(int length) {
        while (true) {
            long num = (long)(Math.random() * Math.pow(10, length - 1)) + (long)Math.pow(10, length - 1);
            if (isPrimeGost(num)) {
                return num;
            }
        }
    }

    // Check a number for primality using a probabilistic test
    public static boolean isPrimeProbabilisticTest(long n, int iterations) {
        for (int i = 0; i < iterations; i++) {
            long a = (long)(Math.random() * (n - 1)) + 1;
            if (modPow(a, n - 1, n) != 1) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int length = 5; // Length of the number in digits
        int iterations = 5; // Number of iterations for the probabilistic test
        int k = 5; // Number of iterations for the Miller-Rabin test
        int countRejected = 0; // Count of rejected numbers

        // Build a prime table
        boolean[] primeTable = sieveOfEratosthenes(500);

        // Generate and check 10 prime numbers
        System.out.println("№\tP\t\tTest\t\tK");
        for (int i = 1; i <= 10; i++) {
            // Generate a prime number of the given length
            long primeMillerRabin = generatePrimeMillerRabin(length, k);
            long primePocklington = generatePrimePocklington(length);
            long primeGost = generatePrimeGost(length);

            // Check for primality using a probabilistic test
            boolean isPrimeMillerRabinTest = isPrimeProbabilisticTest(primeMillerRabin, iterations);
            boolean isPrimePocklingtonTest = isPrimeProbabilisticTest(primePocklington, iterations);
            boolean isPrimeGostTest = isPrimeProbabilisticTest(primeGost, iterations);

            // Count the number of rejected numbers
            if (!isPrimeMillerRabinTest) countRejected++;
            if (!isPrimePocklingtonTest) countRejected++;
            if (!isPrimeGostTest) countRejected++;

            // Output the results with test names
            System.out.println(i + "\t" + primeMillerRabin + "\tMiller-Rabin\t" + (isPrimeMillerRabinTest ? "+" : "-") + "\t" + countRejected);
            System.out.println(i + "\t" + primePocklington + "\tPocklington\t" + (isPrimePocklingtonTest ? "+" : "-") + "\t" + countRejected);
            System.out.println(i + "\t" + primeGost + "\tGOST\t\t" + (isPrimeGostTest ? "+" : "-") + "\t" + countRejected);
        }
    }
}