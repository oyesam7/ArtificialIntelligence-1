#!/usr/bin/python
import sys
import copy
import itertools

class CSP:
    def __init__(self):
        # self.variables is a list of the variable names in the CSP
        self.variables = []

        # self.domains[i] is a list of legal values for variable i
        self.domains = {}

        # self.constraints[i][j] is a list of legal value pairs for
        # the variable pair (i, j)
        self.constraints = {}

    def add_variable(self, name, domain):
        """Add a new variable to the CSP. 'name' is the variable name
        and 'domain' is a list of the legal values for the variable.
        """
        self.variables.append(name)
        self.domains[name] = list(domain)
        self.constraints[name] = {}

    def get_all_possible_pairs(self, a, b):
        """Get a list of all possible pairs (as tuples) of the values in
        the lists 'a' and 'b', where the first component comes from list
        'a' and the second component comes from list 'b'.
        product('ABCD', 'xy') --> Ax Ay Bx By Cx Cy Dx Dy
        """
        return itertools.product(a, b)

    def get_all_arcs(self):
        """Get a list of all arcs/constraints that have been defined in
        the CSP. The arcs/constraints are represented as tuples (i, j),
        indicating a constraint between variable 'i' and 'j'.
        product(A, B) returns the same as ((x,y) for x in A for y in B)
        """
        return [(i, j) for i in self.constraints for j in self.constraints[i]]

    def get_all_neighboring_arcs(self, var):
        """Get a list of all arcs/constraints going to/from variable
        'var'. The arcs/constraints are represented as in get_all_arcs().
        """
        return [(i, var) for i in self.constraints[var]]

    def add_constraint_one_way(self, i, j, filter_function):
        """Add a new constraint between variables 'i' and 'j'. The legal
        values are specified by supplying a function 'filter_function',
        that returns True for legal value pairs and False for illegal
        value pairs. This function only adds the constraint one way,
        from i -> j. You must ensure that the function also gets called
        to add the constraint the other way, j -> i, as all constraints
        are supposed to be two-way connections!
        self.constraints[key]      = {neighbor}
        self.constraints[key][key] = a list of domain (color pairs)
        """
        if not j in self.constraints[i]:
            # First, get a list of all possible pairs of values between variables i and j # domains[key]=domainList
            self.constraints[i][j] = self.get_all_possible_pairs(self.domains[i], self.domains[j])

        # Next, filter this list of value pairs through the function
        # 'filter_function', so that only the legal value pairs remain
        self.constraints[i][j] = filter(lambda value_pair: filter_function(*value_pair), self.constraints[i][j])

    def add_all_different_constraint(self, variables):
        """Add an Alldiff constraint between all of the variables in the
        list 'variables'.
        """
        for (i, j) in self.get_all_possible_pairs(variables, variables):
            if i != j:
                self.add_constraint_one_way(i, j, lambda x, y: x != y)

    def backtracking_search(self):
        """This functions starts the CSP solver and returns the found
        solution.
        """
        # Make a so-called "deep copy" of the dictionary containing the
        # domains of the CSP variables. The deep copy is required to
        # ensure that any changes made to 'assignment' does not have any
        # side effects elsewhere.
        assignment = copy.deepcopy(self.domains)

        # Run AC-3 on all constraints in the CSP, to weed out all of the
        # values that are not arc-consistent to begin with
        self.inference(assignment, self.get_all_arcs())

        # Call backtrack with the partial assignment 'assignment'
        return self.backtrack(assignment)

    def backtrack(self, assignment):
        """The function 'Backtrack' from the pseudocode in the
        textbook.

        The function is called recursively, with a partial assignment of
        values 'assignment'. 'assignment' is a dictionary that contains
        a list of all legal values for the variables that have *not* yet
        been decided, and a list of only a single value for the
        variables that *have* been decided.

        When all of the variables in 'assignment' have lists of length
        one, i.e. when all variables have been assigned a value, the
        function should return 'assignment'. Otherwise, the search
        should continue. When the function 'inference' is called to run
        the AC-3 algorithm, the lists of legal values in 'assignment'
        should get reduced as AC-3 discovers illegal values.

        IMPORTANT: For every iteration of the for-loop in the
        pseudocode, you need to make a deep copy of 'assignment' into a
        new variable before changing it. Every iteration of the for-loop
        should have a clean slate and not see any traces of the old
        assignments and inferences that took place in previous
        iterations of the loop.
        """
        # TODO: IMPLEMENT THIS
        # complete = True
        # for Di in assignment:
        # if not isinstance(assignment[Di], list): assignment[Di] = [assignment[Di]]
        # if len(assignment[Di]) > 1:
        # complete = False
        # break
        # if complete:
        # return assignment                               # if assignment is complete then return

        # assignment
        var = self.select_unassigned_variable(assignment)              # var < SELECT-UNASSIGNED-VARIABLE(csp)
        if len(var) == None: return assignment
        result = copy.deepcopy(assignment)
        if not isinstance(assignment[var], list):
            result[var] = [result[var]]                     # make sure assignment [var] is a list
        # for each value in ORDER-DOMAIN-VALUES(var,assignment,csp) do
        for value in self.order_domain_values(var, result):
            valueIsConsistent = False                # check if value is consistent with assignment
            boolList = []
            for x in self.get_all_neighboring_arcs(var):    # x = variable neighbor
                for y in x:                                 # y = variable neighbor to x
                    if y != var:                            #
                        for v in y:                         # v = domain color
                            if v != value:                  # color v != color value : sonsistent
                                boolList.append(True)
                                break
            if len(boolList) == len(self.get_all_neighboring_arcs(var)):
                valueIsConsistent = True

            print("assignment [var]", result[var])
            if valueIsConsistent:                               # if value is consistent with assignment then
                result[var] = None
                result[var] = value  # add {var = value} to assignment
                # queue = list(set(self.get_all_arcs()))             # queue = all arcs (queue datastructure is as set)
                queue = []
                for key in self.constraints[var]:
                    print("var,key", var, key)
                    queue.append((var, key))
                inferences = self.inference(result, queue)
                if inferences:                                            # if inference  != failure then
                    # assignment.append(inferences)         # add inferences to assignment
                    result = self.backtrack(result) # result < BACKTRACK(assignment, csp)
                    if result:                                      # if result != failure then
                        return result
            print("var:", var)
            print("assignment ", result)
            if var in result: del result[var]              # remove {var = value} and inferences from assignment
            # if inferences != None: del assignment[inferences]
        return False

    def order_domain_values(self, var, assignment):
        """
        The function decide what order should values be tried
        :param var:
        :param assignment:
        :return: an assignment list
        """
        print("assignment [var]", assignment[var])
        return assignment[var]

    def select_unassigned_variable(self, assignment):
        """The function 'Select-Unassigned-Variable' from the pseudocode
        in the textbook. Should return the name of one of the variables
        in 'assignment' that have not yet been decided, i.e. whose list
        of legal values has a length greater than one.
        """
        # TODO: IMPLEMENT THIS
        for Di in assignment:
            # print (Di)
            # if not isinstance(assignment[Di], list): assignment[Di] = [assignment[Di]]
            if len(assignment[Di]) > 1:
                return Di
            else: return None;

    def inference(self, assignment, queue):
        """The function 'AC-3' from the pseudocode in the textbook.
        'assignment' is the current partial assignment, that contains
        the lists of legal values for each undecided variable. 'queue'
        is the initial queue of arcs that should be visited.
        """
        # TODO: IMPLEMENT THIS
        # function AC-3(csp) returns false if an inconsistency is found and true otherwise
        # inputs: csp, a binary CSP with components (X, D, C)
        # local variables: queue, a queue of arcs, initially all the arcs in csp
        while len(queue) != 0:                          # while queue is not empty
            (Xi, Xj) = queue.pop(0)                                   # remove first nested list
            # if isinstance(Xi, tuple):
            # Xi, Xj = Xi[0], Xi[1]
            print("Xi,Xj: ", Xi, Xj)
            if self.revise(assignment, Xi, Xj):
                if len(self.domains[Xi]) == 0: return False            # if size of Di = 0 then return false
                for Xk in self.get_all_neighboring_arcs(Xi):              # for each Xk in Xi.NEIGHBORS - {Xj} do
                    if Xk != Xj:
                        queue.append([Xk, Xi])                     # add (Xk, Xi) to queue
        return True

    def revise(self, assignment, i, j):
        """The function 'Revise' from the pseudocode in the textbook.
        'assignment' is the current partial assignment, that contains
        the lists of legal values for each undecided variable. 'i' and
        'j' specifies the arc that should be visited. If a value is
        found in variable i's domain that doesn't satisfy the constraint
        between i and j, the value should be deleted from i's list of
        legal values in 'assignment'.
        """
        # TODO: IMPLEMENT THIS
        # function REVISE(csp, Xi, Xj) returns true if we revise the domain of Xi
        revised = False
        # for each x in Di:
        # print ( "self.domains[i] ",self.domains[i])
        # for x in self.domains[i]:
        # if no value y in Dj allows (x,y) to satisfy the constraint between Xi and Xj then
        # satisfyConstraint = False
        # for y in self.domains[j]:
        # if x != y:      # color x == y : not satisfy
        # satisfyConstraint = True
        # break
        # # delete x from Di
        # if not satisfyConstraint:
        # self.domains[i].remove(x)
        # revised = True
        print(assignment)
        for x in assignment[i]:
            for y in assignment[j]:
                if (x != y): revised = True
        return revised

def create_map_coloring_csp():
    """Instantiate a CSP representing the map coloring problem from the
    textbook. This can be useful for testing your CSP solver as you
    develop your code.
    """
    csp = CSP()
    states = ['WA', 'NT', 'Q', 'NSW', 'V', 'SA', 'T']
    edges = {'SA': ['WA', 'NT', 'Q', 'NSW', 'V'], 'NT': ['WA', 'Q'], 'NSW': ['Q', 'V']}
    colors = ['red', 'green', 'blue']
    for state in states:
        csp.add_variable(state, colors)
    for state, other_states in edges.items():
        for other_state in other_states:
            csp.add_constraint_one_way(state, other_state, lambda i, j: i != j)
            csp.add_constraint_one_way(other_state, state, lambda i, j: i != j)
    return csp

def create_sudoku_csp(filename):
    """Instantiate a CSP representing the Sudoku board found in the text
    file named 'filename' in the current directory.
    """
    csp = CSP()
    board = map(lambda x: x.strip(), open(filename, 'r'))

    for row in range(9):
        for col in range(9):
            if board[row][col] == '0':
                csp.add_variable('%d-%d' % (row, col), map(str, range(1, 10)))
            else:
                csp.add_variable('%d-%d' % (row, col), [board[row][col]])

    for row in range(9):
        csp.add_all_different_constraint(['%d-%d' % (row, col) for col in range(9)])
    for col in range(9):
        csp.add_all_different_constraint(['%d-%d' % (row, col) for row in range(9)])
    for box_row in range(3):
        for box_col in range(3):
            cells = []
            for row in range(box_row * 3, (box_row + 1) * 3):
                for col in range(box_col * 3, (box_col + 1) * 3):
                    cells.append('%d-%d' % (row, col))
            csp.add_all_different_constraint(cells)

    return csp

def print_sudoku_solution(solution):
    """Convert the representation of a Sudoku solution as returned from
    the method CSP.backtracking_search(), into a human readable
    representation.
    """
    for row in range(9):
        for col in range(9):
            print
            solution['%d-%d' % (row, col)][0],
            if col == 2 or col == 5:
                print
                '|',
        print
        if row == 2 or row == 5:
            print
            '------+-------+------'

if len(sys.argv) == 2:
    print("input file: ", sys.argv[1])
    csp = create_sudoku_csp(sys.argv[1])
    print_sudoku_solution(csp.backtracking_search())
else:
    csp = create_map_coloring_csp()
    # print("variables: ")
    # print(csp.variables)
    # print("\nConstraints:")
    # print(csp.constraints)
    print("\nsalution:")
    print(csp.backtracking_search())
