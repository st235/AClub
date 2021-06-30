package com.github.st235.aclub;

import org.jetbrains.annotations.NotNull;

public class BinaryIndexedTree<T extends Number> {

    public interface CombineFunction<T extends Number> {
        T zero();
        T combine(T one, T another);
        T inverse(T one, T another);
    }

    private final Object[] tree;
    private final CombineFunction<T> combineFunction;

    public BinaryIndexedTree(int n, CombineFunction<T> combineFunction) {
        this.tree = new Object[n + 1];
        this.combineFunction = combineFunction;
    }

    public BinaryIndexedTree(T[] array, CombineFunction<T> combineFunction) {
        this.tree = new Object[array.length + 1];
        this.combineFunction = combineFunction;

        for (int i = 0; i < array.length; i++) {
            updateWithDiff(i, array[i]);
        }
    }

    @NotNull
    public T getValue(int index) {
        return combineFunction.inverse(getUpTo(index), getUpTo(index - 1));
    }

    @NotNull
    public T getInBetween(int lowerIndex, int finishIndex) {
        return combineFunction.inverse(getUpTo(finishIndex), getUpTo(lowerIndex - 1));
    }

    @NotNull
    public T getOverall() {
        return getUpTo(tree.length - 2);
    }

    @NotNull
    public T getUpTo(int index) {
        T value = combineFunction.zero();
        int current = index + 1;

        while (isValid(current)) {
            value = combineFunction.combine(value, nodeFromTree(current));
            current = parent(current);
        }

        return value;
    }

    public void updateWithValue(int index, @NotNull T newValue) {
        T actualValue = getValue(index);
        T diff = combineFunction.inverse(newValue, actualValue);
        updateWithDiff(index, diff);
    }

    public void updateWithDiff(int index, @NotNull T diff) {
        int current = index + 1;

        while (isValid(current)) {
            tree[current] = combineFunction.combine(nodeFromTree(current), diff);
            current = sibling(current);
        }
    }

    @SuppressWarnings(value = "unchecked")
    private T nodeFromTree(int index) {
        T node = (T) tree[index];

        if (node == null) {
            return combineFunction.zero();
        }

        return node;
    }

    private int parent(int index) {
        return index - leastSignificantOne(index);
    }

    private int sibling(int index) {
        return index + leastSignificantOne(index);
    }

    private boolean isValid(int index) {
        // 0 - is a dummy node
        return index > 0 && index < tree.length;
    }

    private int leastSignificantOne(int index) {
        return (~index + 1) & index;
    }

}
