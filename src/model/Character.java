package model;

import java.awt.*;

/**
 * Karakter absztrakt ősosztály
 */
public abstract class Character {
    /**
     * Kezdőpozíció
     */
    private final Position startLoc;
    /**
     * Mátrixbeli pozíció
     */
    private Position location;
    /**
     *
     */
    private Position drawLoc;

    /**
     * Karakter példányosítása
     *
     * @param startLocation kezdőpozíció
     */
    public Character(Position startLocation) {
        this.location = startLocation;
        this.startLoc = startLocation;
    }

    /**
     * Animációbeli pozíció lekérdezése
     *
     * @return
     */
    public Position getDrawLoc() {
        return drawLoc;
    }

    /**
     * Animációbeli pozíció beállítása
     *
     * @param drawLoc új pozíció
     */
    public void setDrawLoc(Position drawLoc) {
        this.drawLoc = drawLoc;
    }

    /**
     * Kezdőpozíció lekérdezése
     *
     * @return kezdőpozíció
     */
    public Position getStartLoc() {
        return startLoc;
    }

    /**
     * Mátrixbeli pozíció lekérdezése
     *
     * @return mátrixbeli pozíció
     */
    public Position getLocation() {
        return location;
    }

    /**
     * Karakter mozgatása a paraméterként átadott irányba
     *
     * @param d mozgatás iránya
     */
    public void moveChar(Direction d) {
        location = new Position(location.x + d.x, location.y + d.y);
    }

    /**
     * Absztrakt metódus a karakterek kirajzolásához
     *
     * @param g2d   grafika osztály
     * @param scale nagyítás mértéke
     */
    abstract public void draw(Graphics2D g2d, int scale);
}
