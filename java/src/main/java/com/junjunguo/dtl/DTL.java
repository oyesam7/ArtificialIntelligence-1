package com.junjunguo.dtl;

import java.io.Serializable;
import java.util.*;

/**
 * This file is part of java.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 15/04/16.
 * <p>
 * Figure 18.5  page: 702
 */
public class DTL {

    /**
     * Decision tree learning tree.
     *
     * @param examples       the examples
     * @param attributes     the attributes
     * @param parentExamples the parent examples
     * @param randImp        the importance
     * @return a tree
     */
    Tree decisionTreeLearning(List<List<Integer>> examples, List<Integer> attributes,
            List<List<Integer>> parentExamples, boolean randImp) {
        Tree tree;
        //    if examples is empty then return PLURALITY-VALUE(parent examples)
        if (examples == null || examples.size() == 0) return new Tree(pluralityValue(parentExamples));
            //    else if all examples have the same classification then return the classification
        else if (sameClassification(examples)) return new Tree(examples.get(0).get(examples.get(0).size() - 1));
            //    else if attributes is empty then return PLURALITY-VALUE(examples)
        else if (attributes == null || attributes.size() == 0) return new Tree(pluralityValue(examples));
        else {
            //            add a branch to tree with label (A = vk) and subtree subtree
            int A; // the best tree
            //        A (best) ← argmaxa ∈ attributes IMPORTANCE(a, examples)
            if (randImp) A = (int) randomImportance(attributes);
            else A = (int) gainImportance(attributes, examples);
            //        tree ← a new decision tree with root test A (best)
            tree = new Tree(A);
            attributes.remove((Integer) A);
            //                        System.out.println(attributes.size());
            //        for each value vk of A (best) do
            for (int i = 1; i < 3; i++) {
                //            exs ←{e : e∈examples and e.A = vk} {elements of examples with best = vk }
                List<List<Integer>> exs = new ArrayList<>();
                for (List<Integer> example : examples) {
                    if (example.get(A) == i) exs.add(example);
                }
                //            subtree ← DECISION-TREE-LEARNING(exs, attributes − A, examples)
                Tree subTree = decisionTreeLearning(exs, attributes, examples, randImp);
                tree.addChild(i, subTree);
            }

        }
        return tree;
    }

    /**
     * Gain importance : Gain (A) = B (p / (p + n)) - Remainder(A)
     *
     * @param attributes the attributes
     * @param examples   the examples
     * @return the attribute to split on
     */
    double gainImportance(List<Integer> attributes, List<List<Integer>> examples) {
        Map<Integer, Double> entropy = new HashMap<>();
        for (int attribute : attributes) {
            int positiveEx = 0;
            for (List<Integer> line : examples) {
                if (line.get(attribute).equals(examples.get(0).get(attribute))) positiveEx++;
            }
            entropy.put(attribute, B(positiveEx / examples.size()));
        }
        return Collections.min(entropy.entrySet(), (Comparator<Map.Entry<Integer, Double>> & Serializable) (a, b) ->
                a.getValue().compareTo(b.getValue())).getKey();
    }

    /**
     * B double q: B(q) the entropy of a Boolean random variable. B(q) = −(q log2 q + (1 − q) log2(1 − q))
     *
     * @param q the q
     * @return the double
     */
    double B(double q) {
        if (q == 0) return 0;
        return -(q * (Math.log(q) / Math.log(2)) + (1 - q) * (Math.log(1 - q) / Math.log(2)));
    }

    /**
     * Random importance int.
     *
     * @param attributes the attributes
     * @return a random number from attributes
     */
    float randomImportance(List<Integer> attributes) {
        return attributes.get((int) (Math.random() * (attributes.size() - 1)));
    }

    /**
     * Same classification boolean.
     *
     * @param data the data
     * @return true if all data classifications are same, false otherwise.
     */
    boolean sameClassification(List<List<Integer>> data) {
        int classes = data.get(0).get(data.get(0).size() - 1);
        for (List<Integer> l : data) {
            if (l.get(l.size() - 1) != classes) return false;
        }
        return true;
    }

    /**
     * Plurality value selects the most common output value among a set of examples
     *
     * @param data the data
     * @return the most frequent class in the data
     */
    int pluralityValue(List<List<Integer>> data) {
        Map<Integer, Integer> counter = new HashMap<>();
        for (List<Integer> l : data) {
            int     key   = l.get(l.size() - 1);
            Integer count = counter.get(key);
            if (counter.containsKey(key)) counter.put(key, count + 1);
            else counter.put(key, 1);
        }
        return Collections.max(counter.entrySet(), (Comparator<Map.Entry<Integer, Integer>> & Serializable) (a, b) ->
                a.getValue().compareTo(b.getValue())).getKey();
    }


    /**
     * Test: calculate the number of correct results
     *
     * @param tree the tree
     * @param data the data
     */
    void test(Tree tree, List<List<Integer>> data) {
        int correct = 0;
        for (List<Integer> line : data) {
            if (line.get(line.size() - 1).equals(classification(tree, line))) correct++;
        }
        double rate = Math.round(((double) correct * 100 / (double) data.size()) * 10d) / 10d;
        System.out.println(
                "Correct = " + correct + ", Total = " + data.size() + ", Rate = " + rate + " %");
    }

    /**
     * Classification: classify a single entry given the root of the classification tree.
     *
     * @param tree the tree
     * @param line the line
     * @return the integer
     */
    int classification(Tree tree, List<Integer> line) {
        Tree current = tree;
        while (current.getChildren().size() != 0) {
            current = current.getChildren().get(line.get(current.getData()));
        }
        return current.getData();
    }
}
