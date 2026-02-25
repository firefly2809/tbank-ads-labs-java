package ru.tbank.ads.labs.lab_2;

/*
Реализуйте дек, используя только стеки. Реализуйте операции нахождения min/max/sum/gcd.
Амортизированное время O(1).
 */

/*
Дек — ребенок стэка и очереди - можем добавлять/забирать с обоих концов

addFirst()  → LEFT стек
addLast()   → RIGHT стек
removeFirst() ← LEFT стек (если пуст — перекладываем из RIGHT)
removeLast()  ← RIGHT стек (если пуст — перекладываем из LEFT)
 */

/*
В плане сложности тут получается похожая история как и с очередью.

Перебалансировка стоит n/2
Но происходит раз в n/2 операций
(пока не опустошим половину LEFT)

Стоимость = (n/2) / (n/2) = O(1)

Но это будет работать когда мы действительно делаем ребалансироку достаточно редко.
 */

import java.util.Stack;

public class DequeOnStacks {

    private static class StackNode {
        long value;
        long minVal;
        long maxVal;
        long sumVal;
        long gcdVal;

        StackNode(long value, long minVal, long maxVal, long sumVal, long gcdVal) {
            this.value = value;
            this.minVal = minVal;
            this.maxVal = maxVal;
            this.sumVal = sumVal;
            this.gcdVal = gcdVal;
        }
    }

    private static class AggStack {
        private final Stack<StackNode> data = new Stack<>();

        boolean isEmpty() {
            return data.isEmpty();
        }

        int size() {
            return data.size();
        }

        long min() {
            return data.peek().minVal;
        }

        long max() {
            return data.peek().maxVal;
        }

        long sum() {
            return data.peek().sumVal;
        }

        long gcd() {
            return data.peek().gcdVal;
        }

        void push(long value) {
            if (data.isEmpty()) {
                data.push(new StackNode(value, value, value, value, value));
            } else {
                StackNode top = data.peek();
                data.push(new StackNode(
                        value,
                        Math.min(value, top.minVal),
                        Math.max(value, top.maxVal),
                        top.sumVal + value,
                        evalGcd(value, top.gcdVal)
                ));
            }
        }

        long pop() {
            return data.pop().value;
        }

        long peek() {
            return data.peek().value;
        }
    }

    private static long evalGcd(long a, long b) {
        return b == 0 ? a : evalGcd(b, a % b);
    }

    private final AggStack leftStack = new AggStack();
    private final AggStack rightStack = new AggStack();

    public boolean isEmpty() {
        return leftStack.isEmpty() && rightStack.isEmpty();
    }

    public int size() {
        return leftStack.size() + rightStack.size();
    }

    // ── Агрегаты ──────────────────────────────────
    public long min() {
        if (leftStack.isEmpty()) return rightStack.min();
        if (rightStack.isEmpty()) return leftStack.min();
        return Math.min(leftStack.min(), rightStack.min());
    }

    public long max() {
        if (leftStack.isEmpty()) return rightStack.max();
        if (rightStack.isEmpty()) return leftStack.max();
        return Math.max(leftStack.max(), rightStack.max());
    }

    public long sum() {
        if (leftStack.isEmpty()) return rightStack.sum();
        if (rightStack.isEmpty()) return leftStack.sum();
        return leftStack.sum() + rightStack.sum();
    }

    public long gcd() {
        if (leftStack.isEmpty()) return rightStack.gcd();
        if (rightStack.isEmpty()) return leftStack.gcd();
        return evalGcd(leftStack.gcd(), rightStack.gcd());
    }

    // ── Операции дека ─────────────────────────────
    public void addFirst(long value) {
        leftStack.push(value);
    }

    public void addLast(long value) {
        rightStack.push(value);
    }

    public long removeFirst() {
        if (leftStack.isEmpty()) rebalance(rightStack, leftStack);
        if (leftStack.isEmpty()) throw new RuntimeException("Дек пуст!");
        return leftStack.pop();
    }

    public long removeLast() {
        if (rightStack.isEmpty()) rebalance(leftStack, rightStack);
        if (rightStack.isEmpty()) throw new RuntimeException("Дек пуст!");
        return rightStack.pop();
    }

    public long peekFirst() {
        if (leftStack.isEmpty()) rebalance(rightStack, leftStack);
        if (leftStack.isEmpty()) throw new RuntimeException("Дек пуст!");
        return leftStack.peek();
    }

    public long peekLast() {
        if (rightStack.isEmpty()) rebalance(leftStack, rightStack);
        if (rightStack.isEmpty()) throw new RuntimeException("Дек пуст!");
        return rightStack.peek();
    }

    // ── Перекладываем половину из src в dst ───────
    private void rebalance(AggStack src, AggStack dst) {
        int half = src.size() / 2;
        // Сначала снимаем верхнюю половину во временный стек
        Stack<Long> tmp = new Stack<>();
        for (int i = 0; i < half; i++) {
            tmp.push(src.pop());
        }
        // Перекладываем оставшуюся половину в dst
        while (!src.isEmpty()) {
            dst.push(src.pop());
        }
        // Возвращаем верхнюю половину обратно в src
        while (!tmp.isEmpty()) {
            src.push(tmp.pop());
        }
    }

    // ── Проверяем! ────────────────────────────────
    public static void main(String[] args) {
        DequeOnStacks deque = new DequeOnStacks();

        deque.addLast(4);
        deque.addLast(3);
        deque.addLast(6);
        // Дек: [4, 3, 6]

        System.out.println("Min: " + deque.min());              // 3
        System.out.println("Max: " + deque.max());              // 6
        System.out.println("Sum: " + deque.sum());              // 13
        System.out.println("GCD: " + deque.gcd());              // 1

        System.out.println("RemoveFirst: " + deque.removeFirst()); // 4
        System.out.println("RemoveLast: " + deque.removeLast());  // 6
        // Дек: [3]

        deque.addFirst(10);
        // Дек: [10, 3]

        System.out.println("Min: " + deque.min());              // 3
        System.out.println("Max: " + deque.max());              // 10
        System.out.println("Sum: " + deque.sum());              // 13
        System.out.println("GCD: " + deque.gcd());              // 1
    }
}
