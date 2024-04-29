import java.util.Scanner;
import java.util.Random;

public class ProstoTest2 {
    // Function to calculate power modulo
    public static long modPow(long base, long exponent, long modulus) {
        long result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }
            exponent = exponent >> 1;
            base = (base * base) % modulus;
        }
        return result;
    }

    // Function to check if a number is prime using the Miller-Rabin test
    public static boolean millerRabinTest(long n, int k) {
        if (n < 2) return false;
        if (n != 2 && n % 2 == 0) return false;

        long d = n - 1;
        while (d % 2 == 0) {
            d /= 2;
        }

        Random rand = new Random();
        for (int i = 0; i < k; i++) {
            long a = rand.nextInt((int) (n - 1)) + 1;
            long x = modPow(a, d, n);
            if (x == 1 || x == n - 1) continue;

            boolean composite = true;
            for (long r = 0; r < d - 1; r++) {
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

    // Function to check if a number is prime using the Pocklington's test
    public static boolean pocklingtonTest(long n, long[] factors) {
        if (n < 2) return false;
        if (n != 2 && n % 2 == 0) return false;

        long d = n - 1;
        while (d % 2 == 0) {
            d /= 2;
        }

        Random rand = new Random();
        for (int i = 0; i < factors.length; i++) {
            long a = rand.nextInt((int) (n - 1)) + 1;
            long x = modPow(a, d, n);
            if (x != 1) return false;

            for (int j = 0; j < factors.length; j++) {
                long y = modPow(factors[j], d, n);
                if (modPow(a, y, n) == 1) break;
                if (j == factors.length - 1) return false;
            }
        }

        return true;
    }

    // Function to check if a number is prime according to the GOST test
    public static boolean gostTest(long n, int k) {
        if (n <= 1 || n == 4) return false;
        if (n <= 3) return true;

        long d = n - 1;
        while (d % 2 == 0) {
            d /= 2;
        }

        Random rand = new Random();
        for (int i = 0; i < k; i++) {
            long a = 2 + rand.nextInt((int) (n - 4));
            long x = modPow(a, d, n);
            if (x == 1 || x == n - 1) continue;

            boolean composite = true;
            for (long r = 0; r < d - 1; r++) {
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

    // Function to check if a number is prime
    public static boolean isPrime(long n, int testType) {
        switch (testType) {
            case 1:
                return millerRabinTest(n, 5);
            case 2:
                return pocklingtonTest(n, new long[]{2, 3, 5}); // Example: factorization of n - 1 = 2 * 3 * 5
            case 3:
                return gostTest(n, 5);
            default:
                return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number to check: ");
        long number = scanner.nextLong();

        System.out.println("Primality tests:\n");
        System.out.println("1. Miller-Rabin\n");
        System.out.println("2. Pocklington\n");
        System.out.println("3. GOST R 34.10-94\n");
        System.out.println("Enter the test number: ");
        int testType = scanner.nextInt();

        if (isPrime(number, testType)) {
            System.out.println(number + " is a prime number.");
        } else {
            System.out.println(number + " is a composite number.");
        }

        scanner.close();
    }
}