package view;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Board extends JPanel {
    private Game game;
    private boolean isDark;

    public Board(Game game) {
        this.game = game;
        this.isDark = false;
    }

    public void newBoard(Game game)
    {
        this.game = game;
        this.isDark = false;


    }

    public void toggleDark() {
        isDark = !isDark;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GRAY);
        g.fillRect(game.getPlayer().getStartLoc().y*50, game.getPlayer().getStartLoc().x*50, 50, 50);
        g.setColor(Color.YELLOW);
        g.fillRect((game.getMapSize()-2)*50, 0, 50, 50);


        g.setColor(Color.YELLOW);
        g.fillRect((game.getMapSize()-2)*50, 50, 50, 50);



       for (int i = 0; i < game.getMapSize(); i++) {
            for (int j = 0; j < game.getMapSize(); j++) {
                {
                    if (game.getLevel().getMap(i, j) == 1) {
                        g.setColor(Color.BLACK);
                        g.drawRect(j * 50, i * 50, 50, 50);
                        g.fillRect(j * 50, i * 50, 50, 50);
                    }
               }
           }
       }


        g.setColor(Color.BLUE);
        g.drawOval(game.getPlayer().getLocation().y * 50 + 2, game.getPlayer().getLocation().x * 50 + 2, 46, 46);
        g.fillOval(game.getPlayer().getLocation().y * 50 + 2, game.getPlayer().getLocation().x * 50 + 2, 46, 46);

        g.setColor(Color.RED);
        g.drawOval(game.getDragon().getLocation().y * 50 + 2, game.getDragon().getLocation().x * 50 + 2, 46, 46);
        g.fillOval(game.getDragon().getLocation().y * 50 + 2, game.getDragon().getLocation().x * 50 + 2, 46, 46);


        if(isDark) {
            Area outer = new Area(new Rectangle(0, 0, game.getMapSize() * 50, game.getMapSize() * 50));
            int y = (game.getPlayer().getLocation().x * 50);
            int x = (game.getPlayer().getLocation().y * 50);
            Ellipse2D.Double inner = new Ellipse2D.Double(x - 3 * 50, y - 3 * 50, 7 * 50, 7 * 50);
            outer.subtract(new Area(inner));

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.BLACK);
            g2d.fill(outer);
            g2d.dispose();
        }


        /*if(game.isEnded())
        {g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }*/
        g.dispose();
    }
}
