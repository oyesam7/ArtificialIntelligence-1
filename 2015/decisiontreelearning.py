import math
import random

def decisionTreeLearning(examples, attributes, parentExamples, rndImp):
    '''Figure 18.5 The decision-tree learning algorithm. The function IMPORTANCE is described in Section 18.3.4.
    The function PLURALITY-VALUE selects the most common output value among a set of examples, breaking ties
    randomly.'''
    if not examples: return Node(pluralityValue(parentExamples))
    elif sameClassification(examples): return Node(examples[0][-1])
    elif not attributes: return Node(pluralityValue(examples))
    else:
        if rndImp: A = randomImportance(attributes)
        else:  A = gainImportance(attributes, examples)

        tree = Node(A)
        attributes.remove(A)

        for nr in range(1, 3):
            exp = [e for e in examples if int(e[A]) == nr]
            subtree = decisionTreeLearning(exp, list(attributes), examples, rndImp)
            tree.addBranch(nr, subtree)
    return tree

def pluralityValue(data):
    '''return output value of majority \n take the most frequent class in the data leaf and return as prediction'''
    counter = {}
    for p in data:
        if p[-1] in counter:
            counter[p[-1]] += 1
        else:
            counter[p[-1]] = 1
    return max(counter, key = counter.get)

class Node():
    '''A node with data and a list of children'''

    def __init__(self, data):
        self.data = data
        self.children = {}

    def addBranch(self, key, value): self.children[key] = value

    def generateTree(self):
        '''Generate a string of tree: the node and its children added recursively; \n Can be viewed by syntax tree
        generator: http://mshang.ca/syntree/'''
        if len(self.children) == 0: return "[" + str(self.data) + "]"
        else: tree = "[" + str(self.data)
        for k, v in self.children.items(): tree += self.children[k].generateTree()
        return tree + "]"

def sameClassification(data):
    '''True if all data classifications are same, False otherwise'''
    for line in data:
        if line[-1] != data[0][-1]: return False
    return True

def gainImportance(attributes, examples):
    ''' :return attribute to split on, assumes boolean classification'''
    entropy = {}
    for a in attributes:
        counter = 0
        for line in examples:
            if line[a] == examples[0][a]:
                counter += 1

        entropy[a] = B(counter / len(examples))
    return min(entropy, key = entropy.get)

def B(q):
    ''' :return: boolean entropy'''
    if q == 0: return 0
    return -(q * math.log(q, 2) + ((1.0 - q) * math.log(1.0 - q, 2)))

def randomImportance(attributes):
    '''  :return: attribute to split'''
    return attributes[random.randint(0, len(attributes) - 1)]

def classify(root, line):
    '''Classify a single entry given the root of the classification tree'''
    current = root
    while current.children: current = current.children[int(line[current.data])]
    return current.data

def readfile(filename):
    '''Read .txt file \n return a two dimensional list'''
    file = open("exercise4data/" + filename + ".txt")
    return [row.split("	") for row in file.read().split("\n")]

def runTest(tree, data):
    '''calculate the number of correct results'''
    correct = 0
    for line in data:
        if line[-1] == classify(tree, line):
            correct += 1
    print "Correct", correct, "out of", len(data), "Rate:", (float(correct) / len(data)) * 100, "%"

def run():
    trainingList = readfile("training")
    testList = readfile("test")
    for i in range(2):
        root = decisionTreeLearning(trainingList, range(7), [], i == 1)
        print "The root tree: use random importance = ", i == 1
        print root.generateTree()
        runTest(root, testList)
        runTest(root, trainingList)
        print "----"
run()
