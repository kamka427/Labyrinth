package persistence;


/**
 *
 */
public class HighScore {
    public final String id;
    public final int completed;

    /**
     *
     * @param id
     * @param completed
     */
    public HighScore(String id, int completed){
      this.id = id;
      this.completed = completed;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "HighScore{" +
                "id='" + id + '\'' +
                ", completed=" + completed +
                '}';
    }
}
