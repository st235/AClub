package aclub;

import com.github.st235.aclub.BinaryIndexedTree;

public class BinaryIndexedTreeExample {

    public static void main(String[] args) {
        BinaryIndexedTree<Float> tree = new BinaryIndexedTree<>(new Float[] { 1F, 5F, 3F, -1F }, new BinaryIndexedTree.CombineFunction<>() {
            @Override
            public Float zero() {
                return 0F;
            }

            @Override
            public Float combine(Float one, Float another) {
                return one + another;
            }

            @Override
            public Float inverse(Float one, Float another) {
                return one - another;
            }
        });

        System.out.println(tree.getOverall());

        tree.updateWithValue(1, 11.5F);
        System.out.println(tree.getOverall());

        System.out.println(tree.getUpTo(1));
        tree.updateWithDiff(0, 1F);

        System.out.println(tree.getInBetween(0, 1));
        System.out.println(tree.getOverall());
    }
}
