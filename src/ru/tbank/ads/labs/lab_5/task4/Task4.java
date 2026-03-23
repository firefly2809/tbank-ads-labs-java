package ru.tbank.ads.labs.lab_5.task4;

public class Task4 {
    private static final long BASE = 256;
    private static final long MOD = 1000000007;

    private long[] hashes;
    private long[] powers;
    private String s;

    public Task4(String s) {
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

    // Сравнение двух подстрок лексикографически за O(log |S|)
    public boolean isGreater(int l1, int r1, int l2, int r2) {
        int len1 = r1 - l1 + 1;
        int len2 = r2 - l2 + 1;

        // Бинарный поиск для нахождения LCP
        int left = 0;
        int right = Math.min(len1, len2);
        int lcp = 0;

        while (left <= right) {
            int mid = (left + right) / 2;
            long hash1 = getHash(l1, l1 + mid - 1);
            long hash2 = getHash(l2, l2 + mid - 1);

            if (hash1 == hash2) {
                lcp = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // Если одна строка является префиксом другой
        if (lcp == Math.min(len1, len2)) {
            return len1 > len2;
        }

        // Сравниваем символы после LCP
        return s.charAt(l1 + lcp) > s.charAt(l2 + lcp);
    }

    public static void main(String[] args) {
        String s = "abcxyzdef";
        Task4 comparator = new Task4(s);

        // Сравниваем подстроки "abc" [0,2] и "abx" [0,2]
        System.out.println("\"abc\" > \"abx\": " + comparator.isGreater(0, 2, 0, 2)); // false

        // Сравниваем подстроки "xyz" [3,5] и "def" [6,8]
        System.out.println("\"xyz\" > \"def\": " + comparator.isGreater(3, 5, 6, 8)); // true
    }
}