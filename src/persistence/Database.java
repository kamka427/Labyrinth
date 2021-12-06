package persistence;

import java.sql.*;
import java.util.ArrayList;
/*
  Készítette: Neszlényi Kálmán Balázs
  Neptun kód: DPU51T
  Dátum: 2021. 12. 5.
 */

/**
 * Az adatbázis kapcsolat implementációja
 * A gyakorlati kódok alapján
 * @author Neszlényi Kálmán Balázs
 */
public class Database {
    /**
     * Kapcsolat a MySQL adatbázissal
     */
    private final Connection connection;
    /**
     * A tárolt adatok maximális mérete
     */
    private final int maxScores;
    /**
     * Query a beszúráshoz
     */
    private PreparedStatement insertStatement;
    /**
     * Query a törléshez
     */
    private PreparedStatement deleteStatement;

    /**
     * Az adatbázis példányosítása
     */
    public Database() {
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/labyrinth", "student", "asd123");
        } catch (Exception ex) {
            System.out.println("No connection");
        }
        this.connection = c;
        String insertQuery = "INSERT INTO SCORES (NAME, SCORE) VALUES (?, ?) ON DUPLICATE KEY UPDATE SCORE=?";
        String deleteQuery = "DELETE FROM SCORES WHERE SCORE=? LIMIT 1";
        try {
            if (connection != null) {
                insertStatement = connection.prepareStatement(insertQuery);
                deleteStatement = connection.prepareStatement(deleteQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        maxScores = 10;
    }

    /**
     * Pontszámok lekérdezése
     *
     * @return a pontszámok listája
     * @throws SQLException SQL kivétel
     */
    public ArrayList<HighScore> getHighScores() throws SQLException {
        String query = "SELECT * FROM SCORES";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            highScores.add(new HighScore(name, score));
        }
        sortHighScores(highScores);
        return highScores;
    }

    /**
     * Pontszám beszúrása az adatbázisba
     *
     * @param name  játékos neve
     * @param score játékos pontszáma
     * @throws SQLException SQL kivétel
     */
    public void putHighScore(String name, int score) throws SQLException {
        ArrayList<HighScore> highScores = getHighScores();

        boolean exists = false;
        for (HighScore val : getHighScores()) {
            if (val.name.equals(name)) {
                exists = true;
            }
        }

        if (highScores.size() < maxScores || exists) {
            insertScore(name, score);
        } else {
            int leastScore = highScores.get(highScores.size() - 1).score;
            if (leastScore < score) {
                deleteScores(leastScore);
                insertScore(name, score);
            }
        }
    }

    /**
     * A pontszámok listájának rendezése a pontok nagysága szerint
     *
     * @param highScores rendezendő lista
     */
    private void sortHighScores(ArrayList<HighScore> highScores) {
        highScores.sort((a, b) -> b.score - a.score);
    }

    /**
     * Pontszámok beszúrása Queryvel amennyiben szükséges
     *
     * @param name  játékos neve
     * @param score játékos pontszáma
     * @throws SQLException SQL kivétel
     */
    private void insertScore(String name, int score) throws SQLException {
        boolean doUpdate = true;


        for (HighScore val : getHighScores()) {
            if (val.name.equals(name))
                doUpdate = (score > val.score);
        }
        if (doUpdate) {
            insertStatement.setString(1, name);
            insertStatement.setInt(2, score);
            insertStatement.setInt(3, score);
            insertStatement.executeUpdate();
        }
    }

    /**
     * Egy Pontszám törlése
     *
     * @param score Törlendő pontszám
     * @throws SQLException SQL kivétel
     */
    private void deleteScores(int score) throws SQLException {
        deleteStatement.setInt(1, score);
        deleteStatement.executeUpdate();
    }
}


