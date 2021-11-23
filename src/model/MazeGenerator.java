package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;


public class MazeGenerator {
    private final int x;
    private final int y;
    private final int[][] maze;
    int matrixSize;
    private int[][] matrix;



    public MazeGenerator(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new int[this.x][this.y];
        matrix = new int[this.x*2+1][this.y*2+1];
        matrixSize = this.x*2+1;
        generateMaze(0, 0);
        toMatrix(displayWithMatrix());
        matrix.toString();
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
                System.out.print((maze[j][i] & 1) == 0 ? "1 1 " : "1 0 ");
                sb.append((maze[j][i] & 1) == 0 ? "11" : "10");
            }
            System.out.println("1");
            sb.append("1");

            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 8) == 0 ? "1 0 " : "0 0 ");
                sb.append((maze[j][i] & 8) == 0 ? "10" : "00");
            }
            System.out.println("1");
            sb.append("1");

        }
        for (int j = 0; j < x; j++) {
            System.out.print("1 1 ");
            sb.append("11");

        }
        System.out.println("1");
        sb.append("1");
        return sb.toString();

    }

    public int[][] toMatrix(String data) {
        int k = 0;
        for (int i = 0 ; i < matrixSize ; i++){
            for (int j = 0 ; j < matrixSize ; j++){
                matrix[i][j] = data.charAt(k++) - 48;
            }
        }
        return matrix;
    }

    private void generateMaze(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
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

    private enum DIR {
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
        private DIR opposite;

        DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    }

}