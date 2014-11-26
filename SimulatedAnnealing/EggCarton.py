from SimulatedAnnealing import SA
from copy import deepcopy
from time import time
from random import shuffle, random

class Node:
        board, K = None, None

	def __init__(self, board):                              #Only thing we need to know about each state is the current board
                self.board = board


	def F(self):                                      #objective Function return a evaluated quality in (0,1]
                y, l = 0, len(self.board)-1

                dr, dl = 0, 0
                for i in xrange(len(self.board)):               #This for-loop will find the amount of eggs that we have in excess on each column and
                        dr += self.board[i][i]                  #the main diagonals
                        dl += self.board[l-i][i]
                        c = 0
                        for j in xrange(len(self.board)):
                                c += self.board[j][i]
                        y += max(0, c-self.K)
                y += max(0, dr-self.K) + max(0, dl-self.K)

                for i in xrange(self.K-1, len(self.board)-1):   #This-for loop is to count the amount of excessive eggs in all the other diagonals
                        dtl, dtr, dbl, dbr = 0, 0, 0, 0
                        for j in xrange(0, i+1):
                                dtl += self.board[j][i-j]
                                dtr += self.board[j][l-i+j]
                                dbl += self.board[l-j][l-i+j]
                                dbr += self.board[l-j][i-j]
                        y += max(0, dtl-self.K) + max(0, dtr-self.K) + max(0, dbl-self.K) + max(0, dbr-self.K)
                #y will be the total minimum amount of errors in the carton. Because we want good solutions to have small value and bad
                #solutions have a large value, I return the inverse.
                return 1.0/(1+y)



	def Gn(self):                            #Generate some neighbors of the current state.
                neighbors, columns = [], []
                for y in xrange(len(self.board)): columns.append([])
                for i in xrange(len(self.board)):               #Generate a list of all the columns in current solution board
                        for j in xrange(len(self.board)):
                                columns[j].append(i)

                toCheck = [False]*len(self.board)
                for q in columns:
                        if sum(q) > self.K:                     #If a column has more eggs than allowed...
                                for i in q:                     #Then for every row in that column, note that row in toCheck
                                        toCheck[i] = toCheck[i] or i

                for r in xrange(len(toCheck)):                  #For every row in graph
                        if toCheck[r]:                          #If this row had an egg in a column that had too many eggs..
                                new = deepcopy(self.board)      #Copy current board

                                if random() < 0.4:              #With a probability of alpha, 0.4
                                        shuffle(new[r])         #Simply shuffle the entire row
                                else:                           #With a probability of 1-alpha, 0.6
                                        problemSq = []          #Run MIN-CONFLICTS algorithm from the book
                                        for i in xrange(len(self.board)):
                                                problemSq.append(self.countNumberConflicts(r, i))

                                        #Replace the cell of most conflicts with the cell of least conflicts in the problem row
                                        minIndex, maxIndex = problemSq.index(min(problemSq)), problemSq.index(max(problemSq))
                                        new[r][minIndex], new[r][maxIndex] = new[r][maxIndex], new[r][minIndex]
                                neighbors.append(Node(new))
                return neighbors                                #Return our neighbors!


        def countNumberConflicts(self, row, column):            #Method to calculate number of conflicts a specific cell has
                c, dr, dl = 0, 0, 0
                for j in xrange(len(self.board)):               #Sum of row conflics
                        c += self.board[j][column]

                startRowR = min(len(self.board)-1, row+column)
                endRowR = row - min(row, len(self.board)-column-1)
                columnR = column - (startRowR-row)
                for rowR in xrange(startRowR, endRowR-1, -1):   #Sum of diagonal bottom-right to top-left conflicts
                        dr += self.board[rowR][columnR]
                        columnR += 1


                startRowL = row + min(len(self.board)-row, len(self.board)-column)-1
                endRowL = max(0, row-column)
                columnL = column + (startRowL-row)
                for rowL in xrange(startRowL, endRowL-1, -1):   #Sum of diagonal top-right to bottom-left conflicts
                        dl += self.board[rowL][columnL]
                        columnL -= 1

                #Return total amount of conflicts for this cell
                return max(0, c-self.K) + max(0, dr-self.K) + max(0, dl-self.K)


def printBoard(board):                                          #Simple method to print the board
        for i in board:
                print "",
                for j in i:
                        if j:
                                print "O ",
                        else:
                                print "# ",
                print ""


def EggCarton(M, K):                                            #Main method to run an instance of Egg Carton problem
        board, base = [], [0]*M                                 #Initialize empty board and base row with empty cells
        for i in xrange(K): base[i] = 1                         #Fill first K elements with eggs (=1)

        for i in xrange(M):                                     #Create the start board
                temp = list(base)                               #By copying rows with K elements each
                shuffle(temp)                                   #And shuffling them
                board.append(temp)                              #And then adding them to the board, we get a randomly generated board!
        start = Node(board)
        Node.K = K

        counter = 0
        while True:                                             #Main run procedure
                solution = SA(start, 1, 999, 1)          #Solve current board!
                counter += 1                                    #Increase current solution attempt counter
                if solution.F() == 1.0:                   #As long as we do not succeed in solving the problem, start over
                        printBoard(solution.board)              #Once we do succeed, print the result
                        print "Solution found to ("+str(M)+", "+str(K)+") after", counter, "attempts.\n\n"
                        break

startTime = time()                                             #Run all the different problem instances
EggCarton(5, 2)
# EggCarton(6, 2)
# EggCarton(8, 1)
# EggCarton(10, 3)
print int((time()-startTime) * 1000), "ms"