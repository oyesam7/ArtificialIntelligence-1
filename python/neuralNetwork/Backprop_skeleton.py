import math
import random
import copy

# The transfer function of neurons, g(x)
def logFunc(x):
    return (1.0 / (1.0 + math.exp(-x)))

# The derivative of the transfer function, g'(x)
def logFuncDerivative(x):
    return math.exp(-x) / (pow(math.exp(-x) + 1, 2))

def randomFloat(low, high):
    return random.random() * (high - low) + low

# Initializes a matrix of all zeros
def makeMatrix(I, J):
    m = []
    for i in range(I):
        m.append([0] * J)
    return m

class NN: # Neural Network
    def __init__(self, numInputs, numHidden, learningRate = 0.001):
        # Inputs: number of input and hidden nodes. Assuming a single output node.
        # +1 for bias node: A node with a constant input of 1. Used to shift the transfer function.
        self.numInputs = numInputs + 1
        self.numHidden = numHidden

        # Current activation levels for nodes (in other words, the nodes' output value)
        self.inputActivation = [1.0] * self.numInputs
        self.hiddenActivations = [1.0] * self.numHidden
        self.outputActivation = 1.0 # Assuming a single output.
        self.learningRate = learningRate

        # create weights
        # A matrix with all weights from input layer to hidden layer
        self.weightsInput = makeMatrix(self.numInputs, self.numHidden)
        # A list with all weights from hidden layer to the single output neuron.
        self.weightsOutput = [0 for i in range(self.numHidden)]# Assuming single output
        # set them to random vaules
        for i in range(self.numInputs):
            for j in range(self.numHidden):
                self.weightsInput[i][j] = randomFloat(-0.5, 0.5)
        for j in range(self.numHidden):
            self.weightsOutput[j] = randomFloat(-0.5, 0.5)

        # Data for the backpropagation step in RankNets.
        # For storing the previous activation levels (output levels) of all neurons
        self.prevInputActivations = []
        self.prevHiddenActivations = []
        self.prevOutputActivation = 0
        # For storing the previous delta in the output and hidden layer
        self.prevDeltaOutput = 0
        self.prevDeltaHidden = [0 for i in range(self.numHidden)]
        # For storing the current delta in the same layers
        self.deltaOutput = 0
        self.deltaHidden = [0 for i in range(self.numHidden)]

    def propagate(self, inputs):
        if len(inputs) != self.numInputs - 1:
            raise ValueError('wrong number of inputs')

        # input activations
        self.prevInputActivations = copy.deepcopy(self.inputActivation)
        for i in range(self.numInputs - 1):
            self.inputActivation[i] = inputs[i]
        self.inputActivation[-1] = 1 # Set bias node to -1.

        # hidden activations
        self.prevHiddenActivations = copy.deepcopy(self.hiddenActivations)
        for j in range(self.numHidden):
            sum = 0.0
            for i in range(self.numInputs):
                # print self.ai[i] ," * " , self.wi[i][j]
                sum = sum + self.inputActivation[i] * self.weightsInput[i][j]
            self.hiddenActivations[j] = logFunc(sum)

        # output activations
        self.prevOutputActivation = self.outputActivation
        sum = 0.0
        for j in range(self.numHidden):
            sum = sum + self.hiddenActivations[j] * self.weightsOutput[j]
        self.outputActivation = logFunc(sum)
        return self.outputActivation

    def computeOutputDelta(self):
        """TO DO: Implement the delta function for the      output layer:
            O is the single output node;
            Pab can be interpreted as probability;
            ( 1 - Pab ) is the error we have to correct;
            Oa & Ob are outputs;
            g'(x) : derivative of the transfer function;
            Deltas[O]a and Deltas[O]b use to update output weights.
        """
        p_ab = logFunc(self.prevOutputActivation - self.outputActivation)            # Pab=1/(1/(1+e^-(Oa-Ob))    (1)
        self.prevDeltaOutput = logFuncDerivative(self.prevDeltaOutput) * (1 - p_ab)  # Delta[O]a = g'(Oa)*(1-Pab) (2)
        self.deltaOutput = logFuncDerivative(self.deltaOutput) * (1 - p_ab)          # Delta[O]b = g'(Ob)*(1-Pab) (3)

    def computeHiddenDelta(self):
        """TO DO: Implement the delta function for the      hidden layer:
            outha and outhb are the outputs from node h for input pattern a and b;
            wh,o is the weight from hidden node h to the single output node o;
        """
        del_dif = self.prevDeltaOutput - self.deltaOutput
        for i in xrange(self.numHidden):
            # Delta[H]a = g'(outHa)Wh,o(Delta[o]a - Delta[o]b)          (4)
            self.prevDeltaHidden[i] = logFuncDerivative(self.prevHiddenActivations[i]) * self.weightsOutput[i] * del_dif
            # Delta[H]b = g'(outHb)Wh,o(Delta[o]a - Delta[o]b)          (5)
            self.deltaHidden[i] = logFuncDerivative(self.hiddenActivations[i]) * self.weightsOutput[i] * del_dif

    def updateWeights(self):
        """ TO DO: Update the weights of the network using the deltas:
            Weight wi,j sends the output from node i to node j;
            a is the learning rate of the network;
            outia and outib are the outputs of node i for inputs a and b;
            Delta[j]a and Delta[j]b are the already calculated deltas of node j, for input pattern a and b.
        """
        # Wi,j = Wi,j + a * (Delta[j]a * outia - Delta[j]b * outib)     (6)
        for i in xrange(self.numInputs):
            for j in xrange(self.numHidden):
                self.weightsInput[i][j] += self.learningRate * (
                    self.prevDeltaHidden[j] * self.prevInputActivations[i] -
                    self.deltaHidden[j] * self.inputActivation[i])
        for i in xrange(self.numHidden):
            self.weightsOutput[i] += self.learningRate * (
                self.prevDeltaOutput * self.prevHiddenActivations[i] -
                self.deltaOutput * self.hiddenActivations[i])

    def backpropagate(self):
        self.computeOutputDelta()
        self.computeHiddenDelta()
        self.updateWeights()

    # Prints the network weights
    def weights(self):
        print('Input weights:')
        for i in range(self.numInputs):
            print(self.weightsInput[i])
        print()
        print('Output weights:')
        print(self.weightsOutput)

    def train(self, patterns, iterations = 1):
        # TO DO: Train the network on all patterns for a number of iterations.
        # To measure performance each iteration: Run for 1 iteration, then count misordered pairs.
        for i in xrange(iterations):
            # TO DO: Training is done  like this (details in exercise text):
            for pair in patterns:
                self.propagate(pair[0].features)        # -Propagate A
                self.propagate(pair[1].features)        # -Propagate B
                self.backpropagate()                    # -Backpropagate

    def countMisorderedPairs(self, patterns):
        # TO DO: Let the network classify all pairs of patterns. The highest output determines the winner.
        num_miss = 0
        num_right = 0
        for pair in patterns:                           # for each pair, do
            a = self.propagate(pair[0].features)        # Propagate A
            b = self.propagate(pair[1].features)        # Propagate B
            if a > b:                                   # if A > B: A wins. If B > A: B wins
                num_right += 1                          # if rating(winner) > rating(loser): numRight++
            else:
                num_miss += 1                           # else: numMisses++
        # TO DO: Calculate the ratio of correct answers:
        # print num_miss, num_right, float(num_miss) / (num_miss + num_right)
        return float(num_miss) / (num_miss + num_right) # errorRate = numMisses/(numRight+numMisses)
