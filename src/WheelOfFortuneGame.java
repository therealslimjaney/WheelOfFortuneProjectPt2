import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * The WheelOfFortuneGame class represents a game where the player guesses a hidden phrase by suggesting letters.
 * This class extends the GuessingGame class and manages the game mechanics.
 */
class WheelOfFortuneGame extends GuessingGame {
    protected String phrase;
    protected StringBuilder previousGuesses;
    protected ArrayList<String> phraseList;
    protected char guess;

    /**
     * Constructs a new instance of the WheelOfFortune game.
     * Initializes the game by reading phrases from a file, initializing previousGuesses, and setting up the initial round.
     */
    public WheelOfFortuneGame() {
        phraseList = readPhrases();
        previousGuesses = new StringBuilder();
    }

    /**
     * Reads a list of phrases from a file and returns them as an ArrayList of strings.
     *
     * @return An ArrayList containing the phrases read from the file.
     */
    private ArrayList<String> readPhrases() {
        List<String> phraseList = null;
        // Get the phrase from a file of phrases
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (
                IOException e) {
            System.out.println(e);
        }
        return new ArrayList<>(phraseList);
    }

    /**
     * Generates a random index to return an element of a phraseList and removes that element from phraseList
     *
     * @return a phrase
     */
    public String randomPhrase() {
        int x; // The index of the phrase to return
        if (phraseList.size() == 1) {
            x = 0;
        } else {
            Random rand = new Random();
            x = rand.nextInt(phraseList.size());
        }
        String phrase = phraseList.get(x);
        phraseList.remove(x);
        return phrase;
    }

    /**
     * Generates the initial hidden phrase before the user has made any guesses, replacing letters in the original
     * phrase with asterisks (*)
     *
     * @param phrase the original phrase to obscure with asterisks
     * @return the hidden phrase with letters replaced by asterisks
     */
    public StringBuilder getHiddenPhrase(String phrase) {
        StringBuilder initialCode = new StringBuilder();
        for (int i = 0; i < phrase.length(); i++) {
            if (Character.isLetter(phrase.charAt(i))) {
                initialCode.append("*");
            } else {
                initialCode.append(phrase.charAt(i));
            }
        }
        return initialCode;
    }

    /**
     * Determines if a guess is a match and calls the appropriate method
     *
     * @return void
     */
    public void processGuess() {
        String stringGuess = String.valueOf(this.guess);
        if (this.phrase.contains(stringGuess.toLowerCase()) || this.phrase.contains(stringGuess.toUpperCase())) {
            processCorrect();
        } else {
            processIncorrect();
        }
    }

    /**
     * Updates previousGuesses with both lowercase and uppercase and calls updateHiddenPhrase
     */
    public void processCorrect() {
        // must call update hidden phrase
        this.previousGuesses.append(this.guess);
        this.previousGuesses.append(Character.toUpperCase(this.guess));
        updateHiddenPhrase();
    }

    /**
     * Updates previousGuesses with both lowercase and uppercase
     */
    public void processIncorrect() {
        this.previousGuesses.append(this.guess);
        this.previousGuesses.append(Character.toUpperCase(this.guess));
        this.guessesRemaining--;
    }

    /**
     * Updates the secret phrase to reflect the current correct guesses made
     * The method iterates through each character in the 'phrase', checks if it's a letter, and if it has been guessed.
     */
    public void updateHiddenPhrase() {
        this.secret.setLength(0); //clear existing content of the hiddenPhrase StringBuilder
        for (int i = 0; i < this.phrase.length(); i++) {
            char currentChar = this.phrase.charAt(i);
            if ((Character.isLetter(currentChar)) && (this.previousGuesses.indexOf(String.valueOf(currentChar))) == -1) { // If the character is a letter and it hasn't been guessed yet obscure with *
                this.secret.append("*");
            } else {
                this.secret.append(this.phrase.charAt(i));
            }
        }
    }

    /**
     * Resets the game state for a new round, including previousGuesses, roundCounter, guessesRemaining,
     * the phrase, and the hidden phrase.
     */
    @Override
    public void loadNewGame() {
        previousGuesses.setLength(0);
        roundCounter = 0;
        guessesRemaining = 5;
        phrase = randomPhrase();
        secret = getHiddenPhrase(phrase);
    }

    /**
     * Displays wheel of fortune specific game instructions to the player
     */
    @Override
    public void displayGameInstructions() {
        System.out.println("\n=================== Welcome to the Wheel of Fortune Game! ===================\nObjective: Try to guess the hidden phrase by suggesting letters one at a time.\nInstructions:\n1. Enter a letter to make a guess (e.g., \"a\" or \"B\").\n2. You will receive feedback after each guess.\n3. You can make up to " + guessesRemaining + "  incorrect guesses.\n4. Keep guessing until you either guess the phrase correctly or run out of guesses.\n -GOOD LUCK!");
    }

    /**
     * Prints the current state of the game for player
     */
    @Override
    public void displayGameInfo() {
        System.out.println("\n<---ROUND " + roundCounter + "--->\nPhrase:\t" + secret + "\nPrevious Guesses: " + previousGuesses + "\n#Guesses Remaining: " + guessesRemaining);
    }

    /**
     * Displays the game's summary, including the secret phrase and the player's score.
     */
    public void displayGameResult() {
        System.out.println("<----GAME SUMMARY---->\nPhrase:\t" + secret + "\nScore: " + guessesRemaining + " (defined as guesses remaining)");
    }

    /**
     * Prompts the player to enter a letter as a guess and validates the input to ensure it is a single letter.
     * The chosen letter is stored as the current guess.
     */
    @Override
    public void getGuess() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nGuess a letter: ");
        String stringGuess = scanner.next().toLowerCase();
        char charGuess = 0;

        if (stringGuess.length() == 1) {
            charGuess = stringGuess.charAt(0);
        } else {
            System.out.println("Please enter a single letter as your guess.");
            getGuess(); // Recursively call the method to get a valid guess
        }

        if (previousGuesses.indexOf(String.valueOf(charGuess)) != -1) {
            System.out.println("You have already guessed '" + charGuess + "'");
            getGuess(); // Recursively call the method to get a new guess
        }

        guess = charGuess;
    }

    /**
     * Checks if the player has guessed the entire phrase and won the game.
     *
     * @return true if the phrase has no asterisks, indicating that it has been entirely guessed, false otherwise.
     */
    @Override
    public boolean processWin() {
        return secret.indexOf("*") == -1;
    }

    /**
     * Determines if the user wants to play another game and resets the game state if necessary.
     *
     * @return boolean true if the user wants to play another game, false otherwise.
     */
    @Override
    public boolean playNext() {
        if (phraseList.isEmpty()) {
            System.out.println("\nSorry, we are all out of game phrases.\n"); // Stop the game if the phraseList is empty.
            return false;
        }
        System.out.println("\nPlay another game? Enter 'y' or 'n': "); // Need error handling here
        Scanner scanner = new Scanner(System.in);
        String playNext = scanner.next().toLowerCase();
        if (playNext.equals("y")) {
            loadNewGame();
            return true;
        }
        return false;
    }

    /**
     * Returns a string representation of the WheelOfFortuneGame object.
     */
    @Override
    public String toString() {
        return "WheelOfFortuneGame{" +
                "phrase='" + phrase + '\'' +
                ", previousGuesses=" + previousGuesses +
                ", phraseList=" + phraseList +
                ", guess=" + guess +
                '}';
    }

    /**
     * Checks if this WheelOfFortuneGame object is equal to another object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WheelOfFortuneGame that = (WheelOfFortuneGame) o;
        return guess == that.guess && Objects.equals(phrase, that.phrase) && Objects.equals(previousGuesses, that.previousGuesses) && Objects.equals(phraseList, that.phraseList);
    }

    /**
     * Returns the hash code value for the WheelOfFortuneGame object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(phrase, previousGuesses, phraseList, guess);
    }

    /**
     * The main method to start and play the Wheel of Fortune game with human user inputs
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        WheelOfFortuneGame wofGame = new WheelOfFortuneGame();
        AllGamesRecord allGamesRecord = wofGame.playAll();

        // Display AllGamesRecord object
        System.out.println("\nAll games record object: ");
        System.out.println(allGamesRecord);

        //Display High Game List for 2 games
        System.out.println("\nHigh game list for 2 games");
        System.out.println(allGamesRecord.highGameList(2));

        //Display average of games all games in AllGamesRecord object
        System.out.println("\nAverage of games:");
        System.out.println(allGamesRecord.average());
    }
}
