package ru.tbank.ads.labs.lab7.task_7;

public class Solution7 {

    public static int minOperations(int n) {
        if (n == 1) return 0; // Если n уже 1, не нужно никаких операций.

        int[] dp = new int[n + 1];
        dp[1] = 0; // Начальное состояние: из 1 в 1 операций не требуется.

        for (int i = 2; i <= n; i++) {
            int minOps = dp[i - 1] + 1; // Минимальное кол-во операций, прибавив 1 к i-1.

            if (i % 2 == 0) {
                minOps = Math.min(minOps, dp[i / 2] + 1); // Операция умножения на 2.
            }

            if (i % 3 == 0) {
                minOps = Math.min(minOps, dp[i / 3] + 1); // Операция умножения на 3.
            }

            dp[i] = minOps;
        }

        return dp[n];
    }

    public static void main(String[] args) {
        int n = 10; // Число, до которого нужно дойти.
        System.out.println("Минимальное количество операций: " + minOperations(n));
    }
}

