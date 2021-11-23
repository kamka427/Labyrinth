package model;

public class Level {
    MazeGenerator mazeGenerator;
    int mapSize;
    int[][] map;

    public int getMap(int x, int y) {
        return map[x][y];
    }

    public Level(int mapSize) {
        this.mapSize = mapSize;
        mazeGenerator = new MazeGenerator(mapSize,mapSize);
        map = mazeGenerator.toMatrix(mazeGenerator.displayWithMatrix());
    }
}
