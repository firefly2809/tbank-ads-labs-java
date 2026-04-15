package ru.tbank.ads.labs.lab_6;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Friends {

    static int countFriends(int n, List<List<Integer>> graph, int start) {
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(start);
        visited[start] = true;

        int size = 0;

        while (!queue.isEmpty()) {
            int v = queue.poll();
            size++;

            for (int neighbor : graph.get(v)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }

        return size - 1; // исключаем самого мальчика
    }

    public static void main(String[] args) {

        int n = 6;
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

        // 5 — изолирован

        int boy = 0;

        System.out.println(countFriends(n, graph, boy)); // 2
    }
}
