package ru.tbank.ads.labs.lab_5.task2;

public class Task2 {
    private static final long BASE = 256;
    private static final long MOD = 1000000007;

    private long[] forwardHashes;
    private long[] backwardHashes;
    private long[] powers;
    private String s;

    public Task2(String s) {
        this.s = s;
        preprocess();
    }

    private void preprocess() {
        int n = s.length();
        forwardHashes = new long[n + 1];
        backwardHashes = new long[n + 1];
        powers = new long[n + 1];

        // Вычисляем степени BASE
        powers[0] = 1;
        for (int i = 1; i <= n; i++) {
            powers[i] = (powers[i - 1] * BASE) % MOD;
        }

        // Вычисляем хэши префиксов прямой строки
        forwardHashes[0] = 0;
        for (int i = 0; i < n; i++) {
            forwardHashes[i + 1] = (forwardHashes[i] * BASE + s.charAt(i)) % MOD;
        }

        // Вычисляем хэши префиксов обратной строки
        backwardHashes[0] = 0;
        for (int i = 0; i < n; i++) {
            backwardHashes[i + 1] = (backwardHashes[i] * BASE + s.charAt(n - 1 - i)) % MOD;
        }
    }

    // Получение хэша подстроки S[l..r] за O(1)
    private long getForwardHash(int l, int r) {
        long result = (forwardHashes[r + 1] - (forwardHashes[l] * powers[r - l + 1]) % MOD + MOD) % MOD;
        return result;
    }

    // Получение хэша подстроки S[l..r], прочитанной в обратном порядке, за O(1)
    private long getBackwardHash(int l, int r) {
        int n = s.length();
        // В обратной строке индексы меняются местами
        int revL = n - 1 - r;
        int revR = n - 1 - l;
        long result = (backwardHashes[revR + 1] - (backwardHashes[revL] * powers[r - l + 1]) % MOD + MOD) % MOD;
        return result;
    }

    // Проверка, является ли подстрока S[l..r] палиндромом за O(1)
    public boolean isPalindrome(int l, int r) {
        return getForwardHash(l, r) == getBackwardHash(l, r);
    }

    public static void main(String[] args) {
        String s = "abacabad";
        Task2 checker = new Task2(s);

        // Тестовые запросы
        System.out.println("Строка: " + s);
        System.out.println("Подстрока [0,2] \"aba\": " + checker.isPalindrome(0, 2)); // true
        System.out.println("Подстрока [1,3] \"bac\": " + checker.isPalindrome(1, 3)); // false
        System.out.println("Подстрока [4,6] \"aba\": " + checker.isPalindrome(4, 6)); // true
        System.out.println("Подстрока [0,7] \"abacabad\": " + checker.isPalindrome(0, 7)); // false
    }
}