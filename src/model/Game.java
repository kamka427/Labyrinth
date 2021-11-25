package model;

import persistence.Database;

import java.util.Random;

public class Game {
    private Player player;
    private Dragon dragon;
    private Level level;
    private int completedCount;
    private int mapSize;
    private int generationSize;
    private boolean randomized;
    private final Database database;
    private String playerName;



    public Game(int Size) {
        generationSize = Size;
        this.mapSize = Size * 2 + 1;
        level = new Level(Size);
        player = new Player(new Position(Size * 2 - 1, 1));
        dragon = new Dragon(generateStart());
        completedCount = 0;
        randomized = false;
        database = new Database();

    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Game() {
        Random rnd = new Random();
        generationSize = rnd.nextInt(10 - 6) + 6;
        this.mapSize = (generationSize * 2 + 1);
        level = new Level(generationSize);
        player = new Player(new Position(generationSize * 2 - 1, 1));
        dragon = new Dragon(generateStart());
        completedCount = 0;
        randomized = true;
        database = new Database();
    }

    public String getCompletedCount() {
        return "Teljesített pályák száma: " + completedCount;
    }

    public Player getPlayer() {
        return player;
    }

    public Dragon getDragon() {
        return dragon;
    }

    public Level getLevel() {
        return level;
    }

    public void addToCompletedCount() {
        this.completedCount++;
    }

    public int getMapSize() {
        return mapSize;
    }

    Position generateStart() {
        Random rnd = new Random();
        int rndX = rnd.nextInt(generationSize/2 * 2 - 1);
        int rndY = rnd.nextInt(generationSize * 2 - 1);

        while (level.getMap(rndX, rndY) != 0) {
            rndX = rnd.nextInt(generationSize/2 * 2 - 1);
            rndY = rnd.nextInt(generationSize * 2 - 1);
        }
        return new Position(rndX, rndY);
    }

    public boolean isEnded() {
        return player.getLocation().x + 1 == dragon.getLocation().x && player.getLocation().y == dragon.getLocation().y ||
                player.getLocation().x - 1 == dragon.getLocation().x && player.getLocation().y == dragon.getLocation().y ||
                player.getLocation().x == dragon.getLocation().x && player.getLocation().y + 1 == dragon.getLocation().y ||
                player.getLocation().x == dragon.getLocation().x && player.getLocation().y - 1 == dragon.getLocation().y;
    }

    public boolean isCompleted() {
        return player.getLocation().x == 1 && player.getLocation().y == level.getRealSize() && !isEnded();
    }

    public void newLevel() {
        if (randomized) {
            Random rnd = new Random();
            generationSize = rnd.nextInt(10 - 6) + 6;
            mapSize = generationSize * 2 + 1;
        }
        level = new Level(generationSize);
        player = new Player(new Position(mapSize - 2, 1));
        dragon = new Dragon(generateStart());
    }

    public int getGenerationSize() {
        return generationSize;
    }

    public boolean isFree(Position p) {
        return level.getMap(p.x, p.y) == 0;
    }

    public void movePlayer(Direction d) {
        Position curr = player.getLocation();
        Position next = curr.moveNext(d);
        if (isFree(next) && !isEnded()) {
            player.moveChar(d);
        }
    }

    public void moveDragon() {
        Position curr = dragon.getLocation();
        Position next = curr.moveNext(dragon.getCurrentD());
        if (isFree(next)) {
            dragon.moveChar(dragon.getCurrentD());
        } else {
            dragon.newDirection(isFree(curr.moveNext(Direction.UP)), isFree(curr.moveNext(Direction.DOWN)), isFree(curr.moveNext(Direction.LEFT)), isFree(curr.moveNext(Direction.RIGHT)));
        }
        if(isEnded())
        {
            database.storeHighScore(playerName,calculateScore());
        }
    }

    public int calculateScore(){
        if(randomized)
            return completedCount * 4 ;
        else return  completedCount * mapSize /4;

    }
}