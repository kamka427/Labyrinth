package view;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import persistence.HighScore;

/**
 *
 */
public class HighScoreTableModel extends AbstractTableModel{
    /**
     *
     */
    private final ArrayList<HighScore> highScores;
    /**
     *
     */
    private final String[] colName = new String[]{ "Név", "Pontszám" };

    /**
     *
     * @param highScores
     */
    public HighScoreTableModel(ArrayList<HighScore> highScores){
        this.highScores = highScores;
    }

    /**
     *
     * @return
     */
    @Override
    public int getRowCount() { return highScores.size(); }

    /**
     *
     * @return
     */
    @Override
    public int getColumnCount() { return 2; }

    /**
     *
     * @param r
     * @param c
     * @return
     */
    @Override
    public Object getValueAt(int r, int c) {
        HighScore h = highScores.get(r);
        if      (c == 0) return h.id;
        else return h.completed;
    }

    /**
     *
     * @param i
     * @return
     */
    @Override
    public String getColumnName(int i) { return colName[i]; }
}
