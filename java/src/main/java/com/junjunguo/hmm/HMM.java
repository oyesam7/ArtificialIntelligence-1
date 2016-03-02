package com.junjunguo.hmm;

import Jama.Matrix;

/**
 * This file is part of ArtificialIntelligence.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 01/03/16.
 * <p/>
 * Hidden Markov Model
 * <p/>
 * umbrella: 	p = 0.9		raining
 * <p/>
 * umbrella:    p = 0.2 	sunshine
 * <p/>
 * weather is same as yesterday:
 * <p/>
 * p = 0.7
 * <p/>
 * reference: Artificial Intelligence A Modern Approach, 3rd
 */
public class HMM {

    /** initial array value when there is no observation: probability of rain [0.5, 0.5] */
    private double[][] initArray = {{0.5}, {0.5}};
    /** the transition matrix for the umbrella world is */
    private double[][] tArray    = {{0.7, 0.3}, {0.3, 0.7}};
    /**
     * T 15.3 .1 Simplified matrix algorithms P579
     * <p/>
     * Transition model P(Xt | Xt - 1) becomes an S * S matrix T
     * <p/>
     * Tij = P(Xt = j | Xt - 1 = i)
     * <p/>
     * <p/>
     * Tij is the probability of a transition from state i to state j
     */
    private Matrix     T         = new Matrix(tArray);

    /** no umbrella */
    private double[][] umbrellaF = {{0.1, 0}, {0, 0.8}};
    /** bring umbrella */
    private double[][] umbrellaT = {{0.9, 0}, {0, 0.2}};
    /**
     * P579
     * <p/>
     * Ot: ith diagonal entry is P(et | Xt = i) and other entries are 0
     */
    private Matrix[]   O         = {new Matrix(umbrellaF), new Matrix(umbrellaT)};

    /**
     * Get O: probability distribution of umbrella (true or false).
     *
     * @param ev evidence: umbrella (true or false)
     * @return the double [ ] [ ] represent probability distribution of umbrella true or false
     */
    public double[][] getO(boolean ev) {
        return ev ? umbrellaT : umbrellaF;
    }

    /**
     * α P(et+1 | Xt+1) sum_xt( P(Xt+1 | xt) P (xt | e1:t) ) (Markov assumption). (15.5)
     * <p/>
     * use column vectors to represent the forward and backward messages, all the computations become simple
     * matrix–vector operations:
     * <p/>
     * f1:t+1 = α Ot+1 T⊤ f1:t (15.12)
     *
     * @param fv the fv: a vector of forward messages for steps 0, . . . , t
     * @param ev the evidence boolean
     * @return the double [ ] [ ]
     */
    public double[][] forward(double[][] fv, boolean ev) {
        return normalize(new Matrix(getO(ev)).times(T.transpose()).times(new Matrix(fv)).getArray());
    }

    /**
     * bk+1:t = T Ok+1 bk+2:t . (15.13)
     * <p/>
     * Backward double [ ] [ ].
     *
     * @param b  the b: a representation of the backward message
     * @param ev the ev: the observation of evidence
     * @return the double [ ] [ ]
     */
    public double[][] backward(double[][] b, boolean ev) {
        return T.times(new Matrix(getO(ev))).times(new Matrix(b)).getArray();
    }

    /**
     * Filtering double [ ] [ ].
     *
     * @param evidence a vector of evidence values for steps 1 to t
     * @return the double [ ] [ ] a vector of probability distributions <p/> <p/> Filtering: This is the task of
     * computing the belief state—the posterior distribution over the most recent state—given all evidence to date. <p/>
     * P(Xt | e1:t) <p/> computing the probability of rain today, given all the observations of the umbrella carrier
     * made so far.
     */
    public double[][] filtering(boolean[] evidence) {
        double[][] p = initArray;
        for (int i = 0; i < evidence.length; i++) {
            p = forward(p, evidence[i]);
        }
        return new Matrix(p).transpose().getArray();
    }

    /**
     * Prediction double [ ] [ ].
     *
     * @param evidence a vector of evidence values for steps 1 to t
     * @return the double [ ] [ ] a vector of probability distributions <p/> <p/> Prediction: This is the task of
     * computing the posterior distribution over the future state, given all evidence to date. That is, we wish to
     * compute P(Xt+k | e1:t) for some k > 0. <p/> In the umbrella example, this might mean computing the probability of
     * rain three days from now, given all the observations to date. <p/> Prediction is useful for evaluating possible
     * courses of action based on their expected outcomes.
     */
    public double[][] prediction(boolean[] evidence) {
        return filtering(evidence);
    }

    /**
     * Forward backward double [ ] [ ].
     *
     * @param evidence a vector of evidence values for steps 1 to t
     * @return the double [ ] [ ] a vector of probability distributions <p/> <p/> <p/> Figure 15.4  page 576 <p/> The
     * forward–backward algorithm for smoothing: computing posterior probabilities of a sequence of states given a
     * sequence of observations.
     */
    public double[][][] forwardBackward(boolean[] evidence) {
        //        fv, a vector of forward messages for steps 0, . . . , t
        double[][][] fv = new double[evidence.length + 1][evidence.length][evidence.length];
        fv[0] = initArray; //distribution on the initial state
        for (int i = 0; i < evidence.length; i++) {
            fv[i + 1] = forward(fv[i], evidence[i]);
        }
        //        b, a representation of the backward message, initially all 1s
        double[][] b = {{1}, {1}};
        //        sv, a vector of smoothed estimates for steps 1, . . . , t
        double[][][] sv = new double[evidence.length + 1][evidence.length + 1][evidence.length];

        for (int i = evidence.length; i > 0; i--) {
            sv[i] = normalize(new Matrix(fv[i]).times(new Matrix(b).transpose()).getArray());
            b = backward(b, evidence[i - 1]);
        }
        return sv;
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

    /**
     * Gets stringre presentation of matrix.
     *
     * @param a the array of double [][]
     * @return the string value of the matrix
     */
    public String getString(double[][] a) {
        String s = "[";
        for (int i = 0; i < a.length; i++) {
            s += "[";
            for (int j = 0; j < a[i].length; j++) {
                s += a[i][j];
                if (j != a[i].length - 1) s += ", ";
            }
            s += "]";
            if (i != a.length - 1) s += ",\n";
        }
        return s + "]";
    }

    /**
     * Gets string representation of triple array.
     *
     * @param a the a double [][][]
     * @return the string
     */
    public String getString(double[][][] a) {
        String s = "[";
        for (int i = 0; i < a.length; i++) {
            s += getString(a[i]);
        }
        return s + "]";
    }

    /**
     * log for debug
     */
    private void log(String s) {
        System.out.println(s);
    }
}
