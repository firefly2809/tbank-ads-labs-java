package ru.tbank.ads.labs.lab_6;

import java.util.*;

//Инспектору нужно проверить состояние дорог в городе, для этого он хочет проехать по каждой
//дороге в каждую сторону (все дороги двусторонние). Постройте кратчайший путь. O(n+m).

public class InspectorRoute {

    static class Edge {
        int to;
        boolean used;

        Edge(int to) {
            this.to = to;
            this.used = false;
        }
    }

    static List<List<Edge>> graph;
    static List<Integer> path = new ArrayList<>();

    static void dfs(int v) {
        path.add(v);

        for (Edge e : graph.get(v)) {
            if (!e.used) {
                e.used = true;
                dfs(e.to);
                path.add(v); // возвращаемся обратно
            }
        }
    }

    public static void main(String[] args) {
        int n = 4;

        graph = new ArrayList<>();
        for (int i = 0; i < n; i++)
            graph.add(new ArrayList<>());

        // добавляем дороги
        graph.get(0).add(new Edge(1));
        graph.get(1).add(new Edge(0));

        graph.get(1).add(new Edge(2));
        graph.get(2).add(new Edge(1));

        graph.get(2).add(new Edge(3));
        graph.get(3).add(new Edge(2));

        dfs(0);

        System.out.println(path);
    }
}
