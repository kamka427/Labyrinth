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
     * Animációbeli pozíció
     */
    private Position drawLoc;
    /**
     * Mozgási sebesség
     */
    private int moveSpeed;
    /**
     * Léphet-e
     */
    private boolean canMove;

    /**
     * Karakter példányosítása
     *
     * @param startLocation kezdőpozíció
     */
    public Character(Position startLocation) {
        this.location = startLocation;
        this.startLoc = startLocation;
        this.moveSpeed = 5;
        this.canMove = true;
    }

    /**
     * A karakter sebességének lekérdezése
     *
     * @return a karakter sebessége
     */
    public int getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * A karakter sebességének átállítása
     *
     * @param moveSpeed a karakter új sebessége
     */
    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    /**
     * Lekérdezés arról, hogy léphet-e már újra a játékos
     *
     * @return léphet-e
     */
    public boolean isCanMove() {
        return canMove;
    }

    /**
     * Beállítás arról, hogy léphet-e már újra a játékos
     *
     * @param canMove léphet-e
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /**
     * Animációbeli pozíció lekérdezése
     *
     * @return animációbeli pozíció
     */
    public Position getDrawLoc() {
        return drawLoc;
    }

    /**
     * Animációbeli pozíció beállítása
     *
     * @param drawLoc új animációbeli pozíció
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
    public void move(Direction d) {
        location = new Position(location.x + d.x, location.y + d.y);
    }

    /**
     * Absztrakt metódus a karakterek kirajzolásához
     *
     * @param g2d   grafika osztály
     * @param scale nagyítás mértéke
     */
    abstract public void draw(Graphics2D g2d, int scale);

    /**
     * Absztrakt metódus a karakterek animálásához
     *
     * @param g2d   grafika osztály
     * @param game  játék példány
     * @param scale nagyítás mértéke
     */
    abstract public void animate(Graphics2D g2d, Game game, int scale);
}
