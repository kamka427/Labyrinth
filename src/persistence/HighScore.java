package persistence;

import java.util.Objects;

public class HighScore {
    public final String id;
    public final int size;
    public final int completed;


    public HighScore(String id, int size, int completed){
        this.id = id;
      this.size = size;
      this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HighScore highScore = (HighScore) o;
        return size == highScore.size && completed == highScore.completed && Objects.equals(id, highScore.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, completed);
    }

    @Override
    public String toString() {
        return "HighScore{" +
                "id='" + id + '\'' +
                ", size=" + size +
                ", completed=" + completed +
                '}';
    }
}
