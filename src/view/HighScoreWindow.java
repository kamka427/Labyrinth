package view;

import persistence.HighScore;

import javax.swing.*;
import java.util.ArrayList;

/**
 *
 */
public class HighScoreWindow extends JDialog {
    /**
     * @param highScores
     * @param parent
     */
    public HighScoreWindow(ArrayList<HighScore> highScores, JFrame parent) {
        super(parent, true);
        JTable table = new JTable(new HighScoreTableModel(highScores));
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table));
        setSize(400, 400);
        setTitle("Dicsőség tábla");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
