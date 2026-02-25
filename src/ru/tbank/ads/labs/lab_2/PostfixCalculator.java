package ru.tbank.ads.labs.lab_2;

import java.util.Stack;

/*
Используя стек, научитесь вычислять выражения в постфиксной записи (это когда оператор ста-
вится после аргументов, например, выражение 4 - ((1+3) * 3)  в постфиксной записи выглядит так:
4 1 2 + 3 * -.
 */

/**
 * Читаем токены слева направо
 * Если число → кладём в стек
 * Если оператор → достаём два числа из стека, применяем оператор, результат кладём обратно
 * В конце в стеке остаётся один элемент — ответ
 */

public class PostfixCalculator {

    public static long evaluate(String expression) {
        Stack<Long> stack = new Stack<>();
        String[] tokens = expression.split(" ");

        for (String token : tokens) {
            switch (token) {
                case "+" -> {
                    long b = stack.pop();
                    long a = stack.pop();
                    stack.push(a + b);
                }
                case "-" -> {
                    long b = stack.pop();
                    long a = stack.pop();
                    stack.push(a - b);
                }
                case "*" -> {
                    long b = stack.pop();
                    long a = stack.pop();
                    stack.push(a * b);
                }
                case "/" -> {
                    long b = stack.pop();
                    long a = stack.pop();
                    stack.push(a / b);
                }
                default -> stack.push(Long.parseLong(token));
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        // 4 * (1 + 2) * 3 = 36
        System.out.println(evaluate("4 1 2 + 3 * *")); // 36

        // 2 + 3 * 4 = 14
        System.out.println(evaluate("2 3 4 * +")); // 14

        // (2 + 3) * 4 = 20
        System.out.println(evaluate("2 3 + 4 *")); // 20
    }
}