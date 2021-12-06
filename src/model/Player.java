package model;

import java.awt.*;
/*
  Készítette: Neszlényi Kálmán Balázs
  Neptun kód: DPU51T
  Dátum: 2021. 12. 5.
 */

/**
 * A játékos osztálya
 * @author Neszlényi Kálmán Balázs
 */
public class Player extends Character {
    /**
     * Játékos példánosítása
     *
     * @param startLocation kezdőpozíció
     */
    public Player(Position startLocation) {
        super(startLocation);
    }

    /**
     * A játékos kirajzolása
     *
     * @param g2d   grafika osztály
     * @param scale nagyítás mértéke
     */
    public void draw(Graphics2D g2d, int scale) {
        g2d.setColor(Color.BLUE);
        g2d.drawOval(getDrawLoc().y + 2, getDrawLoc().x + 2, scale - 4, scale - 4);
        g2d.fillOval(getDrawLoc().y + 2, getDrawLoc().x + 2, scale - 4, scale - 4);
    }

    /**
     * @param g2d   grafika osztály
     * @param game  játék osztály
     * @param scale nagyítás mértéke
     */
    public void animate(Graphics2D g2d, Game game, int scale) {
        if (getDrawLoc().x == game.getPlayer().getLocation().x * scale && getDrawLoc().y == game.getPlayer().getLocation().y * scale) {
            g2d.setColor(Color.BLUE);
            g2d.drawOval(game.getPlayer().getLocation().y * scale + 2, game.getPlayer().getLocation().x * scale + 2, scale - 4, scale - 4);
            g2d.fillOval(game.getPlayer().getLocation().y * scale + 2, game.getPlayer().getLocation().x * scale + 2, scale - 4, scale - 4);
            setCanMove(true);
        } else {
            setCanMove(false);
            if (getDrawLoc().x < game.getPlayer().getLocation().x * scale) {
                draw(g2d, scale);
                getDrawLoc().x += getMoveSpeed();
            }
            if (getDrawLoc().x > game.getPlayer().getLocation().x * scale) {
                draw(g2d, scale);
                getDrawLoc().x -= getMoveSpeed();
            }
            if (getDrawLoc().y < game.getPlayer().getLocation().y * scale) {
                draw(g2d, scale);
                getDrawLoc().y += getMoveSpeed();
            }
            if (getDrawLoc().y > game.getPlayer().getLocation().y * scale) {
                draw(g2d, scale);
                getDrawLoc().y -= getMoveSpeed();
            }
        }

    }
}
