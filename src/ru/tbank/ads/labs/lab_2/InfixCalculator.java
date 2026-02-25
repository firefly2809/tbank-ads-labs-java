package ru.tbank.ads.labs.lab_2;

import java.util.Stack;

/*
Научитесь вычислять выражения в инфиксной записи со скобками (обычные выражения). Для про-
стоты можно считать, что в выражении проставлены все скобки (то есть внутри скобок вычисляется
только один оператор, например так можно: (4-((1+2)*3)), а так — нет: (1 + 2 + 3).
 */

/**
 Стек чисел:     numbers
 Стек операторов: operators

 Число → кладём в numbers
 Оператор → кладём в operators
 Открывающая скобка ( → игнорируем
 Закрывающая скобка ) → достаём оператор и два числа, считаем, результат в numbers

 (4-((1+2)*3))

 Читаем (    → игнорируем
 Читаем 4    → numbers: [4]
 Читаем -    → operators: [-]
 Читаем (    → игнорируем
 Читаем (    → игнорируем
 Читаем 1    → numbers: [4, 1]
 Читаем +    → operators: [-, +]
 Читаем 2    → numbers: [4, 1, 2]
 Читаем )    → достаём +, 2, 1 → считаем 1+2=3 → numbers: [4, 3]
 Читаем *    → operators: [-, *]
 Читаем 3    → numbers: [4, 3, 3]
 Читаем )    → достаём *, 3, 3 → считаем 3*3=9 → numbers: [4, 9]
 Читаем )    → достаём -, 9, 4 → считаем 4-9=-5 → numbers: [-5]

 Ответ: -5 ✓
 */

public class InfixCalculator {

    public static long evaluate(String expression) {
        Stack<Long> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        // Убираем все пробелы
        String s = expression.replaceAll(" ", "");

        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);

            // Открывающая скобка — игнорируем
            if (c == '(') {
                i++;
            }
            // Число (может быть многозначным)
            else if (Character.isDigit(c)) {
                long num = 0;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + (s.charAt(i) - '0');
                    i++;
                }
                numbers.push(num);
            }
            // Оператор
            else if (c == '+' || c == '-' || c == '*' || c == '/') {
                operators.push(c);
                i++;
            }
            // Закрывающая скобка — считаем
            else if (c == ')') {
                long b = numbers.pop();
                long a = numbers.pop();
                char op = operators.pop();
                numbers.push(apply(a, op, b));
                i++;
            }
        }

        return numbers.pop();
    }

    private static long apply(long a, char op, long b) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new RuntimeException("Неизвестный оператор: " + op);
        };
    }

    public static void main(String[] args) {
        // 4 - (1 + 2) * 3 = -5
        System.out.println(evaluate("(4-((1+2)*3))")); // -5

        // (2 + 3) * 4 = 20
        System.out.println(evaluate("((2+3)*4)")); // 20

        // 2 + 3 * 4 = 14
        System.out.println(evaluate("(2+(3*4))")); // 14
    }
}