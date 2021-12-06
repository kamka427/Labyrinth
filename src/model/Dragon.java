package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
/*
  Készítette: Neszlényi Kálmán Balázs
  Neptun kód: DPU51T
  Dátum: 2021. 12. 5.
 */

/**
 * A sárkány karakter osztálya
 * @author Neszlényi Kálmán Balázs
 */
public class Dragon extends Character {
    /**
     * Jelenlegi irány
     */
    private Direction currentD;

    /**
     * A sárkány példányosítása
     *
     * @param startLocation kezdőpozíció
     */
    public Dragon(Position startLocation) {
        super(startLocation);
        newDirection();
    }

    /**
     * Aktuális irány lekérdezése
     *
     * @return aktuális irány
     */
    public Direction getCurrentD() {
        return currentD;
    }

    /**
     * Új irány beállítása a játék kezdetéhez
     */
    private void newDirection() {
        currentD = Direction.values()[new Random().nextInt(Direction.values().length)];
    }

    /**
     * Új irány beállítása a játék közben
     *
     * @param up    szabad-e felfelé lépni
     * @param down  szabad-e lefelé lépni
     * @param left  szabad-e balra lépni
     * @param right szabad-e jobbra lépni
     */
    public void newDirection(boolean up, boolean down, boolean left, boolean right) {

        ArrayList<Direction> available = new ArrayList<>();
        if (up)
            available.add(Direction.UP);
        if (down)
            available.add(Direction.DOWN);
        if (left)
            available.add(Direction.LEFT);
        if (right)
            available.add(Direction.RIGHT);

        currentD = available.get(new Random().nextInt(available.size()));
    }

    /**
     * A sárkány kirajzolása
     *
     * @param g2d   grafika osztály
     * @param scale nagyítás mértéke
     */
    public void draw(Graphics2D g2d, int scale) {
        g2d.setColor(Color.RED);
        g2d.drawOval(getDrawLoc().y + 2, getDrawLoc().x + 2, scale - 4, scale - 4);
        g2d.fillOval(getDrawLoc().y + 2, getDrawLoc().x + 2, scale - 4, scale - 4);
    }

    /**
     * A sárkány animálása
     *
     * @param g2d   grafika osztály
     * @param game  játék osztály
     * @param scale nagyítás mértéke
     */
    public void animate(Graphics2D g2d, Game game, int scale) {

        g2d.setColor(Color.RED);
        if (getDrawLoc().x == game.getDragon().getLocation().x * scale && getDrawLoc().y == game.getDragon().getLocation().y * scale) {
            g2d.drawOval(game.getDragon().getLocation().y * scale + 2, game.getDragon().getLocation().x * scale + 2, scale - 4, scale - 4);
            g2d.fillOval(game.getDragon().getLocation().y * scale + 2, game.getDragon().getLocation().x * scale + 2, scale - 4, scale - 4);
            setCanMove(true);

        } else {
            setCanMove(false);
            g2d.setColor(Color.RED);
            if (getDrawLoc().x < game.getDragon().getLocation().x * scale) {
                draw(g2d, scale);
                getDrawLoc().x += getMoveSpeed();
            }
            if (getDrawLoc().x > game.getDragon().getLocation().x * scale) {
                draw(g2d, scale);
                getDrawLoc().x -= getMoveSpeed();
            }
            if (getDrawLoc().y < game.getDragon().getLocation().y * scale) {
                draw(g2d, scale);
                getDrawLoc().y += getMoveSpeed();
            }
            if (getDrawLoc().y > game.getDragon().getLocation().y * scale) {
                draw(g2d, scale);
                getDrawLoc().y -= getMoveSpeed();
            }
        }

    }
}
