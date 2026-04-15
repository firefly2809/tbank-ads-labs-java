package ru.tbank.ads.labs.lab_9;

import java.util.*;

public class ParkingFindNearest_3_C {

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
    public ParkingFindNearest_3_C(int n) {
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
    public void occupy(int pos) { update(1, 0, n - 1, pos, 0); }
    public void free(int pos)   { update(1, 0, n - 1, pos, 1); }

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

    // ======= Первое свободное на [l, r] (минимальный индекс) =======
    // Спуск с приоритетом ВЛЕВО.
    // Отсечение: sum == 0 → нет свободных.
    // Сложность: O(log^2 n)
    private int findFirstFree(int v, int tl, int tr, int l, int r) {
        if (l > tr || r < tl) return -1;   // вне диапазона
        if (tree[v].sum == 0) return -1;    // отсечение: нет свободных
        if (tl == tr) return tl;             // лист — он свободен
        int tm = (tl + tr) / 2;
        // Сначала влево — там меньшие индексы
        int res = findFirstFree(2 * v, tl, tm, l, r);
        if (res != -1) return res;
        return findFirstFree(2 * v + 1, tm + 1, tr, l, r);
    }

    // ======= Последнее свободное на [l, r] (максимальный индекс) =======
    // Спуск с приоритетом ВПРАВО.
    // Отсечение: sum == 0 → нет свободных.
    // Сложность: O(log^2 n)
    private int findLastFree(int v, int tl, int tr, int l, int r) {
        if (l > tr || r < tl) return -1;   // вне диапазона
        if (tree[v].sum == 0) return -1;    // отсечение: нет свободных
        if (tl == tr) return tl;             // лист — он свободен
        int tm = (tl + tr) / 2;
        // Сначала вправо — там бо́льшие индексы
        int res = findLastFree(2 * v + 1, tm + 1, tr, l, r);
        if (res != -1) return res;
        return findLastFree(2 * v, tl, tm, l, r);
    }

    // ======= Запрос: ближайшее свободное место к позиции i =======
    // Ищем двух кандидатов:
    //   - последнее свободное на [0, i]   (ближайшее слева)
    //   - первое свободное на [i, n-1]    (ближайшее справа)
    // Выбираем того, кто ближе. При равном расстоянии — меньший индекс.
    // Сложность: O(log^2 n)
    public int query(int i) {
        if (tree[1].sum == 0) return -1;

        int left  = findLastFree(1, 0, n - 1, 0, i);
        int right = findFirstFree(1, 0, n - 1, i, n - 1);

        if (left == -1) return right;
        if (right == -1) return left;

        int distLeft  = i - left;
        int distRight = right - i;
        // При равном расстоянии — выбираем левый (меньший индекс)
        if (distLeft <= distRight) return left;
        return right;
    }

    // ======= Тест =======
    public static void main(String[] args) {
        ParkingFindNearest_3_C p = new ParkingFindNearest_3_C(8);

        p.occupy(3);
        p.occupy(4);
        p.occupy(5);
        // Свободны: 0, 1, 2, 6, 7
        System.out.println("Свободны: 0, 1, 2, 6, 7");
        System.out.println("Ближайшее к 4: " + p.query(4)); // 2
        System.out.println("Ближайшее к 5: " + p.query(5)); // 6
        System.out.println("Ближайшее к 0: " + p.query(0)); // 0
        System.out.println("Ближайшее к 7: " + p.query(7)); // 7

        p.occupy(6);
        p.occupy(7);
        // Свободны: 0, 1, 2
        System.out.println("\nПосле occupy(6,7). Свободны: 0, 1, 2");
        System.out.println("Ближайшее к 5: " + p.query(5)); // 2
        System.out.println("Ближайшее к 1: " + p.query(1)); // 1

        p.free(4);
        // Свободны: 0, 1, 2, 4
        System.out.println("\nПосле free(4). Свободны: 0, 1, 2, 4");
        System.out.println("Ближайшее к 5: " + p.query(5)); // 4
        System.out.println("Ближайшее к 3: " + p.query(3)); // 2
    }
}
