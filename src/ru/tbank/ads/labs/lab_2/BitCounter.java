package ru.tbank.ads.labs.lab_2;

/*
Битовый счетчик хранит число в виде массива двоичных цифр. Изначально все цифры равны 0.
Операция inc увеличивает счетчик на 1.Реализуйте операцию inc и докажите,что амортизированное
время ее работы O(1).
 */

/*
Как у нас получается амортизированное время работы O(1).

Бит 0 (последний) → меняется каждую операцию     → N раз
Бит 1             → меняется каждые 2 операции    → N/2 раз
Бит 2             → меняется каждые 4 операции    → N/4 раз
Бит 3             → меняется каждые 8 операций    → N/8 раз


Суммируем N + N/2 + N/4 + N/8 + ... = 2N бесконечно стремимся к 2N

Амортизированное время работы = O(2N)/N = O(2) = O(1)
 */

public class BitCounter {


    private final int[] bits;

    public BitCounter(int size) {
        this.bits = new int[size];
    }

    public void inc() {
        int i = bits.length - 1;
        // Пока единицы — сбрасываем в 0
        while (i >= 0 && bits[i] == 1) {
            bits[i] = 0;
            i--;
        }
        // Первый ноль — ставим 1
        if (i >= 0) {
            bits[i] = 1;
        }
    }

    public void print() {
        for (int bit : bits) System.out.print(bit);
        System.out.println();
    }

    public static void main(String[] args) {
        BitCounter counter = new BitCounter(4);
        for (int i = 0; i < 18; i++) {
            counter.print();
            counter.inc();
        }
    }
}