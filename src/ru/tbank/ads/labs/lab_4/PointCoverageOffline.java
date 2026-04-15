package ru.tbank.ads.labs.lab_4;

import java.util.*;

public class PointCoverageOffline {

    // Типы событий с приоритетом для сортировки
    static final int START = 1;    // начало отрезка (наивысший приоритет)
    static final int QUERY = 0;    // запрос (средний приоритет)
    static final int END = -1;     // конец отрезка (низший приоритет)

    static class Event implements Comparable<Event> {
        int coord;
        int type;     // START, QUERY или END
        int queryId;  // для запросов: номер запроса (для остальных -1)

        Event(int coord, int type, int queryId) {
            this.coord = coord;
            this.type = type;
            this.queryId = queryId;
        }

        @Override
        public int compareTo(Event other) {
            if (this.coord != other.coord) {
                return Integer.compare(this.coord, other.coord);
            }
            // При равных координатах: START (1) > QUERY (0) > END (-1)
            return Integer.compare(other.type, this.type);
        }
    }

    public static int[] countCoverage(int[][] segments, int[] queries) {
        List<Event> events = new ArrayList<>();

        // 1. Добавляем события от отрезков
        for (int[] seg : segments) {
            events.add(new Event(seg[0], START, -1));  // начало
            events.add(new Event(seg[1], END, -1));    // конец
        }

        // 2. Добавляем события-запросы
        for (int i = 0; i < queries.length; i++) {
            events.add(new Event(queries[i], QUERY, i));
        }

        // 3. Сортируем все события
        Collections.sort(events);

        // 4. Проходим по событиям и отвечаем на запросы
        int[] answers = new int[queries.length];
        int activeSegments = 0;

        System.out.println("Обработка событий:");
        for (Event e : events) {
            String typeStr = e.type == START ? "НАЧАЛО" :
                    (e.type == END ? "КОНЕЦ" : "ЗАПРОС");

            System.out.printf("  coord=%d, %s", e.coord, typeStr);

            if (e.type == START) {
                activeSegments++;
                System.out.printf(" → active=%d\n", activeSegments);
            }
            else if (e.type == END) {
                activeSegments--;
                System.out.printf(" → active=%d\n", activeSegments);
            }
            else { // QUERY
                answers[e.queryId] = activeSegments;
                System.out.printf(" → ответ для точки %d = %d\n",
                        queries[e.queryId], activeSegments);
            }
        }

        return answers;
    }

    public static void main(String[] args) {
        // Отрезки
        int[][] segments = {
                {1, 4},
                {2, 5},
                {3, 7},
                {6, 9}
        };

        // Запросы
        int[] queries = {2, 4, 6, 8, 10};

        System.out.println("Отрезки:");
        for (int[] s : segments) {
            System.out.println("  [" + s[0] + ", " + s[1] + "]");
        }
        System.out.println("Запросы: " + Arrays.toString(queries));
        System.out.println();

        int[] result = countCoverage(segments, queries);

        System.out.println("\nРезультаты:");
        for (int i = 0; i < queries.length; i++) {
            System.out.println("  Точка " + queries[i] + " → " + result[i] + " отрезков");
        }
    }
}