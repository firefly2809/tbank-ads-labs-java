package ru.tbank.ads.labs.lab7.task_6;

public class Solution6 {

    public static int maxScrapMetal(int[] a) {
        int n = a.length;
        if (n == 0) return 0; // Нет балок, нет металлолома.
        if (n == 1) return a[0]; // Только одна балка, Петрович может её спилить.

        // Создаем и инициализируем массив динамического программирования.
        int[] dp = new int[n];
        dp[0] = a[0]; // Максимальный вес металлолома, если Петрович спиливает только первую балку.
        dp[1] = Math.max(a[0], a[1]); // Петрович может спилить либо первую, либо вторую балку.

        // Заполняем массив dp.
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] + a[i]);
        }

        // Результат хранится в последнем элементе массива dp.
        return dp[n-1];
    }

    public static void main(String[] args) {
        int[] beams = {3, 4, 5, 6, 7}; // Веса балок.
        System.out.println("Максимальный вес металлолома, который может сдать Петрович: " + maxScrapMetal(beams));
    }
}

