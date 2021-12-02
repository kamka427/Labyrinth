package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Dragon extends Character {
    private Direction currentD;

    Dragon(Position startLocation) {
        super(startLocation);
        newDirection();
    }

    public Direction getCurrentD() {
        return currentD;
    }

    void newDirection() {
        currentD = Direction.values()[new Random().nextInt(Direction.values().length)];
    }

    void newDirection(boolean up, boolean down, boolean left, boolean right) {

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


    public void drawDragon(Graphics2D g2d, int scale) {
        g2d.setColor(Color.RED);
        g2d.drawOval(getDrawLoc().y + 2, getDrawLoc().x + 2, scale - 4, scale - 4);
        g2d.fillOval(getDrawLoc().y + 2, getDrawLoc().x + 2, scale - 4, scale - 4);
    }

    public void dragonAnimate(Graphics2D g2d, Game game, int scale) {
        g2d.setColor(Color.RED);
        if (getDrawLoc().x == game.getDragon().getLocation().x * scale && getDrawLoc().y == game.getDragon().getLocation().y * scale) {
            g2d.drawOval(game.getDragon().getLocation().y * scale + 2, game.getDragon().getLocation().x * scale + 2, scale - 4, scale - 4);
            g2d.fillOval(game.getDragon().getLocation().y * scale + 2, game.getDragon().getLocation().x * scale + 2, scale - 4, scale - 4);
        } else {
            g2d.setColor(Color.RED);
            if (getDrawLoc().x < game.getDragon().getLocation().x * scale) {
                drawDragon(g2d, scale);
                getDrawLoc().x += 1;
            }
            if (getDrawLoc().x > game.getDragon().getLocation().x * scale) {
                drawDragon(g2d, scale);
                getDrawLoc().x -= 1;
            }
            if (getDrawLoc().y < game.getDragon().getLocation().y * scale) {
                drawDragon(g2d, scale);
                getDrawLoc().y += 1;
            }
            if (getDrawLoc().y > game.getDragon().getLocation().y * scale) {
                drawDragon(g2d, scale);
                getDrawLoc().y -= 1;
            }
        }
    }
}
