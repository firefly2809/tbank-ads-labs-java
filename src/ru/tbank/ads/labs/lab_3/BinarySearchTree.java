package ru.tbank.ads.labs.lab_3;

public class BinarySearchTree {

    public Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    public static BinarySearchTree create() {
        BinarySearchTree bst = new BinarySearchTree();

        // Создаем узлы
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        Node node9 = new Node(9);
        Node node10 = new Node(10);

        // Формируем дерево вручную
        bst.root = node5;
        node5.left = node3;
        node5.right = node7;
        node5.size = 10;

        node3.left = node1;
        node3.right = node4;
        node3.parent = node5;
        node3.size = 4;

        node4.parent = node3;
        node4.size = 1;

        node7.left = node6;
        node7.right = node9;
        node7.parent = node5;
        node7.size = 5;

        node6.parent = node7;
        node6.size = 1;

        node1.right = node2;
        node1.parent = node3;
        node1.size = 2;

        node2.parent = node1;
        node2.size = 1;

        node9.left = node8;
        node9.right = node10;
        node9.parent = node7;
        node9.size = 3;

        node8.parent = node9;
        node8.size = 1;

        node10.parent = node9;
        node10.size = 1;

        return bst;
    }

    //       5
    //    3  |    7
    //  1  4 | 6    9
    // 2     |    8  10
}