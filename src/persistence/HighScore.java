package persistence;

import java.util.Objects;

public class HighScore {
    public final String id;
    public final int completed;


    public HighScore(String id, int completed){
      this.id = id;
      this.completed = completed;
    }



    @Override
    public String toString() {
        return "HighScore{" +
                "id='" + id + '\'' +
                ", completed=" + completed +
                '}';
    }
}
