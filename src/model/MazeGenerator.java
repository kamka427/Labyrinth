package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class MazeGenerator {
    private final int x;
    private final int y;
    private final int[][] maze;
    private final int matrixSize;
    private final int[][] matrix;

    public MazeGenerator(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new int[this.x][this.y];
        matrix = new int[this.x * 2 + 1][this.y * 2 + 1];
        matrixSize = this.x * 2 + 1;
        generateMaze(0, 0);
        toMatrix(displayWithMatrix());
        removeWalls();

    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    public static void main(String[] args) {
        int x = args.length >= 1 ? (Integer.parseInt(args[0])) : 8;
        int y = args.length == 2 ? (Integer.parseInt(args[1])) : 8;
        MazeGenerator maze = new MazeGenerator(x, y);
        maze.display();
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void display() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
            }
            System.out.println("+");
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
            }
            System.out.println("|");
        }
        for (int j = 0; j < x; j++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }

    public String displayWithMatrix() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                //   System.out.print((maze[j][i] & 1) == 0 ? "1 1 " : "1 0 ");
                sb.append((maze[j][i] & 1) == 0 ? "11" : "10");
            }
            // System.out.println("1");
            sb.append("1");

            for (int j = 0; j < x; j++) {
                //System.out.print((maze[j][i] & 8) == 0 ? "1 0 " : "0 0 ");
                sb.append((maze[j][i] & 8) == 0 ? "10" : "00");
            }
            // System.out.println("1");
            sb.append("1");

        }
        //  System.out.print("1 1 ");
        sb.append("11".repeat(Math.max(0, x)));
        //  System.out.println("1");
        sb.append("1");
        return sb.toString();

    }

    public void toMatrix(String data) {
        int k = 0;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrix[i][j] = data.charAt(k++) - 48;
            }
        }
    }

    private void generateMaze(int cx, int cy) {
        EXPLORATION[] mazedirs = EXPLORATION.values();
        Collections.shuffle(Arrays.asList(mazedirs));
        for (EXPLORATION dir : mazedirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, x) && between(ny, y)
                    && (maze[nx][ny] == 0)) {
                maze[cx][cy] = maze[cx][cy] | dir.bit;
                maze[nx][ny] = maze[nx][ny] | dir.opposite.bit;
                generateMaze(nx, ny);
            }
        }
    }

    public void removeWalls() {

            Random rnd = new Random();

            int tries = 0;
            int r1;
            int r2;
            while (true) {
                r1 = rnd.nextInt((matrixSize - 1) - 1) + 1;
                r2 = rnd.nextInt((matrixSize - 1) - 1) + 1;


                if (matrix[r1][r2] == 1 && ((matrix[r1][r2 + 1] != 1 && matrix[r1][r2 - 1] != 1 && matrix[r1][r2 + 2] != 1 && matrix[r1][r2 - 2] != 1 && matrix[r1][r2 + 3] != 1 && matrix[r1][r2 - 3] != 1 )
                        || (matrix[r1 + 1][r2] != 1 && matrix[r1 - 1][r2] != 1 && matrix[r1 + 2][r2] != 1 && matrix[r1 - 2][r2] != 1 && matrix[r1 + 3][r2] != 1 && matrix[r1 - 3][r2] != 1 ))) {
                    matrix[r1][r2] = 0;
                }
                tries++;

                if(tries > 100)
                    break;

            }

            for (int i = 1; i < matrixSize - 1; i++) {
                for (int j = 1; j < matrixSize - 1; j++) {
                    if (matrix[i][j] == 1 &&
                            matrix[i + 1][j] == 0 &&
                            matrix[i - 1][j] == 0 &&
                            matrix[i][j + 1] == 0 &&
                            matrix[i][j - 1] == 0) {
                        matrix[i][j] = 1;
                        matrix[i-1][j] = 1;
                        matrix[i-2][j] = 1;

                    }

            }
        }
    }


    private enum EXPLORATION {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);

        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        private final int bit;
        private final int dx;
        private final int dy;
        private EXPLORATION opposite;

        EXPLORATION(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    }

}