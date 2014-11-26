IntroductionArtificialIntelligence
==================================

Introduction to Artificial Intelligence
---
Simple reflex agent: 
- Selects actions on the basis of the current percept, ignoring the rest og the history.

Model-based reflex agents

Goal-based agents

Utility-based agents:
- trying to maximize the expected 'happiness'. Needs a utility function to determine what action leads to the heighest performance measuer. 

Learning agents: Learn from their actions.
1. Learning element
    - making improvements
2. Performance element
    - selecting external actions.
3. Critic 
    - give feedback ... 
4. Problem generator

###Solving Problems by Searching
Search: a process for finding something.
Uninformed  vs informed
Partial     vs Complete solutions
Single-state problem formulation

Breadth-first search
- FIFO queue

Uniform-cost search
- queue is ordered by path cost

Depth-fist serach
- LIFO

Depth-limited search
- depth-first search + depth limit

Iterative deepening search

Best first search
- Idea: use an evluation function for each node - estimate of 'desirability'. 
- Expand the most desirable unexpanded node. Implementation: nodes are put in a queue sorted in decreasing order of desirability.

Greedy search

A*
- A* is a form of best-first search where the idea is not to expand 'expensive' paths.
- A* evaluates nodes with: f(n) = g(n) + h(n)

A* is optimal
- A* algorithm always expands the node with the lowest f(n) value, and the h(n) value never overestimates, it will never be possible to reach the goal with a lower cost than the cost of the node we're expanding. Therefor, the first node we expand, and turns out to be a solution, will also be the optimal solution. 

Heuristic:
- Admissible
    - Always understimated -- guarantees that we will find the minimum cost path.
- Consistency
    - The cost of moving is higher than the reduction in the heuristic. 
    - The fina estimated cost never decreases.
- In grid-search
        - Manhattan distance 
        - Euclidean - The length one would measure with a ruler ( straight line distance )

Dominance

Relaxed problems
- the optimal solution cost of a relaxed problem is no greater than the optimal solution cost of the real problem

Local Search
- All nodes in local search are complete solutions. As the search progresses, solutions will become gradually better. 
- The path is unimportant; only the final state matters. 
- Local search doesn't use heuristics, but a similar concept called **objective functions**. The objective function answers the question "How optimal the solution is".

Hill climbling
- **Greedy**: always moves to states with immediate benefits.
- Quick in smooth landscapes.

Simulated Annealing
- Randomized search for a solution. 
- It prioritizes neighbors that improve the situation, accept other neighbors also.

Local Beam Search:  K parallel searches

Genetic Algorithms

MiniMax

Alpha-Beta Pruning

Alpha

Beta

AI Critics

###Constraint Satisfaction Problems
CSP:
    - a set of variables
    - a set of domains(legal values) for these variables
    - a set of constraint relations between the variables

CSP solutions:
    - Initial state
    - Successor function
    - Goal test

Varieties of constraints

Consistency

Node consistency

Arc consistency

Path consistency

Forward checking

Backtracking Search for CSPs

