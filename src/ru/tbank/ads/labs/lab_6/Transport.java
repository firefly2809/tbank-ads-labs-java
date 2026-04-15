package ru.tbank.ads.labs.lab_6;

import java.util.*;

//В министерстве транспорта хотят запретить все маршруты между городами, стоимость которых
//больше чем C. Помогите им найти такое минимальное C, что из любого города можно будет по-
//прежнему добраться до любого другого (правда, возможно, теперь с пересадками). O((n+m)log W ).

//Найти минимальный и максимальный вес ребра
//Бинарный поиск по C
//Для каждого C:
//  запускаем BFS
//      учитываем только рёбра с weight ≤ C
//      проверяем, посетили ли все вершины

class Edge implements Comparable<Edge> {
    int u, v, weight;

    Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

public class Transport {

    static int[] parent;
    static int[] rank;

    static void makeSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++)
            parent[i] = i;
    }

    static int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]);
        return parent[x];
    }

    static boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB)
            return false;

        if (rank[rootA] < rank[rootB])
            parent[rootA] = rootB;
        else if (rank[rootA] > rank[rootB])
            parent[rootB] = rootA;
        else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }

        return true;
    }

    static int findMinimalC(int n, List<Edge> edges) {
        Collections.sort(edges);

        makeSet(n);

        int maxEdge = 0;
        int edgesUsed = 0;

        for (Edge e : edges) {
            if (union(e.u, e.v)) {
                maxEdge = Math.max(maxEdge, e.weight);
                edgesUsed++;

                if (edgesUsed == n - 1)
                    break;
            }
        }

        return maxEdge;
    }
}
