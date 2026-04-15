package ru.tbank.ads.labs.lab_9;

public class SegTreeAltSum_1_C {

    static class Node {
        long evenSum;
        long oddSum;

        Node(long evenSum, long oddSum) {
            this.evenSum = evenSum;
            this.oddSum = oddSum;
        }
    }

    static final Node NEUTRAL = new Node(0, 0);

    static Node merge(Node a, Node b) {
        return new Node(a.evenSum + b.evenSum, a.oddSum + b.oddSum);
    }

    int n;
    Node[] tree;

    public SegTreeAltSum_1_C(int[] a) {
        n = a.length;
        tree = new Node[4 * n];
        build(a, 1, 0, n - 1);
    }

    private void build(int[] a, int v, int tl, int tr) {
        if (tl == tr) {
            if (tl % 2 == 0)
                tree[v] = new Node(a[tl], 0);
            else
                tree[v] = new Node(0, a[tl]);
            return;
        }
        int tm = (tl + tr) / 2;
        build(a, 2 * v, tl, tm);
        build(a, 2 * v + 1, tm + 1, tr);
        tree[v] = merge(tree[2 * v], tree[2 * v + 1]);
    }

    public void update(int pos, int val) {
        update(1, 0, n - 1, pos, val);
    }

    private void update(int v, int tl, int tr, int pos, int val) {
        if (tl == tr) {
            if (pos % 2 == 0)
                tree[v] = new Node(val, 0);
            else
                tree[v] = new Node(0, val);
            return;
        }
        int tm = (tl + tr) / 2;
        if (pos <= tm) update(2 * v, tl, tm, pos, val);
        else           update(2 * v + 1, tm + 1, tr, pos, val);
        tree[v] = merge(tree[2 * v], tree[2 * v + 1]);
    }

    // Возвращает знакочередующуюся сумму на [l, r]
    public long query(int l, int r) {
        Node res = queryNode(1, 0, n - 1, l, r);
        if (l % 2 == 0)
            return res.evenSum - res.oddSum;
        else
            return res.oddSum - res.evenSum;
    }

    private Node queryNode(int v, int tl, int tr, int l, int r) {
        if (l > r) return NEUTRAL;
        if (l == tl && r == tr) return tree[v];
        int tm = (tl + tr) / 2;
        Node left = queryNode(2 * v, tl, tm, l, Math.min(r, tm));
        Node right = queryNode(2 * v + 1, tm + 1, tr, Math.max(l, tm + 1), r);
        return merge(left, right);
    }

    public static void main(String[] args) {
        int[] a = {5, 3, 8, 2, 7};
        SegTreeAltSum_1_C st = new SegTreeAltSum_1_C(a);

        // 3 - 8 + 2 - 7 = -10
        System.out.println("query(1,4): " + st.query(1, 4));

        // 5 - 3 + 8 - 2 + 7 = 15
        System.out.println("query(0,4): " + st.query(0, 4));

        // 8 - 2 = 6
        System.out.println("query(2,3): " + st.query(2, 3));

        // 5
        System.out.println("query(0,0): " + st.query(0, 0));

        st.update(2, 10);  // [5, 3, 10, 2, 7]

        // 5 - 3 + 10 - 2 + 7 = 17
        System.out.println("query(0,4): " + st.query(0, 4));
    }
}