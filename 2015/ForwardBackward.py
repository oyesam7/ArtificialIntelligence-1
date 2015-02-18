import numpy as np

# initial value when there is no observations
initValue = np.matrix("0.5;0.5")

# T 15.3.1 Simplified matrix algorithms  P579
# Transition model P(Xt | Xt-1) becomes an S*S matrix T
# Tij = P(Xt=j | Xt-1=i)
# Tij is the probability of a transition from state i to state j
T = np.matrix("0.7 0.3;0.3 0.7")

# P579
# Ot: ith diagonal entry is P (et | Xt = i) and other entries are 0
O = [np.matrix("0.1 0;0 0.8"), np.matrix("0.9 0;0 0.2")]

def Normalize(q):
    '''normalizing: to make probabilities sum up to 1 \n devide every value by there sum and return the result as
    matrix'''
    return q / sum(q)

def Forward(fv, ev):
    '''Equation 15.12 \n np.dot product of two arrays. \n  np.transpose() Permute the dimensions of an array.'''
    return Normalize(np.dot(np.dot(O[ev], T.transpose()), fv))

def Backward(bv, ev):
    '''Equation 15.13'''
    return np.dot(np.dot(T, O[ev]), bv)

def Prediction(E):
    q = initValue
    for i in E:
        q = Forward(q, i)
    return q.transpose()

def ForwardBackward(E):
    '''Figure 15.4 P576 \n fv, a vector of forward messages for steps 0 ... t
    b, a representation of the backward message, initially all 1s
    sv, a vector of smoothed estimates for steps 1'''
    fv, sv = range(len(E) + 1), range(len(E))
    fv[0], b = initValue, np.ones((len(initValue), 1))

    for i in xrange(len(E)):
        fv[i + 1] = Forward(fv[i], E[i])

    for i in xrange(len(E) - 1, -1, -1):
        sv[i] = Normalize(np.multiply(fv[i + 1], b))
        b = Backward(b, E[i])
    return sv

print("Part B 1: Umbrella was used both on day 1 and day 2\n" + str(Prediction([True, True])))
print(
    "Part B 2: \n e1:5 = {Umbrella1 = true, Umbrella2 = true, Umbrella3 = false, Umbrella4 = true, Umbrella5 = true} "
    "\n" + str(Prediction([True, True, False, True, True])))

print("Part C 1 umbrella was used the first two days \n" + str(ForwardBackward([True, True])))
print(
    "Part C.2: \n e1:5 = {Umbrella1 = true, Umbrella2 = true, Umbrella3 = false, Umbrella4 = true, Umbrella5 = true} "
    "\n" + str(ForwardBackward([True, True, False, True, True])))