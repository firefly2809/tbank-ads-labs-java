package ru.tbank.ads.labs.lab_5.task8;
import java.util.*;

public class Task8 {
    private static final long BASE = 256;
    private static final long MOD = 1000000007;

    // Хэш-процессор для строки
    static class HashProcessor {
        private long[] hashes;
        private long[] powers;
        private String text;

        public HashProcessor(String text) {
            this.text = text;
            preprocess();
        }

        private void preprocess() {
            int n = text.length();
            hashes = new long[n + 1];
            powers = new long[n + 1];

            powers[0] = 1;
            for (int i = 1; i <= n; i++) {
                powers[i] = (powers[i - 1] * BASE) % MOD;
            }

            hashes[0] = 0;
            for (int i = 0; i < n; i++) {
                hashes[i + 1] = (hashes[i] * BASE + text.charAt(i)) % MOD;
            }
        }

        public long getHash(int l, int r) {
            long result = (hashes[r + 1] - (hashes[l] * powers[r - l + 1]) % MOD + MOD) % MOD;
            return result;
        }
    }

    // Проверка существования общей подстроки длины len
    private static boolean hasCommonSubstring(String s1, String s2, int len,
                                              HashProcessor h1, HashProcessor h2) {
        if (len == 0) return true;

        Set<Long> hashes1 = new HashSet<>();

        // Добавляем все хэши подстрок длины len из первой строки
        for (int i = 0; i <= s1.length() - len; i++) {
            hashes1.add(h1.getHash(i, i + len - 1));
        }

        // Проверяем, есть ли такие же хэши во второй строке
        for (int i = 0; i <= s2.length() - len; i++) {
            long hash = h2.getHash(i, i + len - 1);
            if (hashes1.contains(hash)) {
                return true;
            }
        }

        return false;
    }

    // Нахождение наибольшей общей подстроки за O(n log n)
    public static String findLongestCommonSubstring(String s1, String s2) {
        if (s1.isEmpty() || s2.isEmpty()) return "";

        HashProcessor h1 = new HashProcessor(s1);
        HashProcessor h2 = new HashProcessor(s2);

        int left = 0;
        int right = Math.min(s1.length(), s2.length());
        int resultLen = 0;
        int resultStart = 0;

        // Бинарный поиск по длине ответа
        while (left <= right) {
            int mid = (left + right) / 2;

            if (hasCommonSubstring(s1, s2, mid, h1, h2)) {
                resultLen = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // Если общая подстрока существует, находим её
        if (resultLen == 0) return "";

        Set<Long> hashes1 = new HashSet<>();
        Map<Long, Integer> hashToIndex = new HashMap<>();

        // Заполняем хэши первой строки
        for (int i = 0; i <= s1.length() - resultLen; i++) {
            long hash = h1.getHash(i, i + resultLen - 1);
            hashes1.add(hash);
            hashToIndex.put(hash, i);
        }

        // Ищем совпадающий хэш во второй строке
        for (int i = 0; i <= s2.length() - resultLen; i++) {
            long hash = h2.getHash(i, i + resultLen - 1);
            if (hashes1.contains(hash)) {
                resultStart = hashToIndex.get(hash);
                return s1.substring(resultStart, resultStart + resultLen);
            }
        }

        return "";
    }

    public static void main(String[] args) {
        String s1 = "abcdef";
        String s2 = "zabcxy";

        String result = findLongestCommonSubstring(s1, s2);
        System.out.println("Наибольшая общая подстрока \"" + s1 + "\" и \"" + s2 + "\": \"" + result + "\"");
        System.out.println("Длина: " + result.length());
    }
}