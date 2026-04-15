package ru.tbank.ads.labs.lab_9;

import java.util.Arrays;

public class ParkingFindKth_3_B {

    // ======= Вершина: количество свободных мест на отрезке =======
    static class Node {
        int sum;

        Node(int sum) {
            this.sum = sum;
        }
    }

    int n;
    Node[] tree;

    // ======= Слияние =======
    static Node merge(Node a, Node b) {
        return new Node(a.sum + b.sum);
    }

    // ======= Конструктор: все места свободны =======
    public ParkingFindKth_3_B(int n) {
        this.n = n;
        tree = new Node[4 * n];
        int[] a = new int[n];
        Arrays.fill(a, 1);
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

    // ======= Пометить занятым / свободным =======
    public void occupy(int pos) {
        update(1, 0, n - 1, pos, 0);
    }

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
        else update(2 * v + 1, tm + 1, tr, pos, val);
        tree[v] = merge(tree[2 * v], tree[2 * v + 1]);
    }

    // ======= Запрос: k-е свободное место (k начинается с 1) =======
    // Спускаемся по дереву:
    //   - в левом поддереве >= k свободных → ответ слева
    //   - иначе → ищем (k - leftSum)-е в правом
    // Сложность: O(log n)
    public int query(int k) {
        if (tree[1].sum < k) return -1;
        return query(1, 0, n - 1, k);
    }

    private int query(int v, int tl, int tr, int k) {
        // Лист — это и есть k-е свободное место
        if (tl == tr) return tl;

        int tm = (tl + tr) / 2;
        int leftSum = tree[2 * v].sum;
        if (k <= leftSum) {
            // k-е свободное находится в левом поддереве
            return query(2 * v, tl, tm, k);
        } else {
            // Вычитаем свободные из левого, ищем остаток в правом
            return query(2 * v + 1, tm + 1, tr, k - leftSum);
        }
    }

    // ======= Тест =======
    public static void main(String[] args) {
        ParkingFindKth_3_B p = new ParkingFindKth_3_B(8);

        System.out.println("1-е свободное: " + p.query(1)); // 0
        System.out.println("5-е свободное: " + p.query(5)); // 4

        p.occupy(0);
        p.occupy(2);
        p.occupy(4);
        // Свободны: 1, 3, 5, 6, 7
        System.out.println("\nПосле occupy(0,2,4). Свободны: 1,3,5,6,7");
        System.out.println("1-е свободное: " + p.query(1)); // 1
        System.out.println("2-е свободное: " + p.query(2)); // 3
        System.out.println("3-е свободное: " + p.query(3)); // 5
        System.out.println("5-е свободное: " + p.query(5)); // 7
        System.out.println("6-е свободное: " + p.query(6)); // -1

        p.free(0);
        // Свободны: 0, 1, 3, 5, 6, 7
        System.out.println("\nПосле free(0). Свободны: 0,1,3,5,6,7");
        System.out.println("1-е свободное: " + p.query(1)); // 0
        System.out.println("3-е свободное: " + p.query(3)); // 3
    }
}
