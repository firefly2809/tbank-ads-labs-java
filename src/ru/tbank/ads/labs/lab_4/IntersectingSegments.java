package ru.tbank.ads.labs.lab_4;


import java.util.Arrays;

public class IntersectingSegments {

    public static int[] countIntersections(int[][] segments) {
        int n = segments.length;

        // Сразу создаём массивы для сортировки
        int[] sortedL = new int[n];
        int[] sortedR = new int[n];
        for (int i = 0; i < n; i++) {
            sortedL[i] = segments[i][0];
            sortedR[i] = segments[i][1];
        }
        Arrays.sort(sortedL);
        Arrays.sort(sortedR);

        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            int li = segments[i][0];  // оригинальные значения в segments
            int ri = segments[i][1];

            int cntLeft = countLessThan(sortedR, li);
            int cntRight = countGreaterThan(sortedL, ri);

            result[i] = n - 1 - cntLeft - cntRight;
        }
        return result;
    }

    // Количество элементов в отсортированном массиве, строго меньших value
    private static int countLessThan(int[] sorted, int value) {
        int lo = 0, hi = sorted.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (sorted[mid] < value) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    // Количество элементов в отсортированном массиве, строго больших value
    private static int countGreaterThan(int[] sorted, int value) {
        int lo = 0, hi = sorted.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (sorted[mid] <= value) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return sorted.length - lo;
    }

    public static void main(String[] args) {
        int[][] segments = {
                {1, 5},
                {3, 7},
                {8, 10},
                {2, 3}
        };

        int[] result = countIntersections(segments);

        for (int i = 0; i < segments.length; i++) {
            System.out.println("[" + segments[i][0] + "," + segments[i][1]
                    + "] пересекается с " + result[i] + " отрезками");
        }
    }
}
