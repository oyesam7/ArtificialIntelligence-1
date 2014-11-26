import math

def SA(P, Tmax, Ftarget, dT):                   # 1.    start point P: start random or defined value
    T = Tmax                                    # 2.    set temperature to Tmax
    while (true):                               # 3.    objective function F evaluate samples
        if P.F(P) >= Ftarget or T<=0: return P  # 4.    return P as solution
        neighbors = P.Gn(P)                     # 5.    generate n neighbors of P
        for i in range(len(neighbors)):
            eneighbors[i] = P.F(neighbors[i])   # 6.    Evaluate each neighbor
        Pmax = math.max(eneighbors)             # 7.    Let Pmax be the neighbor with the highest evaluation
        q = (P.F(Pmax) - P.F(P)) / P.F(p)       # 8.    q = (F(Pmax) - F(P)) / F(p)
        p = math.min[1, math.e ** (-q / T)]     # 9.
        x = random.uniform(0.0, 1.0)            # 10.   generate x = random [0,1]
        if x > p:   P = Pmax                    # 11.
        else: P = random.choice(eneighbors)     # 12.   a random choice among n neighbors
        T = T - dT                              # 13.
