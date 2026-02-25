package ru.tbank.ads.labs.lab_2;

import java.util.Stack;
import java.util.Arrays;

/*
Был массив чисел, все соседние числа были различны. С этим массивом несколько раз продела-
ли операцию: вставить в массив два одинаковых числа рядом. По конечному состоянию массива
восстановите его исходное состояние. Время O(n).
*/

/**
 * Если в массиве есть два одинаковых соседних числа — это результат операции вставки. Просто удаляем их!
 *
 * Используем стек :
 * Берём число из массива
 * Если вершина стека равна текущему числу → убираем вершину (нашли пару!)
 * Иначе → кладём в стек
 *
 * Пример: [1, 3, 3, 2, 2, 3, 1]
 * Читаем 1 → стек: [1]
 * Читаем 3 → стек: [1, 3]
 * Читаем 3 → вершина 3 == 3 → убираем! стек: [1]
 * Читаем 2 → стек: [1, 2]
 * Читаем 2 → вершина 2 == 2 → убираем! стек: [1]
 * Читаем 3 → стек: [1, 3]
 * Читаем 1 → стек: [1, 3, 1]
 * Ответ: [1, 3, 1] ✓
 */

public class RestoreArray {

    public static int[] restore(int[] array) {
        Stack<Integer> stack = new Stack<>();

        for (int num : array) {
            if (!stack.isEmpty() && stack.peek() == num) {
                // Нашли пару одинаковых соседей — удаляем
                stack.pop();
            } else {
                stack.push(num);
            }
        }

        // Собираем результат из стека
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(restore(new int[]{1, 2, 2, 3, 3, 1})));     // []
        System.out.println(Arrays.toString(restore(new int[]{1, 3, 3, 2, 2, 3, 1}))); // [1, 3, 1]
        System.out.println(Arrays.toString(restore(new int[]{1, 2, 3, 3, 2, 1})));    // []
    }
}