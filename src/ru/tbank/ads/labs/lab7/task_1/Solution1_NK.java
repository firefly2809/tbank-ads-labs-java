package ru.tbank.ads.labs.lab7.task_1;

public class Solution1_NK {

    // Функция для подсчета количества путей
    public static long countWays(int n, int k) {
        // Базовые проверки
        if (n == 1) return 1;

        // Создаем массив для хранения количества путей до каждой клетки
        long[] ways = new long[n + 1];
        ways[1] = 1;  // Изначально кузнечик находится на клетке 1

        // Вычисляем количество путей для каждой клетки от 2 до n
        for (int i = 2; i <= n; i++) {
            // Суммируем пути из предыдущих клеток, откуда можно допрыгнуть
            for (int j = 1; j <= k && i - j > 0; j++) {
                ways[i] += ways[i - j];
            }
        }

        // Возвращаем количество путей для клетки n
        return ways[n];
    }

    public static void main(String[] args) {
        int n = 11;  // Номер последней клетки
        int k = 4;   // Максимальная длина прыжка

        // Вызов функции и вывод результата
        System.out.println("Number of ways to jump from cell 1 to cell " + n + ": " + countWays(n, k));
    }
}

// 1 2 3 4 5
// 1 1 2 3 5

//1 2 3 4 5
//2 3 4 5
//2 3 5
//3 5
//3 4 5


