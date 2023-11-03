/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2023
 * Instructor: Professor Stough
 * Section: MWF, 10:00AM - 10:50AM
 * Lab Section: Thursdays, 1PM - 2:50PM
 *
 * Name: William Choi
 * Date: 09/26/2023
 *
 * Lab / Assignment: lab07
 *
 * Description: A file containing the test methods for codeMaker.java
 *
 * *****************************************/

package hw01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class codeMakerTest {

    private codeMaker codeMakerTest;

    private codeBreaker codeBreakerTest;

    @BeforeEach
    void setUp() {
        board playboard = new board();
        this.codeMakerTest = new codeMaker(playboard);
        this.codeBreakerTest = new codeBreaker(playboard);

        playboard.GAME_STATE = GAME_STATES.GAME_INITIALIZED;
    }

    /**
     * Test method for generateCode() method in codeMaker
     *
     * referenced code from https://freecodecamp.org
     * @see
     * <a href="https://freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/">...</a>
     *
     * @throws gameException if method is being called in wrong state
     */
    @Test
    void generateCode() throws gameException {
        //Check that solution is initialized to ""
        assertEquals("", this.codeMakerTest.solution);

        //Checks that method can only be used when in beginning in GAME_STATE.GAME_INITIALIZED
        assertEquals(GAME_STATES.GAME_INITIALIZED, this.codeMakerTest.playBoard.GAME_STATE);

        //Checks that the solution generated is an integer between 0 - 10000 (inclusive)
        codeMakerTest.generateCode();
        int testCode = Integer.parseInt(codeMakerTest.solution);


        //Checks that GAME_STATE gets changed to BREAKER_TURN
        assertEquals(GAME_STATES.BREAKER_TURN, this.codeMakerTest.playBoard.GAME_STATE);
    }

    /**
     * Test method for checkGuess() in codeMaker
     *
     * @throws gameException if accessing method from wrong state
     */
    @Test
    void checkGuess() throws gameException {
        //Sets up test conditions
        codeBreakerTest.guess = "2111";
        codeMakerTest.solution = "1112";
        codeMakerTest.playBoard.GAME_STATE = GAME_STATES.MAKER_TURN;
        codeMakerTest.checkGuess(codeBreakerTest.guess);

        //Checks that redPegCount is as expected
        assertEquals(2, this.codeMakerTest.redPegCount);

        //Checks that whitePegCount is as expected
        assertEquals(2, this.codeMakerTest.whitePegCount);

        //Checks that method can only be used on in GAME_STATE.MAKERS_TURN
        codeMakerTest.playBoard.GAME_STATE = GAME_STATES.GAME_UNINITIALIZED;
        assertThrows(gameException.class, () -> codeMakerTest.checkGuess(codeBreakerTest.guess));
    }

    /**
     * Test method for placeScorePegs in codeMaker
     *
     * @throws gameException if accessing placeScorePegs() in wrong state
     */
    @Test
    void placeScorePegs() throws gameException {
        //Sets up starting conditions
        codeBreakerTest.guess = "2111";
        codeMakerTest.solution = "1112";
        codeMakerTest.playBoard.GAME_STATE = GAME_STATES.MAKER_TURN;
        codeMakerTest.checkGuess(codeBreakerTest.guess);

        //Checks guesses at the beginning of the game is 12
        assertEquals(12, codeMakerTest.playBoard.guessesRemaining);

        //Checks that guess counter is being correctly decremented
        codeMakerTest.placeScorePegs();
        assertEquals(11, codeMakerTest.playBoard.guessesRemaining);

        //Checks that scorePegRep is as expected
        assertEquals("**++", this.codeMakerTest.playBoard.scorePegs);

        //Check that GAME_STATE changes to GAME_STATE.GAME_COMPLETE after getting a fully correct guess
        codeBreakerTest.guess = "1112";
        codeMakerTest.solution = "1112";
        codeMakerTest.playBoard.GAME_STATE = GAME_STATES.MAKER_TURN;
        codeMakerTest.checkGuess(codeBreakerTest.guess);
        codeMakerTest.placeScorePegs();
        assertEquals(GAME_STATES.GAME_COMPLETE, this.codeMakerTest.playBoard.GAME_STATE);
    }

    /**
     * Test method for resetBoard() in codeMaker
     */
    @Test
    void resetBoard() {
        codeMakerTest.playBoard.codePegs = "1234";
        codeMakerTest.playBoard.scorePegs = "+---";

        //Checks that the scorePegs and codePegs are both "" after method call
        codeMakerTest.resetBoard();
        assertEquals("", codeMakerTest.playBoard.codePegs);
        assertEquals("", codeMakerTest.playBoard.scorePegs);
    }
}