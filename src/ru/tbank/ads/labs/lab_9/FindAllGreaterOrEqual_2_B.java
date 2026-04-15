package ru.tbank.ads.labs.lab_9;

import java.util.ArrayList;
import java.util.List;

public class FindAllGreaterOrEqual_2_B {

    /**
     * Вершина дерева отрезков.
     * Хранит максимум на своём отрезке [tl, tr]
     * и ссылки на левого и правого потомка.
     */
    static class Node {
        int max;          // максимум на отрезке [tl, tr]
        int tl, tr;       // левая и правая границы отрезка
        Node left, right; // потомки

        Node(int tl, int tr) {
            this.tl = tl;
            this.tr = tr;
        }

        /**
         * Является ли вершина листом (отрезок длины 1)
         */
        boolean isLeaf() {
            return tl == tr;
        }

        /**
         * Середина отрезка для разбиения на два потомка
         */
        int mid() {
            return (tl + tr) / 2;
        }
    }

    private Node root; // корень дерева отрезков

    /**
     * Конструктор: строит дерево отрезков по массиву a.
     * Сложность: O(n)
     */
    public FindAllGreaterOrEqual_2_B(int[] a) {
        root = build(a, 0, a.length - 1);
    }

    /**
     * Рекурсивное построение дерева.
     * Для листа: max = a[tl].
     * Для внутренней вершины: max = max(left.max, right.max).
     */
    private Node build(int[] a, int tl, int tr) {
        Node node = new Node(tl, tr);
        if (tl == tr) {
            // Лист — хранит значение одного элемента
            node.max = a[tl];
            return node;
        }
        int tm = (tl + tr) / 2;
        node.left = build(a, tl, tm);        // строим левое поддерево
        node.right = build(a, tm + 1, tr);   // строим правое поддерево
        node.max = Math.max(node.left.max, node.right.max); // пересчёт максимума
        return node;
    }

    /**
     * Присвоение: a[pos] = val.
     * Сложность: O(log n)
     */
    public void assign(int pos, int val) {
        assign(root, pos, val);
    }

    private void assign(Node node, int pos, int val) {
        if (node.isLeaf()) {
            // Дошли до нужного листа — обновляем значение
            node.max = val;
            return;
        }
        // Определяем, в какое поддерево идти
        if (pos <= node.mid()) {
            assign(node.left, pos, val);   // pos в левой половине
        } else {
            assign(node.right, pos, val);  // pos в правой половине
        }
        // Пересчитываем максимум после обновления потомка
        node.max = Math.max(node.left.max, node.right.max);
    }

    /**
     * Находит ВСЕ индексы i, для которых a[i] >= k.
     * Результат отсортирован по возрастанию (слева направо).
     * Сложность: O(x * log n), где x — размер ответа.
     */
    public List<Integer> query(int k) {
        List<Integer> result = new ArrayList<>();
        query(root, k, result);
        return result;
    }

    private void query(Node node, int k, List<Integer> result) {
        // Отсечение: если максимум поддерева < k,
        // то ни один элемент в нём не подходит
        if (node == null || node.max < k) {
            return;
        }
        // Дошли до листа — он подходит, добавляем в ответ
        if (node.isLeaf()) {
            result.add(node.tl);
            return;
        }
        // ОТЛИЧИЕ от задачи (a):
        // Идём в ОБА поддерева, не останавливаемся после левого.
        // Сначала левое — индексы добавятся в порядке возрастания.
        query(node.left, k, result);
        query(node.right, k, result);
    }

    public static void main(String[] args) {
        int[] a = {3, 5, 7, 1, 9, 6, 3, 2};
        FindAllGreaterOrEqual_2_B tree = new FindAllGreaterOrEqual_2_B(a);

        System.out.println(tree.query(6));   // [2, 4, 5]
        System.out.println(tree.query(5));   // [1, 2, 4, 5]
        System.out.println(tree.query(10));  // []

        tree.assign(0, 15);                  // a[0] = 15
        System.out.println(tree.query(6));   // [0, 2, 4, 5]
    }
}