/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Fall 2023
 * Instructor: Prof. Brian King / Prof. Joshua Stough
 *
 * Name: William Choi
 * Section: CSCI 205 10:00AM-10:50AM
 * Date: 10/12/2023
 * Time: 1:17 PM
 *
 * Project: csci205_labs
 * Package: hw01
 * Class: codeBreaker
 *
 * Description:
 * Class representation of the code breaker in the game Mastermind
 * Stores player actions like getting a guess provision
 *
 * ****************************************
 */
package hw01;

import java.util.Scanner;

public class codeBreaker {

    board playBoard;
    String guess;

    Scanner scnr = new Scanner(System.in);

    /**
     * Constructor method for codeBreaker
     *
     * @param inputBoard
     */
    public codeBreaker(board inputBoard) {
        this.playBoard = inputBoard;
        this.guess = "";
    }

    /**
     * Uses a scanner object to take guess from the command line
     * CHECK GUESS NUMBER HERE
     */
    public void catchGuess(String input) throws gameException {
        if (this.playBoard.GAME_STATE == GAME_STATES.BREAKER_TURN){
            this.guess = input; //IMPORTANT LINE
            if (!(Integer.parseInt(this.guess) >= 1000 && Integer.parseInt(this.guess) < 10000)){
                String msg = "ILLEGAL ARGUMENT GIVEN";
                throw new gameException(msg);
            }
        }
        else{
            String msg = "METHOD CALL IN WRONG STATE";
            throw new gameException(msg);
        }
        if(this.playBoard.guessesRemaining == 0){
            this.playBoard.GAME_STATE = GAME_STATES.GAME_COMPLETE;
        }
    }

    /**
     * Puts coding pegs into the board object
     */
    public void placeCodingPegs() throws gameException {
        if (playBoard.GAME_STATE == GAME_STATES.BREAKER_TURN){
            this.playBoard.codePegs = this.guess;
            this.playBoard.GAME_STATE = GAME_STATES.MAKER_TURN;
        }
        else {
            String msg = "METHOD CALL IN WRONG STATE";
            throw new gameException(msg);
        }
    }

}
