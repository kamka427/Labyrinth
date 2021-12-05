package persistence;


/**
 * A pontszám osztály implementációja
 */
public class HighScore {
    public final String name;
    public final int score;

    /**
     * Egy pontszám példányosítása
     * @param name azonosító
     * @param score teljesített pontszám
     */
    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Egy pontszám belső állapotának szöveggé alakítása
     * @return objektum szövegként
     */
    @Override
    public String toString() {
        return "HighScore{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
