package ru.tbank.ads.labs.lab_3.task4;

import ru.tbank.ads.labs.lab_3.BinarySearchTree;
import ru.tbank.ads.labs.lab_3.Node;

public class Solution4 {

    public static void main(String[] args) {
        BinarySearchTree bst = BinarySearchTree.create();
        System.out.println(countLessThanOrEqual(bst.root, 3));
        System.out.println(countLessThanOrEqual(bst.root, 9));
        System.out.println(countLessThanOrEqual(bst.root, 15));
    }

    static int countLessThanOrEqual(Node root, int x) {
        int count = 0;
        Node current = root;

        while (current != null) {
            if (current.key <= x) {
                count += (current.left != null ? current.left.size : 0) + 1;
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return count;
    }

}
