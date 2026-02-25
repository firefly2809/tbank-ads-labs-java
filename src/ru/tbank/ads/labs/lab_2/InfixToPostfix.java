package ru.tbank.ads.labs.lab_2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
Научитесь по выражению в инфиксной записи строить выражение в постфиксной записи.
 */

/*
Стек операторов: operators
Результат:       output (список)

Число → сразу в output
Оператор → в operators
Открывающая скобка ( → игнорируем
Закрывающая скобка ) → достаём оператор из стека, кладём в output

(4-((1+2)*3))

Читаем (    → игнорируем
Читаем 4    → output: [4]
Читаем -    → operators: [-]
Читаем (    → игнорируем
Читаем (    → игнорируем
Читаем 1    → output: [4, 1]
Читаем +    → operators: [-, +]
Читаем 2    → output: [4, 1, 2]
Читаем )    → достаём + → output: [4, 1, 2, +]
Читаем *    → operators: [-, *]
Читаем 3    → output: [4, 1, 2, +, 3]
Читаем )    → достаём * → output: [4, 1, 2, +, 3, *]
Читаем )    → достаём - → output: [4, 1, 2, +, 3, *, -]

Результат: 4 1 2 + 3 * -  ✓
 */

public class InfixToPostfix {

    public static String convert(String expression) {
        Stack<Character> operators = new Stack<>();
        List<String> output = new ArrayList<>();

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
                StringBuilder num = new StringBuilder();
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    num.append(s.charAt(i));
                    i++;
                }
                output.add(num.toString());
            }
            // Оператор
            else if (c == '+' || c == '-' || c == '*' || c == '/') {
                operators.push(c);
                i++;
            }
            // Закрывающая скобка — выгружаем оператор в output
            else if (c == ')') {
                output.add(String.valueOf(operators.pop()));
                i++;
            }
        }

        return String.join(" ", output);
    }

    public static void main(String[] args) {
        // 4 - (1 + 2) * 3
        System.out.println(convert("(4-((1+2)*3))")); // 4 1 2 + 3 * -

        // (2 + 3) * 4
        System.out.println(convert("((2+3)*4)")); // 2 3 + 4 *

        // 2 + 3 * 4
        System.out.println(convert("(2+(3*4))")); // 2 3 4 * +
    }
}