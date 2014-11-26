from simulatedAnnealing import SA
from random import shuffle, random
from copy import deepcopy

class Node:
        board, K = None, None
	
	def __init__(self, board):                              #Conduct a node
                self.board = board
		

	def F(self):                                      # Objective Function (0,1]
                y, l = 0, len(self.board)-1

                dr, dl = 0, 0
                for i in range(len(self.board)):               # Find egg numbers in each row
                        dr += self.board[i][i]                 
                        dl += self.board[l-i][i]
                        c = 0
                        for j in range(len(self.board)):
                                c += self.board[j][i]
                        y += max(0, c-self.K)
                y += max(0, dr-self.K) + max(0, dl-self.K)

                for i in range(self.K-1, len(self.board)-1):   #Find egg numbers in each diagonal
                        dtl, dtr, dbl, dbr = 0, 0, 0, 0
                        for j in range(0, i+1):
                                dtl += self.board[j][i-j]
                                dtr += self.board[j][l-i+j]
                                dbl += self.board[l-j][l-i+j]
                                dbr += self.board[l-j][i-j]
                        y += max(0, dtl-self.K) + max(0, dtr-self.K) + max(0, dbl-self.K) + max(0, dbr-self.K)

                return 1.0/(1+y)                           
                        


	def Gn(self):                            #Generate neighbors
                neighbors, columns = [], []
                for y in range(len(self.board)): columns.append([])
                for i in range(len(self.board)):             
                        for j in range(len(self.board)):
                                columns[j].append(i)

                toCheck = [False]*len(self.board)
                for q in columns:
                        if sum(q) > self.K:                    
                                for i in q:   
                                        toCheck[i] = toCheck[i] or i

                for r in range(len(toCheck)):                 
                        if toCheck[r]:                          #If this row has an egg in a column that had too many eggs..
                                new = deepcopy(self.board) 

                                if random() < 0.4:             
                                        shuffle(new[r])        
                                else:                          
                                        problemSq = []         
                                        for i in range(len(self.board)):
                                                problemSq.append(self.count(r, i))

                                        minIndex, maxIndex = problemSq.index(min(problemSq)), problemSq.index(max(problemSq))
                                        new[r][minIndex], new[r][maxIndex] = new[r][maxIndex], new[r][minIndex]
                                neighbors.append(Node(new))             
                return neighbors                                #Return the neighbors


        def count(self, row, column):            #Calculate number of conflicts
                c, dr, dl = 0, 0, 0
                for j in range(len(self.board)):              
                        c += self.board[j][column]

                startRowR = min(len(self.board)-1, row+column)
                endRowR = row - min(row, len(self.board)-column-1)
                columnR = column - (startRowR-row)
                for rowR in range(startRowR, endRowR-1, -1):  
                        dr += self.board[rowR][columnR]
                        columnR += 1
                
                
                startRowL = row + min(len(self.board)-row, len(self.board)-column)-1
                endRowL = max(0, row-column)
                columnL = column + (startRowL-row)
                for rowL in range(startRowL, endRowL-1, -1):  
                        dl += self.board[rowL][columnL]
                        columnL -= 1

                #Return total amount of conflicts for this element
                return max(0, c-self.K) + max(0, dr-self.K) + max(0, dl-self.K)               
                

def printBoard(board):                                         
      for i in board:
                print "",
                for j in i:
                        if j:
                                print "O ",
                        else:
                                print "# ",
                print ""



def eggCarton(M, K):                                           
        board, base = [], [0]*M                                
        for i in range(K): base[i] = 1                        
        
        for i in range(M):                                    
                temp = list(base)                              
                shuffle(temp)                                  
                board.append(temp)                        
        start = Node(board)
        Node.K = K
        
        counter = 0
        while True:                                            
                solution = SA(start, 1, 1<<12, 0.0625)         
                counter += 1                                   
                if solution.F() == 1.0:                  
                        printBoard(solution.board)             
                        print "Solution found to ("+str(M)+", "+str(K)+") after", counter, "attempts.\n\n"
                        break

eggCarton(5, 2)
eggCarton(6, 2)
eggCarton(8, 1)
eggCarton(10, 3)
