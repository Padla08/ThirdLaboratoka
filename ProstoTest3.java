import java.util.*;

public class ProstoTest3 {
    // Function to calculate power modulo
    public static long power(long a, long b, long n) {
        long x = 1, y = a;
        while (b > 0) {
            if (b % 2 == 1) {
                x = (x * y) % n;
            }
            y = (y * y) % n;
            b /= 2;
        }
        return x % n;
    }

    // Miller-Rabin test
    public static boolean millerRabinTest(long d, long n) {
        long a = 2 + (long)(Math.random() * (n - 4));
        long x = power(a, d, n);

        if (x == 1 || x == n - 1)
            return true;

        while (d != n - 1) {
            x = (x * x) % n;
            d *= 2;

            if (x == 1)      return false;
            if (x == n - 1)  return true;
        }

        return false;
    }

    // Pocklington's test
    public static boolean pocklingtonTest(long n) {
        long a = 2 + (long)(Math.random() * (n - 3));
        long x = power(a, (n - 1) / 2, n);

        if (x != 1 && x != n - 1)
            return false;

        long jacobi = (x * x - a) % n;
        if (jacobi < 0) jacobi += n;

        return jacobi == n - 1;
    }

    // GOST test
    public static boolean gostTest(long n) {
        long q = n - 1;
        long k = 0;

        while (q % 2 == 0) {
            q /= 2;
            k++;
        }

        long a = 2 + (long)(Math.random() * (n - 4));
        long x = power(a, q, n);

        if (x == 1 || x == n - 1)
            return true;

        for (long i = 0; i < k - 1; i++) {
            x = (x * x) % n;
            if (x == 1)
                return false;
            if (x == n - 1)
                return true;
        }

        return false;
    }

    // Prime checking
    public static boolean[] isPrime(long n, int k) {
        boolean[] results = {true, true, true};
        if (n <= 1 || n == 4) {
            return new boolean[]{false, false, false};
        }
        if (n <= 3) {
            return new boolean[]{true, true, true};
        }
        for (int i = 0; i < k; i++) {
            results[0] = results[0] && millerRabinTest(n - 1, n);
            results[1] = results[1] && pocklingtonTest(n);
            results[2] = results[2] && gostTest(n);
        }
        return results;
    }

    // Sieve of Eratosthenes algorithm to build a prime table
    public static boolean[] sieveOfEratosthenes(int n) {
        boolean[] prime = new boolean[n+1];
        Arrays.fill(prime, true);
        prime[0] = prime[1] = false;
        for (int p = 2; p*p <= n; p++) {
            if (prime[p]) {
                for (int i = p*p; i <= n; i += p)
                    prime[i] = false;
            }
        }
        return prime;
    }

    // Generate prime numbers of a given length
    public static List<Integer> generatePrimes(int length) {
        List<Integer> primes = new ArrayList<>();
        boolean[] prime = sieveOfEratosthenes(1000); // Increase the upper limit
        for (int i = 2; i < 1000 && primes.size() < length; i++) {
            if (prime[i]) {
                primes.add(i);
            }
        }
        return primes;
    }

    public static void main(String[] args) {
        int k = 10; // Number of iterations for the tests
        int length = 10; // Length of the generated prime numbers

        // Generate prime numbers
        List<Integer> primes = generatePrimes(length);

        // Output the table
        System.out.println("№\tP\tМиллер\tПонклинтон\tГост");
        for (int i = 0; i < primes.size(); i++) {
            boolean[] results = isPrime(primes.get(i), k);
            System.out.println((i + 1) + "\t" + primes.get(i) + "\t"
                    + (results[0] ? "+" : "-") + "\t"
                    + (results[1] ? "+" : "-") + "\t"
                    + (results[2] ? "+" : "-"));
        }
    }
}