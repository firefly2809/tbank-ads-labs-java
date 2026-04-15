package ru.tbank.ads.labs.lab_9;

import java.util.*;

public class ParkingCountFree_3_A {

    // ======= Вершина: количество свободных мест на отрезке =======
    static class Node {
        int sum;
        Node(int sum) {
            this.sum = sum;
        }
    }

    // Нейтральный элемент — не влияет на сумму
    static final Node NEUTRAL = new Node(0);

    int n;
    Node[] tree;

    // ======= Слияние двух вершин =======
    static Node merge(Node a, Node b) {
        return new Node(a.sum + b.sum);
    }

    // ======= Конструктор: все места изначально свободны =======
    public ParkingCountFree_3_A(int n) {
        this.n = n;
        tree = new Node[4 * n];
        int[] a = new int[n];
        Arrays.fill(a, 1); // 1 = свободно
        build(a, 1, 0, n - 1);
    }

    // ======= Построение =======
    private void build(int[] a, int v, int tl, int tr) {
        if (tl == tr) {
            tree[v] = new Node(a[tl]);
            return;
        }
        int tm = (tl + tr) / 2;
        build(a, 2 * v, tl, tm);
        build(a, 2 * v + 1, tm + 1, tr);
        tree[v] = merge(tree[2 * v], tree[2 * v + 1]);
    }

    // ======= Пометить место как занятое =======
    public void occupy(int pos) {
        update(1, 0, n - 1, pos, 0);
    }

    // ======= Пометить место как свободное =======
    public void free(int pos) {
        update(1, 0, n - 1, pos, 1);
    }

    private void update(int v, int tl, int tr, int pos, int val) {
        if (tl == tr) {
            tree[v] = new Node(val);
            return;
        }
        int tm = (tl + tr) / 2;
        if (pos <= tm) update(2 * v, tl, tm, pos, val);
        else           update(2 * v + 1, tm + 1, tr, pos, val);
        tree[v] = merge(tree[2 * v], tree[2 * v + 1]);
    }

    // ======= Запрос: количество свободных мест на [l, r] =======
    // Классическая сумма на отрезке.
    // Сложность: O(log n)
    public int query(int l, int r) {
        return query(1, 0, n - 1, l, r).sum;
    }

    private Node query(int v, int tl, int tr, int l, int r) {
        if (l > r) return NEUTRAL;
        if (l == tl && r == tr) return tree[v];
        int tm = (tl + tr) / 2;
        Node left  = query(2 * v, tl, tm, l, Math.min(r, tm));
        Node right = query(2 * v + 1, tm + 1, tr, Math.max(l, tm + 1), r);
        return merge(left, right);
    }

    // ======= Тест =======
    public static void main(String[] args) {
        ParkingCountFree_3_A p = new ParkingCountFree_3_A(8);

        System.out.println("Свободных на [0,7]: " + p.query(0, 7)); // 8

        p.occupy(2);
        p.occupy(5);
        System.out.println("После occupy(2), occupy(5):");
        System.out.println("Свободных на [0,7]: " + p.query(0, 7)); // 6
        System.out.println("Свободных на [0,3]: " + p.query(0, 3)); // 3
        System.out.println("Свободных на [2,5]: " + p.query(2, 5)); // 2

        p.free(2);
        System.out.println("После free(2):");
        System.out.println("Свободных на [0,3]: " + p.query(0, 3)); // 4
    }
}