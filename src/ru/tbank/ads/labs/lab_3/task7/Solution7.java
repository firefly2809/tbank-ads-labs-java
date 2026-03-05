package ru.tbank.ads.labs.lab_3.task7;

class TreeNode7 {
    int val;
    TreeNode7 left;
    TreeNode7 right;
    int sum; // Сумма всех узлов в поддереве данного узла

    public TreeNode7(int val) {
        this.val = val;
        this.sum = val;
        this.left = null;
        this.right = null;
    }
}

class BinarySearchTree7 {
    private TreeNode7 root;

    public void add(int val) {
        root = insert(root, val);
    }

    private TreeNode7 insert(TreeNode7 node, int val) {
        if (node == null) {
            return new TreeNode7(val);
        }

        if (val < node.val) {
            node.left = insert(node.left, val);
        } else if (val > node.val) {
            node.right = insert(node.right, val);
        }

        node.sum += val;
        return node;
    }


    public int rangeSum(int limit) {
        return rangeSum(root, limit);
    }

    private int rangeSum(TreeNode7 node, int limit) {
        if (node == null) {
            return 0;
        }

        if (node.val <= limit) {
            return node.val + (node.left != null ? node.left.sum : 0) + rangeSum(node.right, limit);
        } else {
            return rangeSum(node.left, limit);
        }
    }
}

class Main {
    public static void main(String[] args) {
        BinarySearchTree7 bst = new BinarySearchTree7();
        bst.add(10);
        bst.add(5);
        bst.add(15);
        bst.add(3);
        bst.add(7);
        bst.add(18);

        // 40 - 15
        int sum = bst.rangeSum(15) - bst.rangeSum(7);
        System.out.println("Sum of values between 7 and 15 is: " + sum);
    }
}