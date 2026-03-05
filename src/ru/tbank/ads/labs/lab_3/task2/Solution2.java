package ru.tbank.ads.labs.lab_3.task2;

import java.util.ArrayList;

// Node Structure
class NodeTraversal {
    int data;
    NodeTraversal left, right;

    NodeTraversal(int x) {
        data = x;
        left = right = null;
    }
}

class GFG {

    static ArrayList<Integer> inOrder(NodeTraversal root) {
        ArrayList<Integer> res = new ArrayList<>();
        NodeTraversal curr = root;

        while (curr != null) {
            if (curr.left == null) {

                // If no left child, visit this node
                // and go right
                res.add(curr.data);
                curr = curr.right;
            }
            else {

                // Find the inorder predecessor of curr
                NodeTraversal prev = curr.left;
                while (prev.right != null &&
                        prev.right != curr) {
                    prev = prev.right;
                }

                // Make curr the right child of its
                // inorder predecessor
                if (prev.right == null) {
                    prev.right = curr;
                    curr = curr.left;
                }
                else {

                    // Revert the changes made in
                    // the tree structure
                    prev.right = null;
                    res.add(curr.data);
                    curr = curr.right;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {

        // Representation of input binary tree:
        //           1
        //          / \
        //         2   3
        //        / \
        //       4   5
        NodeTraversal root = new NodeTraversal(1);
        root.left = new NodeTraversal(2);
        root.right = new NodeTraversal(3);
        root.left.left = new NodeTraversal(4);
        root.left.right = new NodeTraversal(5);

        ArrayList<Integer> res = inOrder(root);

        for (int data : res) {
            System.out.print(data + " ");
        }
    }
}