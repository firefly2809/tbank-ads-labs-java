package ru.tbank.ads.labs.lab7.task_3;

public class Solution3_NM {
    public static void main(String[] args) {
        int[][] grid = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int n = grid.length;
        int m = grid[0].length;

        System.out.println(maxOddFlowers(n, m, grid)); // Вывод максимального нечетного числа цветочков
    }

    public static int maxOddFlowers(int n, int m, int[][] grid) {
        int[][] dpEven = new int[n][m];
        int[][] dpOdd = new int[n][m];

        // Инициализация начальной клетки
        if (grid[0][0] % 2 == 0) {
            dpEven[0][0] = grid[0][0];
            dpOdd[0][0] = Integer.MIN_VALUE; // минимальное значение, означающее невозможность
        } else {
            dpOdd[0][0] = grid[0][0];
            dpEven[0][0] = Integer.MIN_VALUE;
        }

        // Заполнение массивов dpEven и dpOdd
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 && j == 0) continue;

                int current = grid[i][j];
                int fromLeftEven = j > 0 ? dpEven[i][j - 1] : Integer.MIN_VALUE;
                int fromLeftOdd = j > 0 ? dpOdd[i][j - 1] : Integer.MIN_VALUE;
                int fromTopEven = i > 0 ? dpEven[i - 1][j] : Integer.MIN_VALUE;
                int fromTopOdd = i > 0 ? dpOdd[i - 1][j] : Integer.MIN_VALUE;

                if (current % 2 == 0) {
                    dpEven[i][j] = max(fromLeftEven, fromTopEven, current);
                    dpOdd[i][j] = max(fromLeftOdd, fromTopOdd, current);
                } else {
                    dpEven[i][j] = max(fromLeftOdd, fromTopOdd, current);
                    dpOdd[i][j] = max(fromLeftEven, fromTopEven, current);
                }
            }
        }

        // Максимальное нечетное число цветочков
        int result = dpOdd[n - 1][m - 1];
        return result == Integer.MIN_VALUE ? 0 : result;
    }

    private static int max(int a, int b, int add) {
        if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) return Integer.MIN_VALUE;
        return Math.max(a == Integer.MIN_VALUE ? Integer.MIN_VALUE : a + add,
                b == Integer.MIN_VALUE ? Integer.MIN_VALUE : b + add);
    }
}

