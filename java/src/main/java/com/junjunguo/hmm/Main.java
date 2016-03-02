package com.junjunguo.hmm;

/**
 * This file is part of ArtificialIntelligence.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 01/03/16.
 */
public class Main {
    public static void main(String[] args) {
        HMM hmm = new HMM();
        System.out.println("Part B Implement filtering using the Forward operation (Equation 15.5 and Equation 15.12)");
        boolean[] b1 = {true, true};
        System.out.println("       1: Umbrella was used both on day 1 and day 2\n          " +
                           hmm.getString(hmm.filtering(b1)));
        boolean[] b2 = {true, true, false, true, true};
        System.out.println("       2: calculate the probability of rain at day 5" +
                           "\n          " + hmm.getString(hmm.filtering(b2)));

        System.out.println("\n" +
                           "Part C Implement smoothing using the Forward-Backward algorithm");

        System.out.println("       1: umbrella was used the first two days \n" +
                           hmm.getString(hmm.forwardBackward(b1)));
        System.out.println(
                "       2: e1:5 = {Umbrella1 = true, Umbrella2 = true, Umbrella3 = false, Umbrella4 = true, Umbrella5 = true} \n" +
                hmm.getString(hmm.forwardBackward(b2)));
    }
}