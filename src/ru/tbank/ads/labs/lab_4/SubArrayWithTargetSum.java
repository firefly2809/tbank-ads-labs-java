package ru.tbank.ads.labs.lab_4;

import java.util.HashSet;
import java.util.Set;

public class SubArrayWithTargetSum {

    public boolean hasSubArraySumPositives(int[] a, int target) {
        int left = 0;
        int currentSum = 0;

        for (int right = 0; right < a.length; right++) {
            // Расширяем окно вправо
            currentSum += a[right];

            // Если сумма превысила target, сужаем окно слева
            while (currentSum > target && left <= right) {
                currentSum -= a[left];
                left++;
            }

            // Проверяем, достигли ли мы нужной суммы
            if (currentSum == target) {
                return true;
            }
        }
        return false;
    }

    public boolean hasSubArraySumAnyInts(int[] a, int target) {
        // HashMap для хранения встреченных префиксных сумм
        Set<Integer> prefixSums = new HashSet<>();

        int currentSum = 0;

        // Добавляем 0 для случая, когда подмассив начинается с начала
        prefixSums.add(0);

        for (int num : a) {
            currentSum += num;

            // Проверяем, есть ли такой префикс, который даст нужную сумму
            if (prefixSums.contains(currentSum - target)) {
                return true;
            }

            // Добавляем текущую сумму в множество
            prefixSums.add(currentSum);
        }

        return false;
    }

    public static void main(String[] args) {
        SubArrayWithTargetSum hasSubArraySum = new SubArrayWithTargetSum();


        // Тест для положительных
        int[] posArray = {3, 1, 5, 2, 4};
        System.out.println(hasSubArraySum.hasSubArraySumPositives(posArray, 8));  // true (1+5+2)
        System.out.println(hasSubArraySum.hasSubArraySumPositives(posArray, 20)); // false

        // Тест для любых чисел
        int[] mixedArray = {3, -2, 5, -1, 4};

        // 0, 3, 1, 6, 5, 9
        System.out.println(hasSubArraySum.hasSubArraySumAnyInts(mixedArray, 3));  // true (3)
        System.out.println(hasSubArraySum.hasSubArraySumAnyInts(mixedArray, 7));  // false
        System.out.println(hasSubArraySum.hasSubArraySumAnyInts(mixedArray, 6));  // true (3-2+5=6)
    }
}
