__author__ = 'kaiolae'
__author__ = 'kaiolae'
import Backprop_skeleton as Bp

import matplotlib.pyplot as plt
import sys

# Class for holding your data - one object for each line in the dataset
class dataInstance:
    def __init__(self, qid, rating, features):
        self.qid = qid # ID of the query
        self.rating = rating # Rating of this site for this query
        self.features = features # The features of this query-site pair.

    def __str__(self):
        return "Datainstance - qid: " + str(self.qid) + ". rating: " + str(self.rating) + ". features: " + str(
            self.features)

# A class that holds all the data in one of our sets (the training set or the testset)
class dataHolder:
    def __init__(self, dataset):
        self.dataset = self.loadData(dataset)

    def loadData(self, file):
        """Input: A file with the data.
        Output: A dict mapping each query ID to the relevant documents, like this:
        dataset[queryID] = [dataInstance1, dataInstance2, ...]
        """
        data = open(file)
        dataset = {}
        for line in data:
            # Extracting all the useful info from the line of data
            lineData = line.split()
            rating = int(lineData[0])
            qid = int(lineData[1].split(':')[1])
            features = []
            for elem in lineData[2:]:
                if '#docid' in elem: # We reached a comment. Line done.
                    break
                features.append(float(elem.split(':')[1]))
            # Creating a new data instance, inserting in the dict.
            di = dataInstance(qid, rating, features)
            if qid in dataset.keys():
                dataset[qid].append(di)
            else:
                dataset[qid] = [di]
        return dataset

def runRanker(trainingset, testset, iter_num):
    # TO DO: Insert the code for training and testing your ranker here.
    # Dataholders for training and testset
    dhTraining = dataHolder(trainingset)
    dhTesting = dataHolder(testset)

    # Creating an ANN instance - feel free to experiment with the learning rate (the third parameter).
    nn = Bp.NN(46, 10, 0.001)

    # TO DO: The lists below should hold training patterns in this format: [(data1Features,data2Features),
    # (data1Features,data3Features), ... , (dataNFeatures,dataMFeatures)]
    # TO DO: The training set needs to have pairs ordered so the first item of the pair has a higher rating.
    trainingPatterns = []   # For holding all the training patterns we will feed the network
    testPatterns = []       # For holding all the test patterns we will feed the network
    print "start training set ..."
    for qid in dhTraining.dataset.keys():
        # This iterates through every query ID in our training set
        dataInstance = dhTraining.dataset[qid] # All data instances (query, features, rating) for query qid
        # TO DO: Store the training instances into the trainingPatterns array. Remember to store them as pairs,
        # where the first item is rated higher than the second.
        # TO DO: Hint: A good first step to get the pair ordering right, is to sort the instances based on their
        # rating for this query. (sort by x.rating for each x in dataInstance)
        dataInstance = sorted(dataInstance, key = lambda x: x.rating, reverse = True)
        for i in xrange(len(dataInstance) - 1):
            for j in xrange(i + 1, len(dataInstance)):
                if dataInstance[i].rating == dataInstance[j].rating:
                    continue
                trainingPatterns.append((dataInstance[i], dataInstance[j]))
    print "start testing set ..."
    for qid in dhTesting.dataset.keys():
        # This iterates through every query ID in our test set
        dataInstance = dhTesting.dataset[qid]
        # TO DO: Store the test instances into the testPatterns array, once again as pairs.
        # TO DO: Hint: The testing will be easier for you if you also now order the pairs - it will make it easy to
        # see if the ANN agrees with your ordering.
        dataInstance = sorted(dataInstance, key = lambda x: x.rating, reverse = True)
        for i in xrange(len(dataInstance) - 1):
            for j in xrange(i + 1, len(dataInstance)):
                if dataInstance[i].rating == dataInstance[j].rating:
                    continue
                testPatterns.append((dataInstance[i], dataInstance[j]))
    # Check ANN performance before training
    nn.countMisorderedPairs(testPatterns)
    err_train = []
    err_test = []
    print "start iterations ... total = ", iter_num
    for i in range(iter_num): # 25
        sys.stdout.write(i.__str__() + " ")
        # Running 25 iterations, measuring testing performance after each round of training.
        # Training
        nn.train(trainingPatterns, iterations = 1)
        # Check ANN performance after training.
        # nn.countMisorderedPairs(testPatterns)
        # TO DO: Store the data returned by countMisorderedPairs and plot it, showing how training and testing errors
        # develop.
        err_train.append(nn.countMisorderedPairs(trainingPatterns))
        err_test.append(nn.countMisorderedPairs(testPatterns))
    print ""
    return [err_train, err_test]

def run(run_num, iter_num):
    """run_num:        how many times the runRank() will run;
        iter_num:   iterations (epochs) for training and testing;
        draw run_num of test miss rate lines
        draw run_num of train miss rate line
        draw an average test and an average train miss rate line.
    """
    av_err_train = [0.0 for i in xrange(iter_num)]
    av_err_test = [0.0 for i in xrange(iter_num)]
    line_color = ['r', 'g', 'b', 'gold', 'purple', 'black', 'skyblue', 'm', 'maroon', 'crimson', 'navy']
    for i in xrange(run_num):
        print "run", i + 1
        err_train, err_test = runRanker("../datasets/train.txt", "../datasets/test.txt", iter_num)
        for j in xrange(iter_num):
            av_err_train[j] += (err_train[j] / run_num)
        for j in xrange(iter_num):
            av_err_test[j] += (err_test[j] / run_num)
        plt.plot(err_train, "k--", label = "$training - {i}$".format(i = (i + 1)),
                 color = line_color[i % line_color.__len__()])
        plt.plot(err_test, "k-", label = "$test - {i}$".format(i = (i + 1)),
                 color = line_color[i % line_color.__len__()])
    plt.plot(av_err_train, "k--", label = "training average", color = line_color[run_num % line_color.__len__()])
    plt.plot(av_err_test, "k-", label = "test average", color = line_color[run_num % line_color.__len__()])
    plt.legend(loc = 'best')
    plt.show()

run(5, 25)
