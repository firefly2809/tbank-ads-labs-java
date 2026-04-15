package ru.tbank.ads.labs.lab_6;

import java.util.LinkedList;
import java.util.Queue;

//Возможные ходы коня
//
//(+2, +1)
//(+2, -1)
//(-2, +1)
//(-2, -1)
//(+1, +2)
//(+1, -2)
//(-1, +2)
//(-1, -2)

//Создать массив visited[N][N]
//Запустить BFS из (x1, y1)
//Каждый раз пробовать 8 ходов
//Если достигли (x2, y2) — вернуть количество шагов

public class HungryHorse {

    static class Cell {
        int x, y, dist;

        Cell(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    static int minMoves(int n, int x1, int y1, int x2, int y2) {

        boolean[][] visited = new boolean[n][n];

        int[] dx = {2, 2, -2, -2, 1, 1, -1, -1};
        int[] dy = {1, -1, 1, -1, 2, -2, 2, -2};

        Queue<Cell> queue = new LinkedList<>();
        queue.add(new Cell(x1, y1, 0));
        visited[x1][y1] = true;

        while (!queue.isEmpty()) {

            Cell current = queue.poll();

            if (current.x == x2 && current.y == y2)
                return current.dist;

            for (int i = 0; i < 8; i++) {

                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                if (nx >= 0 && ny >= 0 && nx < n && ny < n
                        && !visited[nx][ny]) {

                    visited[nx][ny] = true;
                    queue.add(new Cell(nx, ny, current.dist + 1));
                    System.out.println("(" + nx + ", " + ny + ")");
                }
            }
        }

        return -1; // недостижимо (на обычной доске такого не бывает)
    }

    public static void main(String[] args) {
        System.out.println(minMoves(8, 0, 0, 7, 7));
    }
}
