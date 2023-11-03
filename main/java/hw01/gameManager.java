/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Fall 2023
 * Instructor: Prof. Brian King / Prof. Joshua Stough
 *
 * Name: William Choi
 * Section: CSCI 205 10:00AM-10:50AM
 * Date: 10/12/2023
 * Time: 1:16 PM
 *
 * Project: csci205_labs
 * Package: hw01
 * Class: gameManager
 *
 * Description:
 * Program that manages the runtime operations of Mastermind
 *
 * ****************************************
 */
package hw01;

import java.util.Scanner;

/**
 * Checked exception representing that a
 */
class gameException extends Exception {
    public gameException(String message) {
        super(message);
    }
}

public class gameManager {

    private static board playBoard;
    private static codeMaker codeMaker;
    private static codeBreaker codeBreaker;
    private static Scanner scnr;
    //String input from user to detect if game should be repeated
    private static String repeatGameString;
    private static boolean repeatGameBool = true;

    /**
     * Main method that runs Mastermind game
     *
     * @param args unused
     */
    public static void main(String[] args) throws gameException {
        //CORE GAMEPLAY LOOP
        while(repeatGameBool == true) {
            //Instantiates the object representing the Mastermind Board
            playBoard = new board();
            //Instantiates the object representing the player in the role of code maker
            codeMaker = new codeMaker(playBoard);
            //Instantiates the object representing the player in the role of code breaker
            codeBreaker = new codeBreaker(playBoard);
            //Scanner object to take user input from the command line
            scnr = new Scanner(System.in);
            //Boolean to detect if game should be repeated
            repeatGameBool = true;
            //Initializes the GAME_STATE of the board to GAME_INITIALIZED to signal instantiation of necessary objects
            playBoard.GAME_STATE = GAME_STATES.GAME_INITIALIZED;
            //Generates solution code for game
            generateGameSolution();

            //Looping the guessing and checking loop between the breaker and the maker
            while (playBoard.GAME_STATE == GAME_STATES.BREAKER_TURN || playBoard.GAME_STATE == GAME_STATES.MAKER_TURN) {
                //BREAKERS TURN NOW
                if (playBreakerTurn()) break;

                //MAKERS TURN NOW
                playMakerTurn();
            }
            //Win message
            displayWinLossMsg();

            //Prompts the user if they want to play another game
            prompReplay();
        }
        //Messages that plays once the main method is killed
        System.out.println("Goodbye!");
    }

    /**
     * Asks the user if they would like to replay the game one
     */
    private static void prompReplay() {
        System.out.println("Would you like to play again? [Y/N]");
        repeatGameString = scnr.next().toLowerCase();
        if (repeatGameString.equals("y")) {
            repeatGameBool = true;
            playBoard.guessesRemaining = 12;
        } else if (repeatGameString.equals("n")) {
            repeatGameBool = false;
        }
    }

    private static void displayWinLossMsg() {
        if (playBoard.GAME_STATE == GAME_STATES.GAME_COMPLETE && playBoard.scorePegs.equals("****")){
            System.out.println(" YOU WON! You guessed the code in 7 moves.");
        }
        else{
            System.out.println("YOU LOST! You ran out of turns.");
        }
    }

    private static void playMakerTurn() throws gameException {
        codeMaker.checkGuess(playBoard.codePegs);
        codeMaker.placeScorePegs();
        System.out.print(codeBreaker.guess + " -->  " + codeMaker.playBoard.scorePegs);
        if (playBoard.GAME_STATE == GAME_STATES.BREAKER_TURN || playBoard.GAME_STATE == GAME_STATES.MAKER_TURN){
            System.out.println("   Try again. " + codeMaker.playBoard.guessesRemaining + " guesses left.");
        }
    }

    private static boolean playBreakerTurn() throws gameException {
        System.out.print("Guess   " + codeBreaker.playBoard.guessesRemaining + ":  "    );
        String guess = scnr.nextLine();
        try {
            codeBreaker.catchGuess(guess);
        }
        catch (gameException e){
            System.err.println("ERROR");
            return true;
        }
        if (playBoard.GAME_STATE == GAME_STATES.GAME_COMPLETE) {
            return true;
        }
        codeBreaker.placeCodingPegs();
        return false;
    }

    private static void generateGameSolution() throws gameException {
        System.out.println("Guess my code, using numbers between 0 and 9 (inclusive). You have 12 guesses.");
        codeMaker.generateCode();
        codeMaker.resetBoard();
    }
}