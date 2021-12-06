package view;

import model.Game;
import model.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/*
  Készítette: Neszlényi Kálmán Balázs
  Neptun kód: DPU51T
  Dátum: 2021. 12. 5.
 */

/**
 * A játék tábla grafikájának implementációja
 * @author Neszlényi Kálmán Balázs
 */
public class Board extends JPanel {


    /**
     * A játék egy példánya
     */
    private Game game;
    /**
     * A teljes tábla látható-e
     */
    private boolean isDark;
    /**
     * A nagyítás mértéke
     */
    private int scale;

    /**
     * A tábla példányosítása
     *
     * @param game a játék egy példánya
     */
    public Board(Game game) {
        int FPS = 1000;
        this.game = game;
        this.isDark = true;
        this.scale = 40;
        game.getPlayer().setDrawLoc(new Position(game.getPlayer().getLocation().x * scale, game.getPlayer().getLocation().y * scale));
        game.getDragon().setDrawLoc(new Position(game.getDragon().getLocation().x * scale, game.getDragon().getLocation().y * scale));

        setBoardSize();
        Timer refreshTimer = new Timer(1000 / FPS, evt -> repaint());
        refreshTimer.start();

    }

    /**
     * A nagyítás mértékének lekérdezése
     *
     * @return a nagyítás mértéke
     */
    public int getScale() {
        return scale;
    }


    /**
     * A nagyítás mértékének átállítása
     *
     * @param scale a nagyítás új mértéke
     */
    public void setScale(int scale) {
        this.scale = scale;
        game.getPlayer().setDrawLoc(new Position(game.getPlayer().getLocation().x * scale, game.getPlayer().getLocation().y * scale));
        game.getDragon().setDrawLoc(new Position(game.getDragon().getLocation().x * scale, game.getDragon().getLocation().y * scale));
    }

    /**
     * Új tábla létrehozása
     *
     * @param game a játék egy példánya
     */
    public void newBoard(Game game) {
        this.game = game;
        game.getPlayer().setDrawLoc(new Position(game.getPlayer().getLocation().x * scale, game.getPlayer().getLocation().y * scale));
        game.getDragon().setDrawLoc(new Position(game.getDragon().getLocation().x * scale, game.getDragon().getLocation().y * scale));
        setBoardSize();

    }

    /**
     * A pálya láthatóságának kapcsolása
     */
    public void toggleDark() {
        isDark = !isDark;
    }

    /**
     * A pálya átméretezése
     */
    public void setBoardSize() {
        Dimension dim = new Dimension((game.getLevel().getRealSize() + 2) * scale, (game.getLevel().getRealSize() + 2) * scale);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
    }

    /**
     * A játék kirajzolása
     *
     * @param g grafika osztály
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        drawMap(g2d);
        game.getPlayer().animate(g2d, game, scale);
        game.getDragon().animate(g2d, game, scale);
        drawAura(g2d);

        g2d.dispose();
        g.dispose();
    }

    /**
     * A pálya kirajzolása
     *
     * @param g2d grafika osztály
     */
    private void drawMap(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        g2d.fillRect(game.getPlayer().getStartLoc().y * scale, game.getPlayer().getStartLoc().x * scale, scale, scale);
        g2d.setColor(Color.YELLOW);
        g2d.fillRect((game.getMapSize() - 2) * scale, 0, scale, scale);

        g2d.setColor(Color.YELLOW);
        g2d.fillRect((game.getMapSize() - 2) * scale, scale, scale, scale);

        for (int i = 0; i < game.getMapSize(); i++) {
            for (int j = 0; j < game.getMapSize(); j++) {
                {
                    if (game.getLevel().getMapValue(i, j) == 1) {
                        g2d.setColor(Color.BLACK);
                        g2d.drawRect(j * scale, i * scale, scale, scale);
                        g2d.fillRect(j * scale, i * scale, scale, scale);
                    } else g2d.drawRect(j * scale, i * scale, scale, scale);
                }
            }
        }
    }

    /***
     * A nem látható területek letakarása
     * @param g2d grafika osztály
     */
    private void drawAura(Graphics2D g2d) {
        if (isDark) {
            Area outer = new Area(new Rectangle(0, 0, game.getMapSize() * scale, game.getMapSize() * scale));
            Ellipse2D.Double inner = new Ellipse2D.Double(game.getPlayer().getDrawLoc().y - scale * 3, game.getPlayer().getDrawLoc().x - scale * 3, 7 * scale, 7 * scale);
            outer.subtract(new Area(inner));
            g2d.setColor(Color.BLACK);
            g2d.fill(outer);
        }
    }
}
