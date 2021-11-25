package model;

public class Level {
    private final int mapSize;
    private final int[][] map;

    public int getMap(int x, int y) {
        return map[x][y];
    }

    public int getMapSize() {
        return mapSize;
    }

    public Level(int mapSize) {
        map = new MazeGenerator(mapSize,mapSize).getMatrix();
        this.mapSize = mapSize*2-1;
    }
}