package view;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Board extends JPanel {
    private Game game;
    private boolean isDark;
    private int scale;

    public Board(Game game) {
        this.game = game;
        this.isDark = true;
        this.scale = 42;
        setBoardSize();
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void newBoard(Game game) {
        this.game = game;
        setBoardSize();
    }

    public void toggleDark() {
        isDark = !isDark;
    }

    public void setBoardSize() {
        Dimension dim = new Dimension((game.getLevel().getRealSize() + 2) * scale, (game.getLevel().getRealSize() + 2) * scale);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.GRAY);
        g2d.fillRect(game.getPlayer().getStartLoc().y * scale, game.getPlayer().getStartLoc().x * scale, scale, scale);
        g2d.setColor(Color.YELLOW);
        g2d.fillRect((game.getMapSize() - 2) * scale, 0, scale, scale);

        g2d.setColor(Color.YELLOW);
        g2d.fillRect((game.getMapSize() - 2) * scale, scale, scale, scale);

        for (int i = 0; i < game.getMapSize(); i++) {
            for (int j = 0; j < game.getMapSize(); j++) {
                {
                    if (game.getLevel().getMap(i, j) == 1) {
                        g2d.setColor(Color.BLACK);
                        g2d.drawRect(j * scale, i * scale, scale, scale);
                        g2d.fillRect(j * scale, i * scale, scale, scale);
                    }
                }
            }
        }

        g2d.setColor(Color.BLUE);
        g2d.drawOval(game.getPlayer().getLocation().y * scale + 2, game.getPlayer().getLocation().x * scale + 2, scale - 4, scale - 4);
        g2d.fillOval(game.getPlayer().getLocation().y * scale + 2, game.getPlayer().getLocation().x * scale + 2, scale - 4, scale - 4);

        g2d.setColor(Color.RED);
        g2d.drawOval(game.getDragon().getLocation().y * scale + 2, game.getDragon().getLocation().x * scale + 2, scale - 4, scale - 4);
        g2d.fillOval(game.getDragon().getLocation().y * scale + 2, game.getDragon().getLocation().x * scale + 2, scale - 4, scale - 4);

        if (isDark) {
            Area outer = new Area(new Rectangle(0, 0, game.getMapSize() * scale, game.getMapSize() * scale));
            int x = (game.getPlayer().getLocation().x * scale);
            int y = (game.getPlayer().getLocation().y * scale);
            Ellipse2D.Double inner = new Ellipse2D.Double(y - 3 * scale, x - 3 * scale, 7 * scale, 7 * scale);
            outer.subtract(new Area(inner));

            g2d.setColor(Color.BLACK);
            g2d.fill(outer);
        }

        g2d.dispose();
        g.dispose();
    }
}
