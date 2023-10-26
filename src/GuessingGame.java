/**
 * Abstract class for a guessing game.
 */
abstract class GuessingGame {

    protected StringBuilder secret;
    protected int guessesRemaining = 5;
    protected int roundCounter;

    /**
     * Plays a series of games, records the results, and returns an AllGamesRecord object summarizing the set.
     *
     * @return An AllGamesRecord object containing records of all the games played.
     */
    public AllGamesRecord playAll() {
        AllGamesRecord allGamesRecord = new AllGamesRecord();
        while (playNext()) {
            GameRecord game = this.play();
            allGamesRecord.addGameRecord(game);
        }
        return allGamesRecord;
    }

    /**
     * Plays a guessing game and returns a GameRecord representing the game's outcome.
     *
     * @return A GameRecord containing the score and playerId.
     */
    public GameRecord play() {
        GameRecord record = new GameRecord();
        record.playerId = "user";
        displayGameInstructions();
        while (guessesRemaining >= 0) {
            if (guessesRemaining == 0 || processWin()) {
                break;
            } else {
                displayGameInfo();
                getGuess();
                processGuess();
                roundCounter++;
            }
        }
        record.score = guessesRemaining;
        displayGameResult();
        return record;
    }

    /**
     * Checks if the next game should be played.
     *
     * @return True if the next game should be played, false otherwise.
     */
    public abstract boolean playNext();

    /**
     * Gets a user guess.
     */
    public abstract void getGuess();

    /**
     * Checks if the game is won.
     *
     * @return True if the game is won, false otherwise.
     */
    public abstract boolean processWin();

    /**
     * Processes a user's guess.
     */
    public abstract void processGuess();

    /**
     * Displays information about the game.
     */
    public abstract void displayGameInfo();

    /**
     * Displays game instructions to the user.
     */
    public abstract void displayGameInstructions();

    /**
     * Resets the game to its initial state with a new round.
     */
    public abstract void loadNewGame();

    /**
     * Displays the result of a completed game.
     */
    public abstract void displayGameResult();

    @Override
    public String toString() {
        return "Game{}";
    }

}