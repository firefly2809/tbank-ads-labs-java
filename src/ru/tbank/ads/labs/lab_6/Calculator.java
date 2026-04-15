package ru.tbank.ads.labs.lab_6;

import java.util.*;

// Калькулятор умеет делать две операции: a = (a +3)% M и a = (a * 4)% M . За сколько операций
//можно получить из числа a число b? Время O(M).



public class Calculator {

    static int minOperations(int a, int b, int M) {

        boolean[] visited = new boolean[M];
        int[] dist = new int[M];

        Queue<Integer> queue = new LinkedList<>();

        queue.add(a);
        visited[a] = true;
        dist[a] = 0;

        while (!queue.isEmpty()) {

            int current = queue.poll();

            if (current == b)
                return dist[current];

            // операция +3
            int next1 = (current + 3) % M;

            if (!visited[next1]) {
                visited[next1] = true;
                dist[next1] = dist[current] + 1;
                queue.add(next1);
            }

            // операция *4
            int next2 = (current * 4) % M;

            if (!visited[next2]) {
                visited[next2] = true;
                dist[next2] = dist[current] + 1;
                queue.add(next2);
            }
        }

        return -1; // если недостижимо
    }

    public static void main(String[] args) {
        int M = 10;
        int a = 1;
        int b = 7;

        System.out.println(minOperations(a, b, M));
    }
}