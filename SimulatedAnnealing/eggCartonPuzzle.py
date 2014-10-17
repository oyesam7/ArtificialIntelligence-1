from simulatedAnnealing import SA

class Node:
    M, N, K, board = None, None, None, None

    def __init__(self, board, K):
        self.board = board
        self.K = K
        M = len(board)
        N = len(board[0])
        if (K > math.min(M, N)):
            print
            "Wrong K value!!!"

    def F():                                    # objective Function return a evaluated quality in [0,1]:

                                                # horrible, mediocre, promising, excellent and optimal solutions...
                                                # toward the optimal solution.

    def Gn():                                   # generate neighbors

                                                # M: rows; N: columns; K: (row, column or diagonal) no more than K eggs

def eggCarton(M, N, K):
    board = [[False] * M] * N                   # build and empty N*M carton
    for k in range(K):
        board[0][k] = True                      # start at fill K eggs in first row
    state = Node(board, K)                      # build first state
    Tmax, dT = 99, 1                            # Tmax max temperature; dT: reduce temperature size
    Ftarget = math.min(M, N)
    while (True):
        solution = SA(state, Tmax, Ftarget, dT)
        Ftarget = math.max(Ftarget, solution)
