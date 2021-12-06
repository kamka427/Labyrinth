package persistence;
/*
  Készítette: Neszlényi Kálmán Balázs
  Neptun kód: DPU51T
  Dátum: 2021. 12. 5.
 */

/**
 * A pontszám osztály implementációja
 * A gyakorlati kódok alapján
 * @author Neszlényi Kálmán Balázs
 */
public class HighScore {
    /**
     * Pontszámhoz tartozó név
     */
    public final String name;
    /**
     * A pontszám
     */
    public final int score;

    /**
     * Egy pontszám példányosítása
     *
     * @param name  azonosító
     * @param score teljesített pontszám
     */
    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Egy pontszám belső állapotának szöveggé alakítása
     *
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
