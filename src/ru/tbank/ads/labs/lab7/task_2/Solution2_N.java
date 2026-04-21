package ru.tbank.ads.labs.lab7.task_2;

import java.util.Deque;
import java.util.LinkedList;

public class Solution2_N {

    public static int minCost(int[] cost, int n, int k) {
        if (n == 1) return cost[1];

        int[] dp = new int[n + 1];
        Deque<Integer> deque = new LinkedList<>();
        dp[1] = cost[1];
        deque.offer(1);

        for (int i = 2; i <= n; i++) {
            if (!deque.isEmpty() && deque.peekFirst() < i - k) {
                deque.pollFirst();
            }

            dp[i] = cost[i] + dp[deque.peekFirst()];
            while (!deque.isEmpty() && dp[deque.peekLast()] >= dp[i]) {
                deque.pollLast();
            }
            deque.offer(i);
        }

        return dp[n];
    }

    public static void main(String[] args) {
        int[] cost = {0, 1, 2, 2, 4};  // Индекс 0 не используется
        int n = 4;
        int k = 3;
        System.out.println("Minimum cost to reach cell " + n + " with jump size up to " + k + " is: " + minCost(cost, n, k));
    }
}

