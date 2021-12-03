package model;

/**
 * Enum az irányok kezeléséhez
 */
public enum Direction {
    /**
     * Az irányok
     */
    DOWN(1, 0), LEFT(0, -1), UP(-1, 0), RIGHT(0, 1);

    /**
     * A koordináták
     */
    public final int x, y;

    /**
     * Új pozíció beállítása
     *
     * @param x visszintes koordináta
     * @param y függőleges koordináta
     */
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
