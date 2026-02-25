package ru.tbank.ads.labs.lab_2;

import java.util.ArrayList;
import java.util.Stack;

/*
Мультистек — это последовательность стеков, максимальный размер i-го стека равен 2^i. Изначально
все стеки пустые. Новый элемент добавляется в первый стек. Если он уже заполнен, то все его
элементы перекладываются во второй, если он также заполнен, то его элементы перекладываются в
третий, и т.д. Покажите, что амортизированное время добавления элемента в мультистек O(log n).
 */

/*
Уровень стека = логарифм от его размера.

log2(2^i) = i

И того, если все стеки заполнены, то при добавление n-го элемента он будет переложен log2(n) раз. Это худший случай.
И получается, что максимальная работа по добавление n-го элемента это затраты на добавление элемента помноженные на максимальное число его перекладываний log n

И амортизированное время добавления элемента в мультистек = (n*log n)/n = O(log n).

*/

public class MultiStack {

    private final ArrayList<Stack<Integer>> stacks = new ArrayList<>();

    private void ensureStack(int index) {
        while (stacks.size() <= index) {
            stacks.add(new Stack<>());
        }
    }

    private int capacity(int index) {
        return 1 << index; // 2^index
    }

    public void add(int value) {
        // Кладём новый элемент в стек 0
        ensureStack(0);
        stacks.get(0).push(value);

        // Если стек переполнен — каскадно перекладываем
        int i = 0;
        while (stacks.get(i).size() > capacity(i)) {
            ensureStack(i + 1);
            Stack<Integer> current = stacks.get(i);
            Stack<Integer> next = stacks.get(i + 1);

            // Перекладываем все элементы из i в i+1
            while (!current.isEmpty()) {
                next.push(current.pop());
            }

            i++;
        }
    }

    public void print() {
        for (int i = 0; i < stacks.size(); i++) {
            System.out.println("Стек " + i + " (макс " + capacity(i) + "): " + stacks.get(i));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MultiStack ms = new MultiStack();
        for (int i = 1; i <= 8; i++) {
            ms.add(i);
            System.out.println("После добавления " + i + ":");
            ms.print();
        }
    }
}