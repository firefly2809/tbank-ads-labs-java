package ru.tbank.ads.labs.lab_9;

public class BracketSequence_4 {

    // ======= Вершина: незакрытые открывающие и закрывающие скобки =======
    static class Node {
        int open;   // количество незакрытых '('
        int close;  // количество незакрытых ')'

        Node(int open, int close) {
            this.open = open;
            this.close = close;
        }
    }

    // Нейтральный элемент — пустая строка, не влияет на слияние
    static final Node NEUTRAL = new Node(0, 0);

    int n;
    Node[] tree;
    char[] s; // храним текущее состояние строки для удобства

    // ======= Слияние двух узлов =======
    // matched — сколько '(' из левого закрыли ')' из правого
    static Node merge(Node left, Node right) {
        int matched = Math.min(left.open, right.close);
        return new Node(
                left.open + right.open - matched,
                left.close + right.close - matched
        );
    }

    // ======= Создание листа из одного символа =======
    static Node fromChar(char c) {
        if (c == '(') return new Node(1, 0);
        else          return new Node(0, 1);
    }

    // ======= Конструктор =======
    public BracketSequence_4(String str) {
        this.n = str.length();
        this.s = str.toCharArray();
        tree = new Node[4 * n];
        build(1, 0, n - 1);
    }

    // ======= Построение =======
    private void build(int v, int tl, int tr) {
        if (tl == tr) {
            tree[v] = fromChar(s[tl]);
            return;
        }
        int tm = (tl + tr) / 2;
        build(2 * v, tl, tm);
        build(2 * v + 1, tm + 1, tr);
        tree[v] = merge(tree[2 * v], tree[2 * v + 1]);
    }

    // ======= (a) Изменить i-ю скобку =======
    // Меняет скобку на противоположную: '(' ↔ ')'
    // Сложность: O(log n)
    public void toggle(int pos) {
        s[pos] = (s[pos] == '(') ? ')' : '(';
        update(1, 0, n - 1, pos);
    }

    // Устанавливает конкретный символ
    // Сложность: O(log n)
    public void set(int pos, char c) {
        s[pos] = c;
        update(1, 0, n - 1, pos);
    }

    private void update(int v, int tl, int tr, int pos) {
        if (tl == tr) {
            tree[v] = fromChar(s[tl]);
            return;
        }
        int tm = (tl + tr) / 2;
        if (pos <= tm) update(2 * v, tl, tm, pos);
        else           update(2 * v + 1, tm + 1, tr, pos);
        tree[v] = merge(tree[2 * v], tree[2 * v + 1]);
    }

    // ======= (b) Проверка: является ли подстрока [l, r] ПСП =======
    // ПСП ↔ open == 0 && close == 0 (всё сбалансировано)
    // Сложность: O(log n)
    public boolean isValid(int l, int r) {
        Node res = query(1, 0, n - 1, l, r);
        return res.open == 0 && res.close == 0;
    }

    private Node query(int v, int tl, int tr, int l, int r) {
        if (l > r) return NEUTRAL;
        if (l == tl && r == tr) return tree[v];
        int tm = (tl + tr) / 2;
        Node left  = query(2 * v, tl, tm, l, Math.min(r, tm));
        Node right = query(2 * v + 1, tm + 1, tr, Math.max(l, tm + 1), r);
        return merge(left, right);
    }

    // ======= Тест =======
    public static void main(String[] args) {
        //                    0 1 2 3 4 5 6 7
        String str =         "(())(())";
        System.out.println("Строка: " + str);

        BracketSequence_4 bs = new BracketSequence_4(str);

        // Вся строка — ПСП
        System.out.println("isValid(0,7): " + bs.isValid(0, 7)); // true

        // Подстроки
        System.out.println("isValid(0,3): " + bs.isValid(0, 3)); // true  "(())"
        System.out.println("isValid(0,1): " + bs.isValid(0, 1)); // false "(("
        System.out.println("isValid(1,2): " + bs.isValid(1, 2)); // true  "()"
        System.out.println("isValid(1,4): " + bs.isValid(1, 4)); // false "())("
        System.out.println("isValid(4,7): " + bs.isValid(4, 7)); // true  "(())"

        // Меняем скобку: позиция 2 была ')' → станет '('
        // Было: ( ( ) ) ( ( ) )
        // Стало: ( ( ( ) ( ( ) )
        bs.toggle(2);
        System.out.println("\nПосле toggle(2): " + new String(bs.s));
        System.out.println("isValid(0,7): " + bs.isValid(0, 7)); // false
        System.out.println("isValid(2,3): " + bs.isValid(2, 3)); // true  "()"

        // Устанавливаем конкретный символ
        bs.set(2, ')');
        System.out.println("\nПосле set(2,')'): " + new String(bs.s));
        System.out.println("isValid(0,7): " + bs.isValid(0, 7)); // true
    }
}
