package ru.tbank.ads.labs.lab_9;

import java.util.Arrays;

public class SegmentTreeMinCount_1_A {

    // ======= Объект вершины — вся информация в одном месте =======
    static class Node {
        int min;
        int count;

        Node(int min, int count) {
            this.min = min;
            this.count = count;
        }
    }

    // Нейтральный элемент — не влияет на результат слияния
    static final Node NEUTRAL = new Node(Integer.MAX_VALUE, 0);

    int n;
    Node[] tree;

    // ======= Слияние двух узлов =======
    static Node merge(Node a, Node b) {
        if (a.min < b.min) return a;
        if (b.min < a.min) return b;
        return new Node(a.min, a.count + b.count);
    }

    // ======= Конструктор =======
    public SegmentTreeMinCount_1_A(int[] a) {
        n = a.length;
        tree = new Node[4 * n];  // просто берем с запасом, чтобы дерево умещалось всегда
        build(a, 1, 0, n - 1);
    }

    // ======= Построение =======
    private void build(int[] a, int v, int tl, int tr) {
        if (tl == tr) {  // дошли до листа
            tree[v] = new Node(a[tl], 1);
            return;
        }
        int tm = (tl + tr) / 2;
        build(a, 2 * v, tl, tm);  // левый ребенок
        build(a, 2 * v + 1, tm + 1, tr); // правый
        tree[v] = merge(tree[2 * v], tree[2 * v + 1]);
    }

    // ======= Обновление: a[pos] = val =======
    public void update(int pos, int val) {
        update(1, 0, n - 1, pos, val);
    }

    private void update(int v, int tl, int tr, int pos, int val) {
        if (tl == tr) {
            tree[v] = new Node(val, 1);
            return;
        }
        int tm = (tl + tr) / 2;
        if (pos <= tm) update(2 * v, tl, tm, pos, val);
        else update(2 * v + 1, tm + 1, tr, pos, val);
        tree[v] = merge(tree[2 * v], tree[2 * v + 1]);
    }

    // ======= Запрос на отрезке [l, r] =======
    public Node query(int l, int r) {
        return query(1, 0, n - 1, l, r);
    }

    private Node query(int v, int tl, int tr, int l, int r) {
        if (l > r) return NEUTRAL;
        if (l == tl && r == tr) return tree[v];

        int tm = (tl + tr) / 2;
        Node left = query(2 * v, tl, tm, l, Math.min(r, tm));
        Node right = query(2 * v + 1, tm + 1, tr, Math.max(l, tm + 1), r);
        return merge(left, right);
    }

    // ======= Тест =======
    public static void main(String[] args) {
        int[] a = {3, 1, 2, 1, 4};
        System.out.println("Массив: " + Arrays.toString(a));

        SegmentTreeMinCount_1_A st = new SegmentTreeMinCount_1_A(a);

        Node res = st.query(0, 4);
        System.out.println("query(0,4): min=" + res.min + " count=" + res.count);
        // min=1, count=2

        res = st.query(2, 4);
        System.out.println("query(2,4): min=" + res.min + " count=" + res.count);
        // min=1, count=1

        st.update(1, 5);  // [3, 5, 2, 1, 4]
        System.out.println("После update(1, 5):");

        res = st.query(0, 4);
        System.out.println("query(0,4): min=" + res.min + " count=" + res.count);
        // min=1, count=1

        st.update(3, 2);  // [3, 5, 2, 2, 4]
        System.out.println("После update(3, 2):");

        res = st.query(0, 4);
        System.out.println("query(0,4): min=" + res.min + " count=" + res.count);
        // min=2, count=2
    }
}
