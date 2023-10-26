import java.util.Objects;

/**
 * The `GameRecord` class represents a game record with game statistics
 * It implements the `Comparable` interface for sorting based on the score.
 */
public class GameRecord implements Comparable<GameRecord> {

    protected int score; // Score for a guessing game is defined as the number of guesses remaining)
    protected String playerId;

    /**
     * Compares this GameRecord to another GameRecord for sorting purposes based on their scores.
     *
     * @param o The GameRecord to compare to this GameRecord.
     * @return A positive integer if this GameRecord's score is greater than 'o', a negative integer if it's less, or 0 if they have equal scores.
     */
    @Override
    public int compareTo(GameRecord o) {
        if (this.score > o.score) {
            return 1;
        } else if (this.score < o.score) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "score=" + score +
                ", playerId='" + playerId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRecord that = (GameRecord) o;
        return score == that.score && Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, playerId);
    }
}