package model;

import java.awt.*;

public class Player extends Character {
    Player(Position startLocation){
        super(startLocation);
    }

    public void drawPlayer(Graphics2D g2d, int scale) {
        g2d.setColor(Color.BLUE);
        g2d.drawOval(getDrawLoc().y + 2, getDrawLoc().x + 2, scale - 4, scale - 4);
        g2d.fillOval(getDrawLoc().y + 2, getDrawLoc().x + 2, scale - 4, scale - 4);
    }

    public boolean playerAnimate(Graphics2D g2d, Game game, int scale) {
        boolean canMove;
        if (getDrawLoc().x == game.getPlayer().getLocation().x * scale && getDrawLoc().y == game.getPlayer().getLocation().y * scale) {
            g2d.setColor(Color.BLUE);
            g2d.drawOval(game.getPlayer().getLocation().y * scale + 2, game.getPlayer().getLocation().x * scale + 2, scale - 4, scale - 4);
            g2d.fillOval(game.getPlayer().getLocation().y * scale + 2, game.getPlayer().getLocation().x * scale + 2, scale - 4, scale - 4);
            canMove = true;
        } else {
            canMove = false;
            if (getDrawLoc().x < game.getPlayer().getLocation().x * scale) {
                drawPlayer(g2d,scale);
                getDrawLoc().x += 1;
            }
            if (getDrawLoc().x > game.getPlayer().getLocation().x * scale) {
                drawPlayer(g2d,scale);
                getDrawLoc().x -= 1;
            }
            if (getDrawLoc().y < game.getPlayer().getLocation().y * scale) {
                drawPlayer(g2d,scale);
                getDrawLoc().y += 1;
            }
            if (getDrawLoc().y > game.getPlayer().getLocation().y * scale) {
                drawPlayer(g2d,scale);
                getDrawLoc().y -= 1;
            }
        }
        return canMove;
    }
}
