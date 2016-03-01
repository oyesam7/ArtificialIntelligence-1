package com.junjunguo.hmm;

import Jama.Matrix;

/**
 * This file is part of ArtificialIntelligence.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 01/03/16.
 */
public class Main {
    public static void main(String[] args) {


        HMM hmm = new HMM();

        System.out.println("Part B 1: Umbrella was used both on day 1 and day 2\n" + str(Prediction([True, True])));
        System.out.println(
                "Part B 2: \n e1:5 = {Umbrella1 = true, Umbrella2 = true, Umbrella3 = false, Umbrella4 = true, Umbrella5 = true} "
                "\n" + str(Prediction([True, True, False, True, True])));

        System.out.println("Part C 1 umbrella was used the first two days \n" + str(ForwardBackward([True, True])));
        System.out.println(
                "Part C.2: \n e1:5 = {Umbrella1 = true, Umbrella2 = true, Umbrella3 = false, Umbrella4 = true, Umbrella5 = true} "
                "\n" + str(ForwardBackward([True, True, False, True, True])));
    }
}
