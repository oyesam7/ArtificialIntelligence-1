package com.junjunguo.dtl;

import java.util.HashMap;
import java.util.Map;

/**
 * This file is part of java.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 15/04/16.
 */
public class Tree {
    private int                data;
    private Map<Integer, Tree> children;

    Tree(int data) {
        this.data = data;
        children = new HashMap<>();
    }

    void addChild(int key, Tree value) {
        children.put(key, value);
    }

    String generateTree() {
        if (children.size() == 0) return "[" + data + "]";
        else {
            String t = "[" + data;
            for (Tree child : children.values()) {
                if (child != null) t += child.generateTree();
            }
            t += "]";
            return t;
        }
    }

    public Map<Integer, Tree> getChildren() {
        return children;
    }

    public int getData() {
        return data;
    }
}
