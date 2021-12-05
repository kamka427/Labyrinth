package model;

/**
 * Szint osztály implementációja
 */
public class Level {
    /**
     * A pálya rendes mátrixbeli mérete
     */
    private final int realSize;
    /**
     * A pálya
     */
    private final int[][] map;

    /**
     * A pálya példányosítása
     * @param mapSize pályaméret
     */
    public Level(int mapSize) {
        map = new MazeGenerator(mapSize, mapSize).getMatrix();
        this.realSize = mapSize * 2 - 1;
    }

    /**
     * A pálya egy mezőjének lekérdezése
     * @param x visszintes koordináta
     * @param y függőleges koordináta
     * @return a pálya mezőjének értéke
     */
    public int getMapValue(int x, int y) {
        return map[x][y];
    }

    /**
     * A pálya normál méretének lekérdezése
     * @return a pálya valós mérete
     */
    public int getRealSize() {
        return realSize;
    }
}
