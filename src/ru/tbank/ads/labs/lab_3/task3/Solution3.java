package ru.tbank.ads.labs.lab_3.task3;

import ru.tbank.ads.labs.lab_3.BinarySearchTree;
import ru.tbank.ads.labs.lab_3.Node;

public class Solution3 {

    public static void main(String[] args) {
        BinarySearchTree bst = BinarySearchTree.create();
        getKSmallestSafe(bst, 3);
        getKSmallestSafe(bst, 9);
        getKSmallestSafe(bst, 11);
    }

    private static void getKSmallestSafe(BinarySearchTree bst, int k) {
        Node smallest = findKthSmallest(bst.root, k);
        if (smallest != null) {
            System.out.println(smallest.key);
        } else {
            System.out.println("Not found");
        }
    }

    static Node findKthSmallest(Node root, int k) {
        Node current = root;
        while (current != null) {
            int leftSize = (current.left == null) ? 0 : current.left.size;

            if (k == leftSize + 1) {
                return current;
            } else if (k <= leftSize) {
                current = current.left;
            } else {
                current = current.right;
                k -= (leftSize + 1);
            }
        }
        return null; // Not found
    }

}
