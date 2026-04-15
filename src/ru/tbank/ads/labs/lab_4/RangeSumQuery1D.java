package ru.tbank.ads.labs.lab_4;

public class RangeSumQuery1D {
    private long[] prefix;

    // Построение: O(n)
    public RangeSumQuery1D(int[] a) {
        int n = a.length;
        prefix = new long[n + 1];
        prefix[0] = 0;
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + a[i];
        }
    }

    // Запрос суммы на [l..r]: O(1)
    public long query(int l, int r) {
        return prefix[r + 1] - prefix[l];
    }

    public static void main(String[] args) {
        int[] a = {3, 1, 4, 1, 5};
        RangeSumQuery1D rsq = new RangeSumQuery1D(a);
        System.out.println(rsq.query(1, 3)); // 6
        System.out.println(rsq.query(0, 4)); // 14
    }
}
