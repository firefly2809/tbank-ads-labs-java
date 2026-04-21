package ru.tbank.ads.labs.lab7.task_8;

public class Solution8 {

    public static void main(String[] args) {
        int[] a = {2, 3, 1}; // Время на покупку одного билета каждым школьником.
        int[] b = {5, 6};   // Время на покупку двух билетов для школьника i и i-1.

        // Массив для хранения минимального времени
        int[] dp = new int[a.length + 1];

        // Инициализируем начальные условия
        dp[0] = 0;
        dp[1] = a[0];

        // Проходим по всем школьникам и вычисляем минимальное время
        for (int i = 2; i <= a.length; i++) {
            dp[i] = Math.min(dp[i - 1] + a[i - 1], dp[i - 2] + b[i - 2]);
        }

        // Минимальное время для всех n школьников
        System.out.println(dp[a.length]);
    }
}

