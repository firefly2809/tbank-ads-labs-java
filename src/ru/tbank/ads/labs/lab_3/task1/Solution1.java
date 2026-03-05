package ru.tbank.ads.labs.lab_3.task1;

import ru.tbank.ads.labs.lab_3.BinarySearchTree;
import ru.tbank.ads.labs.lab_3.Node;

class Solution1 {

    public static void main(String[] args) {
        BinarySearchTree bst = BinarySearchTree.create();
        inorderTraversal(bst.root);
    }

    static void inorderTraversal(Node node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.println(node.key);
            inorderTraversal(node.right);
        }
    }

}