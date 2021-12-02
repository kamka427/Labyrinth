package persistence;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private final Connection connection;
    PreparedStatement insertStatement;
    PreparedStatement deleteStatement;
    int maxScores;

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

        try {
            System.out.println(getHighScores());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        maxScores = 10;

    }

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

    public void putHighScore(String name, int score) throws SQLException {
        ArrayList<HighScore> highScores = getHighScores();
        if (highScores.size() < maxScores) {
            insertScore(name, score);
        } else {
            int leastScore = highScores.get(highScores.size() - 1).completed;
            if (leastScore < score) {
                deleteScores(leastScore);
                insertScore(name, score);
            }
        }
    }

    private void sortHighScores(ArrayList<HighScore> highScores) {
        highScores.sort((a, b) -> b.completed - a.completed);
    }

    private void insertScore(String name, int score) throws SQLException {
        boolean doUpdate = true;


        for (HighScore val: getHighScores()) {
            if(val.id.equals(name))
                doUpdate = (score > val.completed);
        }
        if (doUpdate) {
            insertStatement.setString(1, name);
            insertStatement.setInt(2, score);
            insertStatement.setInt(3, score);
            insertStatement.executeUpdate();
        }
    }

    private void deleteScores(int score) throws SQLException {
        deleteStatement.setInt(1, score);
        deleteStatement.executeUpdate();
    }

}


