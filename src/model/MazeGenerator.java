package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
/*
  Készítette: Neszlényi Kálmán Balázs
  Neptun kód: DPU51T
  Dátum: 2021. 12. 5.
 */

/**
 * Labirintusgenerátor osztály
 * https://weblog.jamisbuck.org/2011/1/12/maze-generation-recursive-division-algorithm alapján
 * @author Neszlényi Kálmán Balázs
 */
public class MazeGenerator {
    private final int n;
    private final int m;
    private final int[][] maze;
    private final int matrixSize;
    private final int[][] matrix;

    /**
     * A labirintusgenerátor példányosítása
     *
     * @param n visszintes méret
     * @param m függőleges méret
     */
    public MazeGenerator(int n, int m) {
        this.n = n;
        this.m = m;
        maze = new int[this.n][this.m];
        matrix = new int[this.n * 2 + 1][this.m * 2 + 1];
        matrixSize = this.n * 2 + 1;
        generateMaze(0, 0);
        convertToMatrix(convertToString());
        removeWalls();
    }

    /**
     * Labirintus szélének lekéredezése
     *
     * @param curr jelenlegi érték
     * @param top  a labirintus tetje
     * @return nem lépnénk-e ki a labirintusból
     */
    private boolean between(int curr, int top) {
        return (curr >= 0) && (curr < top);
    }

    /**
     * A mátrix lekérdezése
     *
     * @return a mátrix
     */
    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * Bit labirintus normál mátrix-á alakítása
     *
     * @return normál mátrix szövegként
     */
    private String convertToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append((maze[j][i] & 1) == 0 ? "11" : "10");
            }
            sb.append("1");
            for (int j = 0; j < n; j++) {
                sb.append((maze[j][i] & 8) == 0 ? "10" : "00");
            }
            sb.append("1");
        }
        sb.append("11".repeat(Math.max(0, n)));
        sb.append("1");
        return sb.toString();
    }

    /**
     * A normál mátrix létrehozása
     *
     * @param data mátrix szöveges formában
     */
    private void convertToMatrix(String data) {
        int k = 0;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrix[i][j] = data.charAt(k++) - 48;
            }
        }
    }

    /**
     * Rekurzív osztásmódszer implementáció
     *
     * @param currx jelenlegi kamra visszintes koordináta
     * @param curry jelenlegi kamra függőleges koordináta
     */
    private void generateMaze(int currx, int curry) {
        Exploration[] directions = Exploration.values();
        Collections.shuffle(Arrays.asList(directions));
        for (Exploration dir : directions) {
            int newx = currx + dir.x;
            int newy = curry + dir.y;
            if (between(newx, n) && between(newy, m)
                    && (maze[newx][newy] == 0)) {
                maze[currx][curry] = maze[currx][curry] | dir.bit;
                maze[newx][newy] = maze[newx][newy] | dir.opposite.bit;
                generateMaze(newx, newy);
            }
        }
    }

    /**
     * Falak eltávolítása a labirintusból
     */
    private void removeWalls() {

        Random rnd = new Random();

        int tries = 0;
        int r1;
        int r2;
        while (tries < 100) {
            r1 = rnd.nextInt((matrixSize - 1) - 1) + 1;
            r2 = rnd.nextInt((matrixSize - 1) - 1) + 1;


            if (matrix[r1][r2] == 1 && ((matrix[r1][r2 + 1] != 1 && matrix[r1][r2 - 1] != 1 && matrix[r1][r2 + 2] != 1 && matrix[r1][r2 - 2] != 1 && matrix[r1][r2 + 3] != 1 && matrix[r1][r2 - 3] != 1)
                    || (matrix[r1 + 1][r2] != 1 && matrix[r1 - 1][r2] != 1 && matrix[r1 + 2][r2] != 1 && matrix[r1 - 2][r2] != 1 && matrix[r1 + 3][r2] != 1 && matrix[r1 - 3][r2] != 1))) {
                matrix[r1][r2] = 0;
            }
            tries++;
        }

        for (int i = 1; i < matrixSize - 1; i++) {
            for (int j = 1; j < matrixSize - 1; j++) {
                if (matrix[i][j] == 1 &&
                        matrix[i + 1][j] == 0 &&
                        matrix[i - 1][j] == 0 &&
                        matrix[i][j + 1] == 0 &&
                        matrix[i][j - 1] == 0) {
                    matrix[i][j] = 1;
                    matrix[i - 1][j] = 1;
                    matrix[i - 2][j] = 1;
                }
            }
        }
    }

    /**
     * privát enum osztály a labirintus feltérképezéséhez
     */
    private enum Exploration {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);

        /*
          ellentétes irányok
         */
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        /**
         * tárolt bit
         */
        private final int bit;
        /**
         * visszintes koordináta
         */
        private final int x;
        /**
         * függőleges koordináta
         */
        private final int y;
        /**
         * ellentéte az iránynak
         */
        private Exploration opposite;

        /**
         * Példányosítás
         *
         * @param bit bit
         * @param x   visszintes koordináta
         * @param y   függőleges koordináta
         */
        Exploration(int bit, int x, int y) {
            this.bit = bit;
            this.x = x;
            this.y = y;
        }
    }
}