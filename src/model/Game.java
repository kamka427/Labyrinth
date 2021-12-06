package model;

import persistence.Database;
import persistence.HighScore;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
/*
  Készítette: Neszlényi Kálmán Balázs
  Neptun kód: DPU51T
  Dátum: 2021. 12. 5.
 */

/**
 * A játékosztály
 * @author Neszlényi Kálmán Balázs
 */
public class Game {
    /**
     * Az adatbázis egy példánya
     */
    private final Database database;
    /**
     * Véletlenszerű méretűek-e a pályák
     */
    private final boolean randomized;
    /**
     * A játékos egy példánya
     */
    private Player player;
    /**
     * A sárkány egy példánya
     */
    private Dragon dragon;
    /**
     * A játékszint egy példánya
     */
    private Level level;
    /**
     * Teljesített szintek száma
     */
    private int completedCount;
    /**
     * A szint mérete
     */
    private int mapSize;
    /**
     * A labirintusgenerátornak átadott mérett bitekhez
     */
    private int generationSize;
    /**
     * A játékos neve
     */
    private String playerName;

    /**
     * A játék példányosítása átadott pályamérettel
     *
     * @param size       a pályaméret
     * @param playerName a játékos neve
     */
    public Game(int size, String playerName) {
        generationSize = size;
        this.mapSize = size * 2 + 1;
        level = new Level(size);
        player = new Player(new Position(size * 2 - 1, 1));
        dragon = new Dragon(generateStart());
        completedCount = 0;
        randomized = false;
        this.playerName = playerName;
        database = new Database();
    }

    /**
     * A játék példányosítása véletlenszerű pályaméretekkel
     *
     * @param playerName a játékos neve
     */
    public Game(String playerName) {
        Random rnd = new Random();
        generationSize = rnd.nextInt(12 - 6) + 6;
        this.mapSize = (generationSize * 2 + 1);
        level = new Level(generationSize);
        player = new Player(new Position(generationSize * 2 - 1, 1));
        dragon = new Dragon(generateStart());
        completedCount = 0;
        randomized = true;
        this.playerName = playerName;
        database = new Database();
    }

    /**
     * A játékos nevének átállítása
     *
     * @param playerName a játékos új neve
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * A teljesített pályák számának lekérdezése
     *
     * @return a teljesített pályák száma szövegként
     */
    public String getCompletedCount() {
        return "Teljesített pályák száma: " + completedCount;
    }

    /**
     * A teljesített pályák számának lekérdezése
     *
     * @return a teljesített pályák száma számként
     */
    public int getCompletedCountValue() {
        return completedCount;
    }

    /**
     * A játékos lekérdezése
     *
     * @return a játékos
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * A sárkány lekérdezése
     *
     * @return a sárkány
     */
    public Dragon getDragon() {
        return dragon;
    }

    /**
     * A pálya lekérdezése
     *
     * @return a pálya
     */
    public Level getLevel() {
        return level;
    }

    /**
     * A teljesített pályák számának növelése
     */
    public void addToCompletedCount() {
        this.completedCount++;
    }

    /**
     * A pályméret lekérdezése
     *
     * @return a pályaméret
     */
    public int getMapSize() {
        return mapSize;
    }

    /**
     * Kezdőpozíció generálása a sárkánynak
     *
     * @return a kezdőpozíció
     */
    private Position generateStart() {
        Random rnd = new Random();
        int rndX = rnd.nextInt(generationSize / 2 * 2 - 1);
        int rndY = rnd.nextInt(generationSize * 2 - 1);

        while (level.getMapValue(rndX, rndY) != 0) {
            rndX = rnd.nextInt(generationSize / 2 * 2 - 1);
            rndY = rnd.nextInt(generationSize * 2 - 1);
        }
        return new Position(rndX, rndY);
    }

    /**
     * A pontszámok lekérdezése az adtbázisból
     *
     * @return a pontszámok
     * @throws SQLException SQL kivétel
     */
    public ArrayList<HighScore> getHighScores() throws SQLException {
        return database.getHighScores();
    }

    /**
     * A játék végének ellenőrzése
     *
     * @return logikai érték a játék végéről
     */
    public boolean isEnded() {
        return player.getLocation().x + 1 == dragon.getLocation().x && player.getLocation().y == dragon.getLocation().y ||
                player.getLocation().x - 1 == dragon.getLocation().x && player.getLocation().y == dragon.getLocation().y ||
                player.getLocation().x == dragon.getLocation().x && player.getLocation().y + 1 == dragon.getLocation().y ||
                player.getLocation().x == dragon.getLocation().x && player.getLocation().y - 1 == dragon.getLocation().y;
    }

    /**
     * A játékos elérte-e a labirintus végét
     *
     * @return logikai érték a célbaérésről
     */
    public boolean isCompleted() {
        return player.getLocation().x == 1 && player.getLocation().y == level.getRealSize() && !isEnded();
    }

    /**
     * Új pálya generálása
     */
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

    /**
     * A generálási méret lekérdezése
     *
     * @return generálási méret
     */
    public int getGenerationSize() {
        return generationSize;
    }

    /**
     * A mátrix egy mezőjének lekérdezése
     *
     * @param p koordináta
     * @return szabad-e a mező
     */
    private boolean isFree(Position p) {
        return level.getMapValue(p.x, p.y) == 0;
    }

    /**
     * A játékos mozgatásának algoritmusa
     *
     * @param d mozgatás iránya
     */
    public void movePlayer(Direction d) {
        Position curr = player.getLocation();
        Position next = curr.moveNext(d);
        if (isFree(next) && !isEnded()) {
            player.move(d);
        }
    }

    /**
     * A sárkány mozgatásának algoritmusa
     */
    public void moveDragon() {
        Position curr = dragon.getLocation();
        Position next = curr.moveNext(dragon.getCurrentD());
        if (isFree(next)) {
            dragon.move(dragon.getCurrentD());
        } else {
            dragon.newDirection(isFree(curr.moveNext(Direction.UP)), isFree(curr.moveNext(Direction.DOWN)), isFree(curr.moveNext(Direction.LEFT)), isFree(curr.moveNext(Direction.RIGHT)));
        }
    }

    /**
     * A pontszám mentése az adatbázisba
     *
     * @param difficultyMul nehézség szorzó
     * @param mapSizeMul    pályaméret szorzó
     */
    public void saveScore(int difficultyMul, int mapSizeMul) {
        try {
            database.putHighScore(playerName, completedCount * difficultyMul * mapSizeMul);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}