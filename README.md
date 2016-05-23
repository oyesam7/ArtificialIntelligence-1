Introduction to Artificial Intelligence
==

**[Artificial Intelligence Method](ai_method.md)**

AI is the study of agents that receive percepts from the environment and perform actions. Each such agent implements a function that maps percept that maps percept sequences to actions, and there are different ways to represent these functions, such as rective agents, real-time planners, and decision-theoretic systems.
Introduction
--
We call ourselves Homo sapiens—man the wise—because our intelligence is so important to us. For thousands of years, we have tried to understand how we think; that is, how a mere handful of matter can perceive, understand, predict, and manipulate a world far larger and more complicated than itself. The field of artificial intelligence, or AI, goes further still: it attempts not just to understand but also to build intelligent entities.
####Artificial intelligence:
Four approaches to artificial intelligent:
- Acting Humanly
    - if we can't distinguish between a computer and a human, the computer is said to act humanly.
    - **turing test:** computer's capability to act humanly
        - Natural language processing
        - Konwledge representation
        - Automated reasoning
        - Machine learning
    - turing test is the most known scenario for testing the intelligence of an articial intelligence system.
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
- maps any given percept sequence to an action.

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

3 Solving Problems by Searching
--
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
####3.5.2 A* search: Minimizing the total estimated solution cost
A* search (A-start search): most widely known best-first search.
- A* is a form of best-first search where the idea is not to expand 'expensive' paths.
- A* evaluates nodes with: f(n) = g(n) + h(n)
    - g(n) the cost from start to reach the node n(start -> n)
    - h(n) (heuristic function) the cost to get from the node n to the goal (n -> goal)

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

4 Beyond Classical Search
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

5 Adversarial Search
--
####MiniMax
    is a decision rule used in decision theory, game theory, statistics and philosophy for minimizing the possible loss for a worst case (maximum loss) scenario. Originally formulated for two-player zero-sum game theory, covering both the cases where players take alternate moves and those where they make simultaneous moves, it has also been extended to more complex games and to general decision making in the presence of uncertainty.

###Alpha-Beta Pruning
    Alpha Beta purning is a way to make Minmax search much more effective by cut off some path, which will never influence the choice. This is done by assigning provisional values (alpha values at Max nodes, beta values at Min nodes). Only a max node can modify alpha and only Min nodes can modify beta.
    The alpha and beta values are defined locally at different nodes. They can also be passed in from above. pruning does not affect the final result but can change the efficiency of the search.

**Alpha**
    Amodifiable property of a Max node.

**Beta**
    a modifiable property of a Min node.

AI Critics

6 Constraint Satisfaction Problems
--
CSP:
    - a set of variables {X1, ..., Xn}
    - a set of domains(legal values) for these variables {D1,..., Dn}
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

###6.4 Local Search for CSPs
Min-conflicts: 
    In choosing a new value for a variable, the most obvious heuristic is to select the value that results in the minimum number of conflicts with other variables—the min-conflicts heuristic.
7 Logical Agents
--
Knowledge base: set of sentences in a formal language.

Logics:
- Formal languages to represent information so that conclusions might be made.

####7.4.1 Syntax:
- defines how sentences are constructed

**Complex sentences** are constructed from simpler sentences, using parentheses and logical connectives.
- negation:       ¬ (not). A sentence such as ¬W1,3 is called the negation of W1,3. 
A literal is either an atomic sentence (a positive literal) or a negated atomic sentence (a negative literal).
- conjunction:    ∧ (and). A sentence whose main connective is ∧, such as W1,3 ∧ P3,1, is called a conjunction; its parts are the conjuncts. (The ∧ looks like an “A” for “And.”)
- disjunction:    ∨ (or). A sentence using ∨, such as (W1,3 ∧P3,1)∨W2,2, is a disjunction of the disjuncts (W1,3 ∧ P3,1) and W2,2. (Historically, the ∨ comes from the Latin “vel,” which means “or.” For most people, it is easier to remember ∨ as an upside-down ∧.)
- implication:    ⇒ (implies). A sentence such as (W1,3 ∧P3,1) ⇒ ¬W2,2 is called an implication (or conditional). Its premise or antecedent is (W1,3 ∧P3,1), and its conclusion or consequent is ¬W2,2. Implications are also known as rules or if–then statements. The implication symbol is sometimes written in other books as ⊃ or →.
- biconditional:  ⇔ (if and only if). The sentence W1,3 ⇔ ¬W2,2 is a biconditional. Some other books write this as ≡.


Semantics:
- the meaning of the sentence.

five rules for complex sentences, which hold for any subsentences P and Q in any model m (here “iff” means “if and only if”):
- ¬P    is true iff P is false in m.
- P ∧ Q is true iff both P and Q are true in m.
- P ∨ Q is true iff either P or Q is true in m.
- P ⇒ Q is true unless P is true and Q is false in m.
- P ⇔ Q is true iff P and Q are both true or both false in m.

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

######Standard logical equivalences:
          (α ∧ β) ≡ (β ∧ α) commutativity of ∧
          (α ∨ β) ≡ (β ∨ α) commutativity of ∨
    ((α ∧ β) ∧ γ) ≡ (α ∧ (β ∧ γ)) associativity of ∧
    ((α ∨ β) ∨ γ) ≡ (α ∨ (β ∨ γ)) associativity of ∨
            ¬(¬α) ≡ α double-negation elimination
          (α ⇒ β) ≡ (¬β ⇒ ¬α) contraposition
          (α ⇒ β) ≡ (¬α ∨ β) implication elimination
          (α ⇔ β) ≡ ((α ⇒ β) ∧ (β ⇒ α)) biconditional elimination
         ¬(α ∧ β) ≡ (¬α ∨ ¬β) DeMorgan
         ¬(α ∨ β) ≡ (¬α ∧ ¬β) DeMorgan
    (α ∧ (β ∨ γ)) ≡ ((α ∧ β) ∨ (α ∧ γ)) distributivity of ∧ over ∨
    (α ∨ (β ∧ γ)) ≡ ((α ∨ β) ∧ (α ∨ γ)) distributivity of ∨ over ∧

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

######Resolution
The resolution inference rule:
- The resolution rule for first-order clauses is simply a lifted version of the propositional reso- lution rule given on page 253. Two clauses, which are assumed to be standardized apart so that they share no variables, can be resolved if they contain complementary literals.

        [Animal(F(x)) ∨ Loves(G(x),x)] and [¬Loves(u,v) ∨ ¬Kills(u,v)]
        
        by eliminating the complementary literals Loves(G(x),x) and ¬Loves(u,v), with unifier θ = {u/G(x), v/x}, to produce the resolvent clause
        
        [Animal(F(x)) ∨ ¬Kills(G(x),x)] 
    This rule is called the **binary resolution rule** because it resolves exactly two literals

10 Classical Planning
--
Plan:   a collection of actions for performing some task.

STRIPS

PDDL:       Planning Domain Definition Language

###10.3 Planning graphs
A mutex holds when:

     * Inconsistent effects: one action negates an effect of the other.
     * Interference: one of the effects of one action is the negation of a precondition of the
       other.
     * Competing needs: one of the preconditions of one action is mutually exclusive with a
       precondition of the other

11 Planning and Acting in the real World
--
Critical path method:       the path whose total duration is longest

12 Knowledge representation
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
    - contains facts about the world, observed directly or derived from a rule.
- Rule base
    - contains rules, where each rule is a stop i a problem solving process
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

22 Natural Language Processing
--
    - Knowledge aquisition & language models

Language models:        Grammar,    semantic

N-gram char models

Model evaluation

N-gram word models

###22.2 Text classification
text classification, also known as categorization: given a text of some kind, decide which of a predefined set of classes it belongs to. Language identification and genre classification are examples of text classification, as is **sentiment analysis** (classifying a movie or product review as positive or negative) and spam detection (classifying an email message as spam or not-spam).

Information retrieval

Models and IR Scoring functions

Evaluation

Refinements

The HITS algorithm

Question answering

###22.4 Information Extraction
- Return useful information from documents.
- Information extraction is the process of acquiring knowledge by skimming a text and looking for occurrences of a particular class of object and for relationships among objects.



Frame language:
- A frame language is a technology used for knowledge representation in artificial intelligence. Frames are stored as ontologies of sets and subsets of the frame concepts. They are similar to class hierarchies in object-oriented languages although their fundamental design goals are different. Frames are focused on explicit and intuitive representation of knowledge where as objects focus on encapsulation and information hiding. Frames originated in AI research and objects primarily in software engineering. However, in practice the techniques and capabilities of frame and object-oriented languages overlap significantly.
