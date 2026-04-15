package ru.tbank.ads.labs.lab_4;

public class RangeSumQuery3D {
    private long[][][] prefix;

    // Построение: O(n * m * p)
    public RangeSumQuery3D(int[][][] a) {
        int ni = a.length;
        int nj = a[0].length;
        int nk = a[0][0].length;

        // +1 по каждому измерению — нулевая рамка
        prefix = new long[ni + 1][nj + 1][nk + 1];

        for (int i = 1; i <= ni; i++) {
            for (int j = 1; j <= nj; j++) {
                for (int k = 1; k <= nk; k++) {
                    prefix[i][j][k] = a[i - 1][j - 1][k - 1]
                            + prefix[i - 1][j][k]
                            + prefix[i][j - 1][k]
                            + prefix[i][j][k - 1]
                            - prefix[i - 1][j - 1][k]
                            - prefix[i - 1][j][k - 1]
                            - prefix[i][j - 1][k - 1]
                            + prefix[i - 1][j - 1][k - 1];
                }
            }
        }
    }

    // Запрос суммы в параллелепипеде [(x1,y1,z1)..(x2,y2,z2)]: O(1)
    public long query(int x1, int y1, int z1,
                      int x2, int y2, int z2) {
        return +prefix[x2 + 1][y2 + 1][z2 + 1]
                - prefix[x1][y2 + 1][z2 + 1]
                - prefix[x2 + 1][y1][z2 + 1]
                - prefix[x2 + 1][y2 + 1][z1]
                + prefix[x1][y1][z2 + 1]
                + prefix[x1][y2 + 1][z1]
                + prefix[x2 + 1][y1][z1]
                - prefix[x1][y1][z1];
    }

    public static void main(String[] args) {
        // Массив 2x2x2
        // Лист 0:        Лист 1:
        //   1  2           5  6
        //   3  4           7  8
        int[][][] a = {
                {{1, 5}, {2, 6}},   // ВНИМАНИЕ: a[i][j][k]
                {{3, 7}, {4, 8}}    // i=строка, j=столбец, k=лист
        };

        // Поясню индексацию:
        // a[0][0][0]=1  a[0][0][1]=5
        // a[0][1][0]=2  a[0][1][1]=6
        // a[1][0][0]=3  a[1][0][1]=7
        // a[1][1][0]=4  a[1][1][1]=8

        RangeSumQuery3D rsq = new RangeSumQuery3D(a);

        // Сумма всего: 1+2+3+4+5+6+7+8 = 36
        System.out.println(rsq.query(0, 0, 0, 1, 1, 1)); // 36

        // Только лист 0: 1+2+3+4 = 10
        System.out.println(rsq.query(0, 0, 0, 1, 1, 0)); // 10

        // Только лист 1: 5+6+7+8 = 26
        System.out.println(rsq.query(0, 0, 1, 1, 1, 1)); // 26

        // Одна ячейка a[0][0][0] = 1
        System.out.println(rsq.query(0, 0, 0, 0, 0, 0)); // 1

        // Одна ячейка a[1][1][1] = 8
        System.out.println(rsq.query(1, 1, 1, 1, 1, 1)); // 8

        // Верхняя строка обоих листов: a[0][0..1][0..1] = 1+2+5+6 = 14
        System.out.println(rsq.query(0, 0, 0, 0, 1, 1)); // 14
    }
}