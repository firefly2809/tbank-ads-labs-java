package ru.tbank.ads.labs.lab_5.task6;

public class Task6 {
    private static final long BASE = 256;
    private static final long MOD = 1000000007;

    private long[] hashes;
    private long[] powers;
    private String s;

    public Task6(String s) {
        this.s = s;
        preprocess();
    }

    private void preprocess() {
        int n = s.length();
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
            hashes[i + 1] = (hashes[i] * BASE + s.charAt(i)) % MOD;
        }
    }

    // Получение хэша подстроки S[l..r] за O(1)
    private long getHash(int l, int r) {
        long result = (hashes[r + 1] - (hashes[l] * powers[r - l + 1]) % MOD + MOD) % MOD;
        return result;
    }

    // Нахождение длины LCP двух подстрок за O(log |S|)
    public int findLCP(int l1, int r1, int l2, int r2) {
        int len1 = r1 - l1 + 1;
        int len2 = r2 - l2 + 1;
        int maxLen = Math.min(len1, len2);

        // Бинарный поиск по длине LCP
        int left = 0;
        int right = maxLen;
        int result = 0;

        while (left <= right) {
            int mid = (left + right) / 2;
            long hash1 = getHash(l1, l1 + mid - 1);
            long hash2 = getHash(l2, l2 + mid - 1);

            if (hash1 == hash2) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        String s = "abcdefg";
        Task6 lcp = new Task6(s);

        // Пример из условия: LCP("abcdefg"[0..6], "abcefg"[0..5])
        // Подстроки: "abcdefg" и "abcefg"
        int lcpLength = lcp.findLCP(0, 6, 0, 5);
        System.out.println("LCP(\"abcdefg\", \"abcdef\") = " + lcpLength); // Должно быть 3 ("abc")

        // Проверим префикс явно
        String substr1 = s.substring(0, lcpLength);
        String substr2 = "abcdef".substring(0, lcpLength);
        System.out.println("Общий префикс: \"" + substr1 + "\"");
    }
}