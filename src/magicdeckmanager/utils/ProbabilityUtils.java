/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.utils;

/**
 *
 * @author drbra_000
 */
public class ProbabilityUtils {

    private static long binomial(int n, int k) {
        if (k > n - k) {
            k = n - k;
        }

        long b = 1;
        for (int i = 1, m = n; i <= k; i++, m--) {
            b = b * m / i;
        }
        return b;
    }

    /**
     * @param k Amount of successfully drawn cards with the same type.
     * @param n Amount of draws.
     * @param N Deck size.
     * @param K Total amount of specific card in the deck.
     * @return probability
     */
    public static double hypeGeomDist(int k, int n, int N, int K) {
        int a = N - K;
        int b = n - k;
        final long x = binomial(K, k);
        final long y = binomial(a, b);
        final long z = binomial(N, n);
        final float result = (float)(x * y) / z;
        return result;
    }
    
    /**
     * @param n Amount of draws.
     * @param N Deck size.
     * @param K Total amount of specific card in the deck.
     * @return probability
     */
    public static double probabilityDrawingAtLeastOneCard(int n, int N, int K) {
        return 1 - hypeGeomDist(0, n, N, K);
    }

}
