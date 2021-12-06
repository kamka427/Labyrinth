package view;

import persistence.HighScore;

import javax.swing.*;
import java.util.ArrayList;
/*
  Készítette: Neszlényi Kálmán Balázs
  Neptun kód: DPU51T
  Dátum: 2021. 12. 5.
 */

/**
 * A pontszám megjelenítés dialógus osztálya
 * A gyakorlati kódok alapján
 * @author Neszlényi Kálmán Balázs
 */
public class HighScoreWindow extends JDialog {
    /**
     * A pontszám dialógus példányosítása
     *
     * @param highScores a pontszámok listája
     * @param parent     a dialógus szülője
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
