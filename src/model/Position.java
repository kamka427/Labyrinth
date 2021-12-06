package model;
/*
  Készítette: Neszlényi Kálmán Balázs
  Neptun kód: DPU51T
  Dátum: 2021. 12. 5.
 */

/**
 * Pozíció segédosztály
 * A gyakorlati kódok alapján
 * @author Neszlényi Kálmán Balázs
 */
public class Position {
    /**
     * A koordináták
     */
    public int x, y;

    /**
     * Pozíció példányosítása
     *
     * @param x visszintes koordináta
     * @param y függőleges koordináta
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * A koordináta mozgatása egy paraméterként átadott irányba
     *
     * @param d mozgatás iránya
     * @return az új pozíció
     */
    public Position moveNext(Direction d) {
        return new Position(x + d.x, y + d.y);
    }
}
