package com.github.st235.aclub;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class UnionFind<T> {

    private static final int DEFAULT_SIZE = 1;

    private final Map<T, T> parents = new HashMap<>();
    private final Map<T, Integer> sizes = new HashMap<>();

    @NotNull
    T parent(@NotNull T node) {
        if (!parents.containsKey(node) || parents.get(node).equals(node)) {
            parents.putIfAbsent(node, node);
            return node;
        }

        parents.put(node, parent(node));
        return parents.get(node);
    }

    boolean union(@NotNull T one, @NotNull T another) {
        T oneParent = parent(one);
        T anotherParent = parent(another);

        if (oneParent.equals(anotherParent)) {
            return false;
        }

        int oneSize = sizes.getOrDefault(oneParent, DEFAULT_SIZE);
        int anotherSize = sizes.getOrDefault(anotherParent, DEFAULT_SIZE);

        if (oneSize > anotherSize) {
            int swap = oneSize;
            oneSize = anotherSize;
            anotherSize = swap;

            T parentSwap = oneParent;
            oneParent = anotherParent;
            anotherParent = parentSwap;
        }

        parents.put(anotherParent, oneParent);
        sizes.put(oneParent, oneSize + anotherSize);
        return true;
    }

}
