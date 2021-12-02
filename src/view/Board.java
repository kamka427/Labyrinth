package view;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Board extends JPanel {
    public boolean canMove;
    public boolean dragonAnimated;
    private Game game;
    private boolean isDark;
    private int scale;
    private int px;
    private int py;
    private int dx;
    private int dy;

    public Board(Game game) {
        this.game = game;
        this.isDark = true;
        this.scale = 40;
        px = game.getPlayer().getLocation().x * scale;
        py = game.getPlayer().getLocation().y * scale;
        dx = game.getDragon().getLocation().x * scale;
        dy = game.getDragon().getLocation().y * scale;
        setBoardSize();
        Timer refreshTimer = new Timer(1, evt -> {
            revalidate();
            repaint();
        });
        refreshTimer.start();
        canMove = true;
        dragonAnimated = true;
    }

    public void setScale(int scale) {
        this.scale = scale;
        px = game.getPlayer().getLocation().x * scale;
        py = game.getPlayer().getLocation().y * scale;
        dx = game.getDragon().getLocation().x * scale;
        dy = game.getDragon().getLocation().y * scale;
    }

    public void newBoard(Game game) {
        this.game = game;
        px = game.getPlayer().getLocation().x * scale;
        py = game.getPlayer().getLocation().y * scale;
        dx = game.getDragon().getLocation().x * scale;
        dy = game.getDragon().getLocation().y * scale;
        setBoardSize();
        canMove = true;
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
        drawMap(g2d);
        playerAnimation(g2d);
        dragonAnimation(g2d);
            drawAura(g2d);

        g2d.dispose();
        g.dispose();
    }

    private void dragonAnimation(Graphics2D g2d) {
        if (dragonAnimated) {
            g2d.setColor(Color.RED);
            if (dx == game.getDragon().getLocation().x * scale && dy == game.getDragon().getLocation().y * scale) {
                drawDragon(g2d);
            } else {
                g2d.setColor(Color.RED);
                if (dx < game.getDragon().getLocation().x * scale) {
                    redrawDragon(g2d);
                    dx += 1;
                }
                if (dx > game.getDragon().getLocation().x * scale) {
                    redrawDragon(g2d);
                    dx -= 1;
                }
                if (dy < game.getDragon().getLocation().y * scale) {
                    redrawDragon(g2d);
                    dy += 1;
                }
                if (dy > game.getDragon().getLocation().y * scale) {
                    redrawDragon(g2d);
                    dy -= 1;
                }
            }
        } else {
            drawDragon(g2d);
        }
    }

    private void playerAnimation(Graphics2D g2d) {
        if (px == game.getPlayer().getLocation().x * scale && py == game.getPlayer().getLocation().y * scale) {
            g2d.setColor(Color.BLUE);
            g2d.drawOval(game.getPlayer().getLocation().y * scale + 2, game.getPlayer().getLocation().x * scale + 2, scale - 4, scale - 4);
            g2d.fillOval(game.getPlayer().getLocation().y * scale + 2, game.getPlayer().getLocation().x * scale + 2, scale - 4, scale - 4);
            canMove = true;
        } else {
            canMove = false;
            if (px < game.getPlayer().getLocation().x * scale) {
                redrawPlayer(g2d);
                px += 1;
            }
            if (px > game.getPlayer().getLocation().x * scale) {
                redrawPlayer(g2d);
                px -= 1;
            }
            if (py < game.getPlayer().getLocation().y * scale) {
                redrawPlayer(g2d);
                py += 1;
            }
            if (py > game.getPlayer().getLocation().y * scale) {
                redrawPlayer(g2d);
                py -= 1;
            }
        }
    }

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
                    if (game.getLevel().getMap(i, j) == 1) {
                        g2d.setColor(Color.BLACK);
                        g2d.drawRect(j * scale, i * scale, scale, scale);
                        g2d.fillRect(j * scale, i * scale, scale, scale);
                    } else g2d.drawRect(j * scale, i * scale, scale, scale);
                }
            }
        }
    }

    private void drawAura(Graphics2D g2d) {
        if (isDark) {
            Area outer = new Area(new Rectangle(0, 0, game.getMapSize() * scale, game.getMapSize() * scale));
            Ellipse2D.Double inner = new Ellipse2D.Double(py - scale * 3, px - scale * 3, 7 * scale, 7 * scale);
            outer.subtract(new Area(inner));
            g2d.setColor(Color.BLACK);
            g2d.fill(outer);
        }
    }

    private void drawDragon(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.drawOval(game.getDragon().getLocation().y * scale + 2, game.getDragon().getLocation().x * scale + 2, scale - 4, scale - 4);
        g2d.fillOval(game.getDragon().getLocation().y * scale + 2, game.getDragon().getLocation().x * scale + 2, scale - 4, scale - 4);
    }

    private void redrawPlayer(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        Ellipse2D.Double pl = new Ellipse2D.Double(py + 2, px + 2, scale - 4, scale - 4);
        g2d.fill(pl);
        revalidate();
        repaint();
    }

    private void redrawDragon(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        Ellipse2D.Double pl = new Ellipse2D.Double(dy + 2, dx + 2, scale - 4, scale - 4);
        g2d.fill(pl);
        revalidate();
        repaint();
    }
}
