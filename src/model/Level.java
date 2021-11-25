package model;

public class Level {
    private final int realSize;
    private final int[][] map;

    public int getMap(int x, int y) {
        return map[x][y];
    }

    public int getRealSize() {
        return realSize;
    }

    public Level(int mapSize) {
        map = new MazeGenerator(mapSize,mapSize).getMatrix();
        this.realSize = mapSize*2-1;
    }
}
