import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/**
 * The `AllGamesRecord` class represents a collection of `GameRecord` objects and provides various methods
 * for managing and analyzing game records.
 */
public class AllGamesRecord {

    /**
     * Constructs a new `AllGamesRecord` object with an empty list of game records.
     */
    private ArrayList<GameRecord> gameRecords;

    public AllGamesRecord() {

        gameRecords = new ArrayList<GameRecord>();
    }

    /**
     * adds a GameRecord to the AllGamesRecord object.
     * @param record The GameRecord to be added to the ArrayList
     */
    public void addGameRecord(GameRecord record) {

        gameRecords.add(record);
    }

    /**
     * Calculates and returns the average score for all games stored in the record.
     *
     * @return The average score of all games in the record.
     */
    public int average() {
        int sum = 0;
        for (GameRecord record: gameRecords) {
            sum += record.score;
        }
        return sum/gameRecords.size();
    }

    /**
     * Calculates and returns the average score for all games played by a specific player.
     *
     * @param id playerId of the player.
     * @return The average score of games played by the specified player.
     */
    public int average(String id) {
        int sum = 0;
        int gamesPlayed = 0;
        for (GameRecord record: gameRecords) {
            if (record.playerId.equals(id)) {
                sum += record.score;
                gamesPlayed++;
            }
        }
        return sum/gamesPlayed;
    }

    /**
     * Returns a sorted list of the top 'n' scores
     * This method sorts the game records using the Collections class.
     *
     * @param n The number of top scores to include in the list.
     * @return A list of the top 'n' GameRecord instances sorted by score.
     */
    public List<GameRecord> highGameList(int n) {
        ArrayList<GameRecord> list = new ArrayList<>(this.gameRecords);
        Collections.sort(list, Collections.reverseOrder());
        return list.subList(0, Math.min(n, list.size())); // Ensure 'n' doesn't exceed the list size.
    }

    /**
     * Returns a sorted list of the top 'n' scores for the specified player.
     * This method sorts the game records using the Collections class.
     *
     * @param id playerId of the player.
     * @param n The number of top scores to include in the list.
     * @return A list of the top 'n' GameRecord instances.
     */
    public List<GameRecord> highGameList(String id, int n) {
        ArrayList<GameRecord> list = new ArrayList<>();
        for (GameRecord record : gameRecords) {
            if (record.playerId.equals(id)) {
                list.add(record);
            }
        }
        Collections.sort(list, Collections.reverseOrder());
        return list.subList(0, Math.min(n, list.size()));
    }

    @Override
    public String toString() {
        return "AllGamesRecord{" +
                "gameRecords=" + gameRecords +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameRecords);
    }
}