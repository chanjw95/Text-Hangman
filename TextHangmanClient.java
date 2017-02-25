import java.io.*;

/**
 * A class that implements the abstract methods of
 * <code>AbstractHangmanClient</code>
 * <p>
 * It provides a text-based interface as well as a <code>main()</code>
 * method, giving an executable program.
 * 
 * @author Justin Chan
 * @date 9 May 2016
 */

public class TextHangmanClient extends AbstractHangmanClient
{
  private BufferedReader keyboard = new BufferedReader
    (new InputStreamReader(System.in));
  /**
   * The constructor for the <code>TextHangmanClient</code> class.
   * @param debugging True iff debugging output is enabled.
   * @param serverName The host on which the hangman server resides
   * @param portNumber The port number on which the server is
   * listening
   */
  public TextHangmanClient(boolean debugging, String serverName, int portNumber) {
    super(debugging,serverName,portNumber);
  }
  /**
   * Obtain a guess from the user.
   * @return an uppercase letter for a GUESS
   */
  public char elicitGuess() throws IOException{
    System.out.print("Letter? ");
    String guessedString = keyboard.readLine();
    char[] guessedArray = guessedString.toCharArray();
    char guess = guessedArray[0];
    if(Character.isLetter(guess))
      guess = Character.toUpperCase(guess);
    else
      guess = elicitGuess();
    return guess;
  }
  /** 
   * Display the current game state
   */
  public void displayGame() {
    System.out.println("Word: " + wordSoFar);
    System.out.println("Guesses remaining: " + guessesRemaining);
  }
  /**
   * Congratulate the winner on their acument.
   * @param answer The correct answer of the game
   */
  public void congratulateWinner(String answer) {
    System.out.println("Word was: " + answer);
    System.out.println("Congratulations! You got the word!");
  }
  /**
   * Player did not guess the correct word.
   * @param answer The correct answer of the game.
   */
  public void punishLoser(String answer) {
    System.out.println("Sorry! Too many guesses!");
    System.out.println("Word was: " + answer);
    System.out.println("Hangman ... take a few \"practice swings\" (heh, heh).");
  }
  /**
   * Find out whether the player wants to play again.
   */
  public boolean elicitPlayAgain() throws IOException {
    System.out.print("Another game (Y/N)? ");
    String response = keyboard.readLine();
    response = response.toUpperCase();
    char responseChar = response.charAt(0);
    if(responseChar == 'Y')
      return true;
    if(responseChar == 'N')
      return false;
    else {
      boolean trueResponse = elicitPlayAgain();
      return trueResponse;
    }
  }
  /** 
   * The usual main() function.
   * After parsing the command line, it invokes the constructor for
   * this class.
   * <ul>
   * <li> Flag -d: enable debug output</li>
   * <li> Flag -h: print help message</li>
   * </ul>
   */
  public static void main(String args[]) {
    String server = HANGMAN_DEFAULT_SERVER;
    boolean debugging = false;
    int currentArg = 0;

    if(args.length > 0)
      if(args[0].charAt(0) == '-') {
	currentArg++;
	switch(args[0].charAt(1)) {
	case 'd':
	  debugging = true;
	  break;
	case 'h':
	  usage();
	  System.exit(0);
	default: //should not get
	  usage();
	  System.exit(1);
	}
      }

    if(args.length > currentArg)
      server = args[currentArg];

    TextHangmanClient game = new TextHangmanClient(debugging,server,HANGMAN_DEFAULT_PORT);
    game.handleSession();
  }
  /**
   * Print usage message
   */
  public static void usage() {
    System.err.println("Usage: HangmanServer [-d] [-h] [server]");
    System.err.println("   -d: print debugging info");
    System.err.println("   -h: print this help msg");
  }
}
