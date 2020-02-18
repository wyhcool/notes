import java.util.BitSet;
import java.util.stream.StreamSupport;

/**
 * run the Sieve of Erathostenes benchmark
 *
 * @version 1.0.0 2020-02-19 00:14
 * @author bruce
 */
public class Sieve {

    public static void main(String[] args) {
        int n = 200000;
        long start = System.currentTimeMillis();
        BitSet b = new BitSet(n + 1);
        int count = 0;
        int i;
        for (i = 2; i <= n; i++) {
            b.set(i);
        }
        i = 2;
        while (i * i < n) {
            if (b.get(i)) {
                count++;
                int k = 2 * i;

                while (k <= n) {
                    b.clear(k);
                    k += i;
                }
            }
            i++;
        }

        while (i <= n) {
            if (b.get(i)) count++;
            i++;
        }

        long end = System.currentTimeMillis();
        System.out.println(count + " primes");
        System.out.println((end - start) + " milliseconds");
    }

}
