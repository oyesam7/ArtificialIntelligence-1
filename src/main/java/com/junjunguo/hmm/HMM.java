package com.junjunguo.hmm;

import Jama.Matrix;

/**
 * This file is part of ArtificialIntelligence.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 01/03/16.
 * <p/>
 * Hidden Markov Model
 */
public class HMM {

    /** initial array value when there is no observation */
    private double[][] initArray = {{0.5}, {0.5}};
    /** initial matrix when there is no observation */
    private Matrix     initValue = new Matrix(initArray);

    private double[][] tArray = {{0.7, 0.3}, {0.3, 0.7}};
    /**
     * T 15.3 .1 Simplified matrix algorithms P579
     * <p/>
     * Transition model P(Xt | Xt - 1) becomes an S * S matrix T
     * <p/>
     * Tij = P(Xt = j | Xt - 1 = i)
     * <p/>
     * Tij is the probability of a transition from state i to state j
     */
    private Matrix     T      = new Matrix(tArray);

    private double[][] o1Array = {{0.1, 0}, {0, 0.8}};
    private double[][] o3Array = {{0.9, 0}, {0, 0.2}};
    /**
     * P579
     * <p/>
     * Ot: ith diagonal entry is P(et | Xt = i) and other entries are 0
     */
    private Matrix[]   O       = {new Matrix(o1Array), new Matrix(o3Array)};


    /**
     * α P(et+1 | Xt+1) sum_xt( P(Xt+1 | xt) P (xt | e1:t) ) (Markov assumption). (15.5)
     * <p/>
     * use column vectors to represent the forward and backward messages, all the computations become simple
     * matrix–vector operations:
     * <p/>
     * f1:t+1 = α Ot+1 T⊤ f1:t (15.12)
     */
    public void forward(double[][] fv, boolean ev) {
        O[ev] =
    }

    /**
     * normalizing constant used to make probabilities sum up to 1.
     *
     * @param n the n [][] array
     * @return the double [] []
     */
    public double[][] normalize(double[][] n) {
        double[][] array = new double[n.length][1];
        double     sum   = sumArray(n);
        for (int i = 0; i < n.length; i++) {
            double s = 0;
            for (int j = 0; j < n[i].length; j++) {
                s += n[i][j];
            }
            array[i][0] = s / sum;
        }
        return array;
    }

    /**
     * @param n the [][] matrix
     * @return sum of the arrays
     */
    private double sumArray(double[][] n) {
        double s = 0;
        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n[i].length; j++) {
                s += n[i][j];
            }
        }
        return s;
    }
}
