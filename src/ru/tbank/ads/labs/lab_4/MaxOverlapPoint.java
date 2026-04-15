package ru.tbank.ads.labs.lab_4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxOverlapPoint {

    static class Event implements Comparable<Event> {
        int coord;
        int type; // +1 начало, -1 конец

        Event(int coord, int type) {
            this.coord = coord;
            this.type = type;
        }

        @Override
        public int compareTo(Event other) {
            if (this.coord != other.coord) {
                return Integer.compare(this.coord, other.coord);
            }
            // Для закрытых отрезков: сначала начала (+1), потом концы (-1)
            return Integer.compare(other.type, this.type); // +1 идет перед -1
        }
    }

    public static double findMaxOverlapPoint(int[][] segments) {
        List<Event> events = new ArrayList<>();

        // Создаем события для всех отрезков
        for (int[] seg : segments) {
            events.add(new Event(seg[0], 1));  // начало
            events.add(new Event(seg[1], -1)); // конец
        }

        // Сортируем события
        Collections.sort(events);

        int currentOverlap = 0;
        int maxOverlap = 0;
        double bestPoint = 0;

        // Проходим по всем событиям
        for (Event e : events) {
            currentOverlap += e.type;

            if (currentOverlap > maxOverlap) {
                maxOverlap = currentOverlap;
                bestPoint = e.coord;
            }
        }

        System.out.println("Максимальное количество пересечений: " + maxOverlap);
        return bestPoint;
    }

    public static void main(String[] args) {
        int[][] segments = {
                {1, 4},
                {2, 6},
                {3, 5},
                {7, 9},
                {2, 3}
        };

        double point = findMaxOverlapPoint(segments);
        System.out.println("Точка максимального покрытия: " + point);
    }
}
