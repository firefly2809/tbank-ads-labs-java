package ru.tbank.ads.labs.lab7.task_2;

public class Solution2_NK {
    public static int minCost(int[] cost, int n, int k) {
        if (n == 1) return cost[1];

        int[] dp = new int[n + 1];
        dp[1] = cost[1];

        for (int i = 2; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 1; j <= k && i - j >= 1; j++) {
                dp[i] = Math.min(dp[i], dp[i - j] + cost[i]);
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        int[] cost = {0, 1, 2, 2, 4, 1, 2, 4, 1};  // Индекс 0 не используется
        int n = 8;
        int k = 3;
        System.out.println("Minimum cost to reach cell " + n + " with jump size up to " + k + " is: " + minCost(cost, n, k));
    }

}