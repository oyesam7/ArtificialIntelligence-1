package com.junjunguo.hmm;

/**
 * This file is part of ArtificialIntelligence.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 01/03/16.
 *
 * Equation 15.12
 *
 * np.dot product of two arrays.
 *
 * np.transpose() Permute the dimensions of an array.
 *
 * α P(et+1 | Xt+1) sum_xt( P(Xt+1 | xt) P (xt | e1:t) ) (Markov assumption). (15.5)
 *
 * use column vectors to represent the forward and backward messages,
 * all the computations become simple matrix–vector operations:
 *
 * f1:t+1 = α Ot+1 T⊤ f1:t (15.12)
 */
public class Forward {

}
