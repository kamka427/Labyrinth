package model;
/*
  Készítette: Neszlényi Kálmán Balázs
  Neptun kód: DPU51T
  Dátum: 2021. 12. 5.
 */

/**
 * Enum az irányok kezeléséhez
 * A gyakorlati kódok alapján
 * @author Neszlényi Kálmán Balázs
 */
public enum Direction {
    /**
     * Az irányok
     * Le
     */
    DOWN(1, 0),
    /**
     * Balra
     */
    LEFT(0, -1),
    /**
     * Fel
     */
    UP(-1, 0),
    /**
     * Jobbra
     */
    RIGHT(0, 1);

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
