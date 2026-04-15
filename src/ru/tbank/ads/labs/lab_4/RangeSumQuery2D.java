package ru.tbank.ads.labs.lab_4;

public class RangeSumQuery2D {
    private final long[][] prefix;

    // Построение: O(n*m)
    public RangeSumQuery2D(int[][] a) {
        int n = a.length;
        int m = a[0].length;
        prefix = new long[n + 1][m + 1];

        // prefix[0][..] и prefix[..][0] уже нули (по умолчанию)

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                prefix[i][j] = a[i - 1][j - 1]
                        + prefix[i - 1][j]      // сверху
                        + prefix[i][j - 1]       // слева
                        - prefix[i - 1][j - 1];  // убираем двойной подсчёт
            }
        }
    }

    // Запрос суммы в прямоугольнике [(x1,y1)..(x2,y2)]: O(1)
    public long query(int x1, int y1, int x2, int y2) {
        return prefix[x2 + 1][y2 + 1]
                - prefix[x1][y2 + 1]       // убираем верхнюю полосу
                - prefix[x2 + 1][y1]       // убираем левую полосу
                + prefix[x1][y1];           // возвращаем угол (вычли дважды)
    }

    public static void main(String[] args) {
        int[][] a = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 1, 2, 3}
        };
        RangeSumQuery2D rsq = new RangeSumQuery2D(a);

        // Сумма всего: 1+2+3+4+5+6+7+8+9+1+2+3 = 51
        System.out.println(rsq.query(0, 0, 2, 3)); // 51

        // Прямоугольник (1,1)..(2,2): 6+7+1+2 = 16
        System.out.println(rsq.query(1, 1, 2, 2)); // 16

        // Одна ячейка (0,0): 1
        System.out.println(rsq.query(0, 0, 0, 0)); // 1
    }
}
