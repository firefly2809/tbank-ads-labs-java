package ru.tbank.ads.labs.lab_3;

public class Node {

    public int key;
    public Node left, right, parent; // Для некоторых задач
    public int size; // Для задач 3, 4, 5
    public int height; // Для задачи 6

    public Node(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.size = 1;
        this.height = 1;
    }
}