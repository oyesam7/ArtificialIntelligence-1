from math import exp
from random import random, randint

def SA(P, FTarget, Tmax, dT):                                           # 1.    start point P: start random or defined value
        T, Pbest = Tmax, P                                              # 2.    set temperature to Tmax  


        while P.F() < FTarget and T>0:                           
                neighbors = P.Gn()                       
                Pmax = max(neighbors, key=lambda x: x.F())        

                q = (Pmax.F()-P.F())/P.F()
                p = min(1, exp(-q/T))
                        
                if random() > p: P = Pmax
                else: P = neighbors[randint(0, len(neighbors)-1)]

                T -= dT                                                 
        return P  
