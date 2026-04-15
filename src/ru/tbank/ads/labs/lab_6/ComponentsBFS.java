package ru.tbank.ads.labs.lab_6;

// Найти количество компонент связности с помощью BFS

//Один запуск BFS обходит ровно одну компоненту.
//
//Идём по всем вершинам
//Если вершина не посещена —
//запускаем BFS
//увеличиваем счётчик компонент

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ComponentsBFS {

    static void bfs(int start, List<List<Integer>> graph, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int v = queue.poll();

            for (int neighbor : graph.get(v)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }

    static int countComponents(int n, List<List<Integer>> graph) {
        boolean[] visited = new boolean[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                bfs(i, graph, visited);
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {

        int n = 5;

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++)
            graph.add(new ArrayList<>());

        // Компонента 1: 0-1-2
        graph.get(0).add(1);
        graph.get(1).add(0);

        graph.get(1).add(2);
        graph.get(2).add(1);

        // Компонента 2: 3-4
        graph.get(3).add(4);
        graph.get(4).add(3);

        System.out.println(countComponents(n, graph)); // 2
    }
}
