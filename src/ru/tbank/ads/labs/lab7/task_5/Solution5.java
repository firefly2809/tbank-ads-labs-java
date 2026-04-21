package ru.tbank.ads.labs.lab7.task_5;

import java.util.Arrays;

public class Solution5 {

    // Функция для поиска максимальной возрастающей подпоследовательности
    public static int lengthOfLIS(int[] nums) {
        // Если массив пуст, возвращаем 0
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // dp массив, который хранит наименьший возможный конечный элемент возрастающей подпоследовательности данной длины
        int[] dp = new int[nums.length];
        int len = 0; // длина наибольшей подпоследовательности

        for (int num : nums) {
            // Ищем место для текущего элемента в массиве dp с помощью бинарного поиска
            int i = Arrays.binarySearch(dp, 0, len, num);

            // Если элемент не найден, binarySearch возвращает индекс (-(insertion point) - 1)
            if (i < 0) {
                i = -(i + 1);
            }

            // Заменяем элемент
            dp[i] = num;

            // Если этот элемент больше всех в текущей последовательности, увеличиваем len
            if (i == len) {
                len++;
            }
        }

        return len;
    }

    public static void main(String[] args) {
        int[] nums = {1,10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("Length of LIS is " + lengthOfLIS(nums)); // Ожидаемый вывод: 4
    }
}

