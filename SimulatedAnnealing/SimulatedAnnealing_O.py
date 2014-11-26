from math import exp
from random import random, randint

def SA(P, FTarget, Tmax, dT):                                           #Straight forward implementation of the algorithm shown in assignment text
        T, Pbest = Tmax, P

        while P.F() < FTarget and T>0:                            #Loop as long as we haven't reached our target or until 'temperature' drops to 0
                neighbors = P.Gn()                       #Generate some neighbors of the current state
                Pmax = max(neighbors, key=lambda x: x.F())        #Pick out the neighbor with the largest objective function value

                q = (Pmax.F()-P.F())/P.F()
                p = min(1, exp(-q/T))
                        
                if random() > p: P = Pmax
                else: P = neighbors[randint(0, len(neighbors)-1)]

                T -= dT                                                 #Decrease the temperature
        return P  
