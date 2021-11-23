package model;

import java.util.Random;

public class Game {
    Player player;
    Dragon dragon;
    Level level;
    int completedCount;

    Game(int mapSize) {
        level = new Level(mapSize);
        player = new Player(new Position(0, 0));
        dragon = new Dragon(generateStart());
        completedCount = 0;
    }
    Position generateStart(){
        Random rnd = new Random();
        int rndX = rnd.nextInt(level.mapSize);
        int rndY = rnd.nextInt(level.mapSize);

        while (level.getMap(rndX,rndY) != 0)
        {
            rndX = rnd.nextInt(level.mapSize);
            rndY = rnd.nextInt(level.mapSize);
        }
        return new Position(rndX,rndY);
    }
    boolean isEnded(){
        return player.getLocation().equals(dragon.getLocation());
    }

    boolean isCompleted(){
        return player.getLocation().equals(new Position(0,level.mapSize-1));
    }

    public boolean isValidPosition(Position p) {
        return (p.x >= 0 && p.y >= 0 && p.x < level.mapSize && p.y < level.mapSize);
    }

    public boolean isFree(Position p) {
        if (!isValidPosition(p)) return false;
        else return level.map[p.x][p.y] == 0;
    }

    public boolean movePlayer(Direction d) {
        Position curr = player.getLocation();
        Position next = curr.moveNext(d);
        if (isFree(next)) {
            player.moveChar(d);
            return true;
        }
        return false;
    }

    public void moveDragon() {
        Position curr = dragon.getLocation();
        Position next = curr.moveNext(dragon.currentD);
        if (isFree(next)) {
            player.moveChar(dragon.currentD);
        } else {
            dragon.newDirection();
        }
    }
}