package ru.tbank.ads.labs.lab7.task_4;

public class Solution4 {

    public static int countPaths(int[][] a, int k) {
        int n = a.length - 1;  // из (0,0) в (n,m)
        int m = a[0].length - 1;

        // dp[i][j][s] = количество путей в (i,j) с "обрезанной" суммой s
        // s — это min(реальная_сумма, k)
        int[][][] dp = new int[n + 1][m + 1][k + 1];

        // Начальная клетка
        int startVal = a[0][0];
        int initialSum = Math.min(startVal, k);
        dp[0][0][initialSum] = 1;

        // Проходим по всем клеткам
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0 && j == 0) continue; // уже обработали

                int val = a[i][j];

                for (int s = 0; s <= k; s++) {
                    long ways = 0; // чтобы избежать переполнения при сложении

                    // пришли сверху: (i, j-1)
                    if (j > 0) {
                        ways += dp[i][j-1][s];
                    }

                    // пришли слева: (i-1, j)
                    if (i > 0) {
                        ways += dp[i-1][j][s];
                    }

                    if (ways == 0) continue;

                    // Новая сумма после добавления текущего значения
                    int newSum = s + val;
                    int newS = Math.min(newSum, k);

                    dp[i][j][newS] += ways;
                }
            }
        }

        // Ответ: количество путей с суммой >= k, то есть "обрезанной" суммой == k
        return dp[n][m][k];
    }

    // Пример использования
    public static void main(String[] args) {
        int[][] grid = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int k = 49;

        int result = countPaths(grid, k);
        System.out.println("Количество путей с суммой >= " + k + ": " + result);
    }
}

