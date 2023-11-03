/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Fall 2023
 * Instructor: Prof. Brian King / Prof. Joshua Stough
 *
 * Name: William Choi
 * Section: CSCI 205 10:00AM-10:50AM
 * Date: 10/12/2023
 * Time: 2:45 PM
 *
 * Project: csci205_labs
 * Package: hw01
 * Class: codeMaker
 *
 * Description:
 * Class representation of the code maker in the game Mastermind.
 * Stores player actions like code generation and code checking.
 *
 * ****************************************
 */
package hw01;
import java.util.Random;

public class codeMaker {

   String solution;
   board playBoard;
   int redPegCount;
   int whitePegCount;
   Random random = new Random();

    public codeMaker(board inputBoard){
        this.solution = "";
        this.playBoard = inputBoard;
        this.redPegCount = 0;
        this.whitePegCount = 0;
    }
    /**
     * Generates a four-digit code at the beginning of a Mastermind game.
     * Stores solution as instance variable solution
     *
     * referenced number generation from this https://stackoverflow.com/.
     *
     * @see
     * <a href="https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java">...</a>
     *
     * @return String code, can be any number between 999 and 10000 (inclusive)
     */
    public void generateCode() throws gameException {
        if (playBoard.GAME_STATE == GAME_STATES.GAME_INITIALIZED || playBoard.GAME_STATE == GAME_STATES.GAME_COMPLETE) {
            playBoard.GAME_STATE = GAME_STATES.BREAKER_TURN;
            String newCode = "" + random.nextInt(10000);
            while (Integer.parseInt(newCode) < 1000){
                newCode = "" + random.nextInt(10000);
            }
            solution = newCode;
        }
        else {
            String msg = "METHOD CALL IN WRONG STATE";
            throw new gameException(msg);
        }
    }

    /**
     * Generates a string representing the score pegs
     * for valid length and digits
     *
     * CHECK FOR ROUND NUMBER HERE
     *
     * @param guess
     * @return String representing the state of number of red and white pegs
     */
    public void checkGuess(String guess) throws gameException {
        if (playBoard.GAME_STATE == GAME_STATES.MAKER_TURN) {
            //Reset count from last guess
            this.redPegCount = 0;
            this.whitePegCount = 0;

            //Checks for red pegs (turn into a helper method later)
            for (int i = 0; i < 4; i++) {
                if (guess.charAt(i) == solution.charAt(i)) {
                    this.redPegCount++;
                }
            }

            //Checks for white pegs (turn into a helper method later)
            char workingDigit = ' ';
            for (int i = 0; i < guess.length(); i++) {
                //Pulls a character from th i'th index of guess
                workingDigit = guess.charAt(i);
                for (int j = 0; j < solution.length(); j++) {
                    if (workingDigit == solution.charAt(j)) {
                        this.whitePegCount++;
                        break;
                    }
                }
            }

            //Removes potential overlap
            this.whitePegCount = this.whitePegCount - this.redPegCount;
        }
        else {
            String msg = "METHOD CALL IN WRONG STATE";
            throw new gameException(msg);
        }
    }

    /**
     * Puts a string representation of the score pegs into the board object
     */
    public void placeScorePegs() throws gameException {
        if (playBoard.GAME_STATE == GAME_STATES.MAKER_TURN) {
            //Ticks down guess
            playBoard.guessesRemaining-=1;

            //Add red pegs
            String scorePegsRep = "";
            for (int i = 0; i < this.redPegCount; i++) {
                scorePegsRep = scorePegsRep + "*";
            }
            //Add white pegs
            for (int i = 0; i < this.whitePegCount; i++) {
                scorePegsRep = scorePegsRep + "+";
            }
            //Add empty spots
            for (int i = scorePegsRep.length(); i < 4; i++){
                scorePegsRep = scorePegsRep + "-";
            }
            //Stores score peg representation in board object
            this.playBoard.scorePegs = scorePegsRep;

            //Turns over GAME_STATE to break
            this.playBoard.GAME_STATE = GAME_STATES.BREAKER_TURN;

            //Overrides last line if solution is found
            if (scorePegsRep.equals("****")) {
                this.playBoard.GAME_STATE = GAME_STATES.GAME_COMPLETE;
            }
        }
        else{
            String msg = "METHOD CALL IN WRONG STATE";
            throw new gameException(msg);
        }
    }

    /**
     * Clears score pegs and code pegs for the board to prepare
     * for another round of play or another game
     *
     * Can be called in any state
     */
    public void resetBoard() {
        this.playBoard.codePegs = "";
        this.playBoard.scorePegs = "";
    }

}