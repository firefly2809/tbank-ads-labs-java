package ru.tbank.ads.labs.lab_6;

// Проверьте, что заданный граф – связный. O(n +m).

//Представим граф списком смежности
//Создадим массив visited[]
//Запустим DFS из вершины 0
//Проверим — все ли вершины посещены

import java.util.ArrayList;
import java.util.List;

public class ConnectedGraph {

    static void dfs(int v, List<List<Integer>> graph, boolean[] visited) {
        visited[v] = true;

        for (int neighbor : graph.get(v)) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited);
            }
        }
    }

    static boolean isConnected(int n, List<List<Integer>> graph) {
        boolean[] visited = new boolean[n];

        dfs(0, graph, visited);

        for (boolean v : visited) {
            if (!v) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        int n = 5;

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++)
            graph.add(new ArrayList<>());

        // добавим рёбра
        graph.get(0).add(1);
        graph.get(1).add(0);

        graph.get(1).add(2);
        graph.get(2).add(1);

        graph.get(2).add(3);
        graph.get(3).add(2);

        graph.get(3).add(4);
        graph.get(4).add(3);

        System.out.println(isConnected(n, graph));
    }

}
