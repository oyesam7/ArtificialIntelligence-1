IntroductionArtificialIntelligence
==================================

Introduction to Artificial Intelligence
---

Epic Introduction
--
####Artificial intelligence:
- Acting Humanly
    - if we can't distinguish between a computer and a human, the computer is said to act humanly.
    - turing test: computer's capability to act humanly
        - Natural language processing
        - Konwledge representation
        - Automated reasoning 
        - Machine learning
- Thinking humanly
- Acting Rationally
    - An agent is something that acts.
    - A rational agent is an agent that does the right thing.
- Thinking rationally

Intelligent Agent
--
Agent
- perceiving its environment through sensors and acting upon that environment through actuators.

Agent function

Rational agent
- select an action which is expected to maximize performance.

Task environment
- PEAS  -   Performance, Environment, Actuators, Sensors.

Properties of task environments

```
Fully Observable    vs      Partially Observable
Single agent        vs      multiagent
Deterministic       vs      Stochastic
Episodic            vs      Sequential
Static              vs      Dynamic
Discrete            vs      Continous
Known               vs      Unknown
```
`

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
    - The final estimated cost never decreases.
- In grid-search
        - Manhattan distance 
        - Euclidean - The length one would measure with a ruler ( straight line distance )

Dominance

Relaxed problems
- the optimal solution cost of a relaxed problem is no greater than the optimal solution cost of the real problem

Beyond Classical Search
--
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

Adversarial Search
--
MiniMax

Alpha-Beta Pruning

Alpha

Beta

AI Critics

Constraint Satisfaction Problems
--
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

Local Search for CSPs

Logical Agents
--
Knowledge base: set of sentences in a formal language.

Logics:
- Formal languages to represent information so that conclusions might be made.

Syntax:
- defines how sentences are constructed.

Semantics:
- the meaning of the sentence.

World:
- A possible world (model) ...

Entailment:
- Means that on ething follows from another.

Satisfiable:

Valid:

Number of Models:

Horn Clauses:

Standard logical equivalence

CNF     =   Conjunction Normal Form

Resolution

First-Order Logic
--
first order logic contains:
- objects
- relations
- functions

Quantifiers

Inference in First-Order Logic
--
Unification:    Matching a free variable with a constant.

Forward chaining:   Start with base facts and try to find out which facts fires which rule. Stop when the goal is reached.

Backward chaining:  Start with the goal and work the way backward. (a depth-first search).

Conversion to CNF

Classical Planning
--
Plan:   a collection of actions for performing some task.

STRIPS

PDDL:       Planning Domain Definition Language

Planning graph

Planning and Acting in the real World
--
Critical path method:       the path whose total duration is longest

Knowledge representation
--
Knowledge-based system (KBS)
- is a model of something is the real world (outside the agent). 3 Modeling:
    - Knowledge engineer    design, builds and tests the 'expert system'
    - Domain expert         possesses the skill & knowledge to find a solution
    - End user              the final system should meet the needs of the end user.

- KB represent knowledge using KB langauge, a system encoding knowledge. The inference engine has the ability to find implicit knowledge by reasoning over the explicit knowledge. Decides what kind og conclusions can be drawn. 
    - Declarative knowledge     Expressed in declarative sentences or indicative propositions.
    - Procedural knowledge      Knowledge exercised in the performance of soe task.
    - Domain knowledge          What we reason about
    - Strategic knowledge       How we reason.

5 roles of knowledge representation:
     * Surrogate: A representation is a substitute for direct interaction with the world.
     * All representations are approximations to reality and they are invariably imperfect
     * Fragmentary theory of Intelligent Reasoning
     * Is a medium for efficient computation
     * Is a medium of human expression

Rule based systems (early KBs expert systems)
- Working memory
- Rule base
- Interpreter

KR languages
- Syntactic
- Semantic
- Inferential

Requirements
- Representation adequacy
- Inferential adequacy
- Inferential efficiency
- Naturalness

Semantic networks
    - stores knowledge in a graph, with nodes representing objects in the world, and arcs representing relationships between objects. Use inheritance when an object belong to a class.

Inheritance in semantic networks

Natural Language Processing
--
    - Knowledge aquisition & language models

Language models:        Grammar,    semantic

N-gram char models

Model evaluation

N-gram word models

Text classification

Information retrieval

Models and IR Scoring functions

Evaluation

Refinements

The HITS algorithm

Question answering

Information Extraction
- Return useful information from documents.

