package ru.tbank.ads.labs.lab_2;

import java.util.Stack;

/*
Реализуйте очередь, используя только стеки. Реализуйте операции нахождения min/max/sum/gcd.
Амортизированное время O(1).
*/

/*
Очередь на двух стеках
Два стека: RIGHT (входящий) и LEFT (исходящий)
enqueue → кладём в RIGHT
dequeue → берём из LEFT
Если LEFT пуст → перекладываем всё из RIGHT в LEFT
Min/Max/Sum/GCD за O(1)

Каждый элемент стека хранит не только значение, но и агрегат от дна до себя
При push → считаем новый агрегат из текущего элемента + агрегат вершины
Текущий агрегат всего стека → просто смотрим вершину
Агрегат всей очереди → берём вершины обоих стеков и комбинируем
*/

/*
Худший случай одного dequeue = O(n)    ← перекладываем всё
Но это происходит ОДИН раз на n операций

Итого за n операций = O(n) + O(1) + O(1) + ... = O(n)
Делим на n операций = O(n) / n = O(1)  - это амортизированное время
 */

public class QueueOnStacks {
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

    private static class StatStack {
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

    private final StatStack rightStack = new StatStack();
    private final StatStack leftStack = new StatStack();

    public boolean isEmpty() {
        return rightStack.isEmpty() && leftStack.isEmpty();
    }

    public int size() {
        return rightStack.size() + leftStack.size();
    }

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

    public void enqueue(long value) {
        rightStack.push(value);
    }

    public long dequeue() {
        if (leftStack.isEmpty()) {
            while (!rightStack.isEmpty()) {
                leftStack.push(rightStack.pop());
            }
        }
        if (leftStack.isEmpty()) throw new RuntimeException("Очередь пуста!");
        return leftStack.pop();
    }

    public long peek() {
        if (leftStack.isEmpty()) {
            while (!rightStack.isEmpty()) {
                leftStack.push(rightStack.pop());
            }
        }
        if (leftStack.isEmpty()) throw new RuntimeException("Очередь пуста!");
        return leftStack.peek();
    }

    public static void main(String[] args) {
        QueueOnStacks queue = new QueueOnStacks();

        queue.enqueue(6);
        queue.enqueue(4);
        queue.enqueue(3);

        System.out.println("Min: " + queue.min());          // 3
        System.out.println("Max: " + queue.max());          // 6
        System.out.println("Sum: " + queue.sum());          // 13
        System.out.println("GCD: " + queue.gcd());          // 1

        System.out.println("Dequeue: " + queue.dequeue());  // 6

        queue.enqueue(12);

        System.out.println("Min: " + queue.min());          // 3
        System.out.println("Max: " + queue.max());          // 12
        System.out.println("Sum: " + queue.sum());          // 19
        System.out.println("GCD: " + queue.gcd());          // 1
    }
}
