package ru.tbank.ads.labs.lab_10.task5;

import java.io.*;
import java.util.*;

class Query {
    int l, r, idx;
    Query(int l, int r, int idx) {
        this.l = l;
        this.r = r;
        this.idx = idx;
    }
}

class SegmentTree {
    int n;
    int[] tree;

    SegmentTree(int size) {
        n = size;
        tree = new int[4 * n];
    }

    void update(int v, int tl, int tr, int pos, int delta) {
        if (tl == tr) {
            tree[v] += delta;
        } else {
            int tm = (tl + tr) / 2;
            if (pos <= tm) {
                update(v * 2, tl, tm, pos, delta);
            } else {
                update(v * 2 + 1, tm + 1, tr, pos, delta);
            }
            tree[v] = tree[v * 2] + tree[v * 2 + 1];
        }
    }

    int query(int v, int tl, int tr, int l, int r) {
        if (l > r) return 0;
        if (l == tl && r == tr) {
            return tree[v];
        }
        int tm = (tl + tr) / 2;
        return query(v * 2, tl, tm, l, Math.min(r, tm)) +
                query(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r);
    }

    // Удобные методы
    void update(int pos, int delta) {
        update(1, 0, n - 1, pos, delta);
    }

    int query(int l, int r) {
        return query(1, 0, n - 1, l, r);
    }
}

public class Task5 {
    static int[] a;
    static int[] count;
    static int currentUniqueCount = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        // Чтение n
        int n = Integer.parseInt(br.readLine());
        int[] a = new int[n];
        String[] tokens = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(tokens[i]);
        }

        // Чтение q
        int q = Integer.parseInt(br.readLine());
        Query[] queries = new Query[q];
        for (int i = 0; i < q; i++) {
            tokens = br.readLine().split(" ");
            int l = Integer.parseInt(tokens[0]) - 1; // в 0-индексацию
            int r = Integer.parseInt(tokens[1]) - 1;
            queries[i] = new Query(l, r, i);
        }

        // Сортируем запросы по r (правой границе)
        Arrays.sort(queries, Comparator.comparingInt(x -> x.r));

        // Максимальное значение для last[] — можно использовать Map, если значения большие
        // Предположим, что значения до 1_000_000
        final int MAX_VAL = 10;
        int[] last = new int[MAX_VAL + 1];
        Arrays.fill(last, -1);

        SegmentTree segTree = new SegmentTree(n);
        int[] ans = new int[q];
        int qIdx = 0;

        // Основной цикл: двигаем r от 0 до n-1
        for (int r = 0; r < n; r++) {
            int val = a[r];

            // Если это не первое вхождение — удаляем предыдущее
            if (val >= 0 && val <= MAX_VAL && last[val] != -1) {
                segTree.update(last[val], -1);
            }

            // Ставим 1 на текущую позицию
            segTree.update(r, 1);
            last[val] = r;

            // Отвечаем на все запросы с r == текущему
            while (qIdx < q && queries[qIdx].r == r) {
                Query query = queries[qIdx];
                int res = segTree.query(query.l, query.r);
                ans[query.idx] = res;
                qIdx++;
            }
        }

        // Вывод ответов
        for (int x : ans) {
            out.println(x);
        }

        out.flush();
        out.close();
        br.close();
    }
}

