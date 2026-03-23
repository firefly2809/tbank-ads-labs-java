package ru.tbank.ads.labs.lab_5.task1;

import java.util.*;

public class Task1 {

    private static final long BASE = 256;
    private static final long MOD = 1000000007;

    // Класс для хранения предвычисленных хэшей
    static class HashPreprocessor {
        private long[] hashes;
        private long[] powers;
        private String text;

        public HashPreprocessor(String text) {
            this.text = text;
            preprocess();
        }

        // Предвычисление хэшей для всех префиксов строки
        private void preprocess() {
            int n = text.length();
            hashes = new long[n + 1];
            powers = new long[n + 1];

            // Вычисляем степени BASE
            powers[0] = 1;
            for (int i = 1; i <= n; i++) {
                powers[i] = (powers[i - 1] * BASE) % MOD;
            }

            // Вычисляем хэши префиксов
            hashes[0] = 0;
            for (int i = 0; i < n; i++) {
                hashes[i + 1] = (hashes[i] * BASE + text.charAt(i)) % MOD;
            }
        }

        // Получение хэша подстроки [l, r] за O(1)
        public long getHash(int l, int r) {
            // Хэш подстроки [l, r] = хэш префикса [0, r] - хэш префикса [0, l-1] * BASE^(r-l+1)
            long result = (hashes[r + 1] - (hashes[l] * powers[r - l + 1]) % MOD + MOD) % MOD;
            return result;
        }
    }

    /**
     * Поиск всех вхождений подстроки S в строке T с использованием предвычисленных хэшей
     * @param T - основная строка
     * @param S - искомая подстрока
     * @return список позиций всех вхождений S в T
     */
    public static List<Integer> findAllOccurrences(String T, String S) {
        List<Integer> result = new ArrayList<>();

        if (S.isEmpty() || T.length() < S.length()) {
            return result;
        }

        // Предвычисление хэшей для строк T и S
        HashPreprocessor tProcessor = new HashPreprocessor(T);
        HashPreprocessor sProcessor = new HashPreprocessor(S);

        // Хэш искомой подстроки S
        long patternHash = sProcessor.getHash(0, S.length() - 1);

        int tLen = T.length();
        int sLen = S.length();

        // Проверяем каждую возможную позицию в строке T
        for (int i = 0; i <= tLen - sLen; i++) {
            // Получаем хэш подстроки T[i..i+sLen-1] за O(1)
            long substringHash = tProcessor.getHash(i, i + sLen - 1);

            // При совпадении хэшей строки равны (согласно условию о отсутствии коллизий)
            if (substringHash == patternHash) {
                result.add(i);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        String T = "ababcababa";
        String S = "aba";

        System.out.println("Поиск всех вхождений подстроки '" + S + "' в строке '" + T + "'");
        List<Integer> occurrences = findAllOccurrences(T, S);
        System.out.println("Позиции вхождений: " + occurrences);

        // Проверка результатов
        System.out.println("\nПроверка:");
        for (int pos : occurrences) {
            System.out.println("Позиция " + pos + ": \"" +
                    T.substring(pos, pos + S.length()) + "\"");
        }
    }
}