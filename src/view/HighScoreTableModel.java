package view;

import persistence.HighScore;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Model a pontszámok megjelenítéséhez
 */
public class HighScoreTableModel extends AbstractTableModel {
    /**
     * A pontszámokat tartalmazó lista
     */
    private final ArrayList<HighScore> highScores;
    /**
     * Az oszlopok nevei
     */
    private final String[] colName = new String[]{"Név", "Pontszám"};

    /**
     * A pontszám megjelenítés modelének példányosítása
     * @param highScores a pontszámok listája
     */
    public HighScoreTableModel(ArrayList<HighScore> highScores) {
        this.highScores = highScores;
    }

    /**
     * A sorok számának lekérdezése
     * @return a sorok száma
     */
    @Override
    public int getRowCount() {
        return highScores.size();
    }

    /**
     * Az oszlopok számának lekérdezése
     * @return az oszlopok száma
     */
    @Override
    public int getColumnCount() {
        return 2;
    }

    /**
     * Adott sorban és oszlopban lévő érték lekérdezése
     * @param r sor
     * @param c oszlop
     * @return az érték a megadott pozíción
     */
    @Override
    public Object getValueAt(int r, int c) {
        HighScore h = highScores.get(r);
        if (c == 0) return h.name;
        else return h.score;
    }

    /**
     * Egy oszlop nevének lekérdezése
     * @param i az oszlop index
     * @return az i.-edik oszlop neve
     */
    @Override
    public String getColumnName(int i) {
        return colName[i];
    }
}
