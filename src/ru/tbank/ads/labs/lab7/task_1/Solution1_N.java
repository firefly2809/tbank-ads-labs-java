package ru.tbank.ads.labs.lab7.task_1;

public class Solution1_N {

    // Функция для подсчета количества путей с оптимизацией до O(n)
    public static long countWays(int n, int k) {
        if (n == 1) return 1;

        long[] ways = new long[n + 1];
        ways[1] = 1;

        long windowSum = ways[1];

        for (int i = 2; i <= n; i++) {
            ways[i] = windowSum;

            // Обновляем сумму окна для следующей итерации
            windowSum += ways[i];
            if(i - k > 0) {
                // Добавляем текущее значение и убираем значение, которое выходит из окна
                windowSum -= ways[i - k];
            }
        }

        return ways[n];
    }

    public static void main(String[] args) {
        int n = 11;  // Номер последней клетки
        int k = 4;   // Максимальная длина прыжка

        // Вызов функции и вывод результата
        System.out.println("Number of ways to jump from cell 1 to cell " + n + ": " + countWays(n, k));
    }
}


