package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private final String tableName = "scores";
    private final Connection conn;
    private final HashMap<String, Integer> highScores;

    public Database() {
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/labyrinth", "student", "asd123");
            System.out.println("Siker");
        } catch (Exception ex) {
            System.out.println("No connection");
        }
        this.conn = c;
        highScores = new HashMap<>();
        loadHighScores();
    }

    public boolean storeHighScore(String name, int newScore) {
        return mergeHighScores(name, newScore, newScore > 0);
    }

    public ArrayList<HighScore> getHighScores() {
        ArrayList<HighScore> scores = new ArrayList<>();
        for (String name : highScores.keySet()) {
            HighScore h = new HighScore(name, highScores.get(name));
            scores.add(h);
            System.out.println(h);
        }
        return scores;
    }

    private void loadHighScores() {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            while (rs.next()) {

                String name = rs.getString("name");
                int completed = rs.getInt("score");

                mergeHighScores(name, completed, false);
            }
        } catch (Exception e) {
            System.out.println("loadHighScores error: " + e.getMessage());
        }
    }

    private boolean mergeHighScores(String name, int score, boolean store) {
        System.out.println("Merge: " + name + "-" + ":" + score + "(" + store + ")");

        boolean doUpdate = true;
        if (highScores.containsKey(name)) {
            int oldScore = highScores.get(name);
            doUpdate = ((score < oldScore && score != 0) || oldScore == 0);
        }

        if (doUpdate) {
            highScores.remove(name);
            highScores.put(name, score);
            if (store) return storeToDatabase(name, score) > 0;
        }
        return true;

    }

    public int storeToDatabase(String name, int score) {
        try (Statement stmt = conn.createStatement()) {
            String s = "INSERT INTO " + tableName +
                    " (name, score) " +
                    "VALUES('" + name + "',"
                    + score +
                    ")";
            return stmt.executeUpdate(s);
        } catch (Exception e) {
            System.out.println("storeToDatabase error");
        }
        return 0;
    }

}


