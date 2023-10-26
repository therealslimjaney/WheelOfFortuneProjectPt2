import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * The MastermindGame class represents a Mastermind guessing game.
 * This class extends the GuessingGame class and implements the game mechanics.
 */
public class MastermindGame extends GuessingGame {
    private StringBuilder guessSB;
    private int exacts;
    private int partials;

    char[] letterList = {'R', 'G', 'B', 'Y', 'O', 'P'};

    /**
     * Constructs a new instance of the MastermindGame game
     * Initializes the game by creating a StringBuilder for storing the player's guesses and sets the secret code.
     */
    public MastermindGame() {
        guessSB = new StringBuilder();
        this.secret = new StringBuilder();
    }

    /**
     * Generates and returns a secret sequence of colors as a StringBuilder.
     * Ensures that there are no repeating colors in the sequence.
     *
     * @return A StringBuilder representing the secret code.
     */
    private StringBuilder getSecretSB() {
        Random rand = new Random();
        StringBuilder secret = new StringBuilder(0);
        while (secret.length() < 4) {
            int randomIndex = rand.nextInt(6);
            if (secret.indexOf(String.valueOf(letterList[randomIndex])) == -1) { // Ensure no repeating colours
                secret.append(letterList[randomIndex]);
            }
        }
        return secret;
    }

    /**
     * Prompts the player to enter a guess and stores it in the guessSB StringBuilder.
     */
    public void getGuess() {
        guessSB.setLength(0);
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nGuess a sequence of 4 colours, e.g GBOY: ");
        String stringGuess = scanner.next().toUpperCase();
        guessSB.append(stringGuess);
    }

    /**
     * Processes the current guess by calculating the number of exact matches and partial matches.
     * Decrements the remaining guesses.
     */
    @Override
    public void processGuess() {
        exacts = checkExacts();
        partials = checkPartials();
        guessesRemaining--;
    }

    /**
     * Checks and returns the number of exact matches between the secret colour sequence and the player's guess.
     *
     * @return The number of exact matches.
     */
    private int checkExacts() {
        int exacts = 0;
        for (int i = 0; i < 4; i++) {
            if (secret.charAt(i) == guessSB.charAt(i)) {
                exacts++;
            }
        }
        return exacts;
    }

    /**
     * Checks and returns the number of partial matches between the secret colour sequence and the player's guess.
     *
     * @return The number of partial matches.
     */
    private int checkPartials() {
        int partials = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i != j) {
                    if (secret.charAt(i) == (guessSB.charAt(j))) {
                        partials++;
                    }
                }
            }
        }
        return partials;
    }

    /**
     * Checks if the player has won the game by correctly guessing the secret colour sequence.
     *
     * @return true if the player has guessed the secret colour sequence, false otherwise.
     */
    public boolean processWin() {
        return secret.toString().equals(guessSB.toString());
    }

    /**
     * Displays information about the current game round, including the round number, exact matches,
     * partial matches, and remaining guesses.
     */
    public void displayGameInfo() {
        System.out.println("\n<---ROUND " + roundCounter + "--->\nExact matches: " + exacts + "\nPartial Matches: " + partials + "\nGuesses remaining: " + guessesRemaining);
    }

    /**
     * Displays mastermind specific game instructions to the player
     */
    public void displayGameInstructions() {
        System.out.println("\n=================== Welcome to the Mastermind Game! ===================\nObjective: Try to guess the hidden sequences of 4 colours (NOTE: There are no repeating colours).\nInstructions:\n1. Enter 4 letters representing colours: \n'R' - Red \n'G' - Green \n'B' - Blue \n'Y' - Yellow, \n'O' - Orange, \n'P' - Purple.\n2. You will receive feedback after each guess as to exact and partial matches.\n3. You can make up to " + guessesRemaining + " incorrect guesses.\n4. Keep guessing until you either guess the sequence correctly or run out of guesses.\n -GOOD LUCK!");
    }

    /**
     * Displays the game's summary, including the secret colour sequence and the player's score.
     */
    public void displayGameResult() {
        System.out.println("<----GAME SUMMARY---->\nHidden colour sequence: " + secret + "\nYour score: " + guessesRemaining);
    }

    /**
     * Resets the game for a new round by generating a new secret colour sequence and resetting the remaining guesses.
     */
    public void loadNewGame() {
        secret = getSecretSB();
        guessesRemaining = 5;
    }

    /**
     * Asks the player if they want to play another game and returns their choice.
     *
     * @return true if the player wants to play another game, false otherwise.
     */
    @Override
    public boolean playNext() {
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
     * Returns a string representation of the MastermindGame object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "MastermindGame{" +
                "guessSB=" + guessSB +
                ", exacts=" + exacts +
                ", partials=" + partials +
                ", letterList=" + Arrays.toString(letterList) +
                '}';
    }

    /**
     * Checks if this MastermindGame object is equal to another object.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MastermindGame that = (MastermindGame) o;
        return exacts == that.exacts && partials == that.partials && Objects.equals(guessSB, that.guessSB) && Arrays.equals(letterList, that.letterList);
    }

    /**
     * Returns the hash code value for the MastermindGame object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(guessSB, exacts, partials);
        result = 31 * result + Arrays.hashCode(letterList);
        return result;
    }

    /**
     * The main method of the MastermindGame class, which initiates and manages the game and displays the results.
     *
     * @param args The command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        MastermindGame game = new MastermindGame();
        AllGamesRecord record = game.playAll();
        System.out.println("\nThanks for playing.");
        // Display highGameList (for two games)
        System.out.println("\nHigh Game List for 2 games:");
        System.out.println(record.highGameList(2));

        // Display average of games
        System.out.println("\nAverage for 2 games:");
        System.out.println(record.average());
    }
}
