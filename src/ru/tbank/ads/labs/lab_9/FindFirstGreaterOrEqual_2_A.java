package ru.tbank.ads.labs.lab_9;

public class FindFirstGreaterOrEqual_2_A {

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

        /** Является ли вершина листом (отрезок длины 1) */
        boolean isLeaf() {
            return tl == tr;
        }

        /** Середина отрезка для разбиения на два потомка */
        int mid() {
            return (tl + tr) / 2;
        }
    }

    private Node root; // корень дерева отрезков

    /**
     * Конструктор: строит дерево отрезков по массиву a.
     * Сложность: O(n)
     */
    public FindFirstGreaterOrEqual_2_A(int[] a) {
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
     * Находит минимальный индекс i, для которого a[i] >= k.
     * Возвращает -1, если такого нет.
     * Сложность: O(log n)
     */
    public int query(int k) {
        return query(root, k);
    }

    private int query(Node node, int k) {
        // Отсечение: если максимум поддерева < k,
        // то ни один элемент в нём не подходит
        if (node.max < k) {
            return -1;
        }
        // Дошли до листа — он подходит (max >= k)
        if (node.isLeaf()) {
            return node.tl;
        }
        // Сначала ищем в левом поддереве (там меньшие индексы).
        // Если нашли — это и есть минимальный, возвращаем сразу.
        int result = query(node.left, k);
        if (result != -1) {
            return result;
        }
        // Слева не нашли — ищем в правом поддереве
        return query(node.right, k);
    }

    public static void main(String[] args) {
        int[] a = {3, 5, 7, 1, 9, 6, 3, 2};
        FindFirstGreaterOrEqual_2_A tree = new FindFirstGreaterOrEqual_2_A(a);

        System.out.println(tree.query(6));   // 2  (a[2]=7 >= 6)
        System.out.println(tree.query(9));   // 4  (a[4]=9 >= 9)
        System.out.println(tree.query(10));  // -1 (нет элемента >= 10)

        tree.assign(2, 1);                   // a[2] = 1
        System.out.println(tree.query(6));   // 4  (теперь a[4]=9 — первый >= 6)
    }
}