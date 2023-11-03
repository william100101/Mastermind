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
 * Description: A file containing the test methods for codeBreaker.java
 *
 * *****************************************/
package hw01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class codeBreakerTest {

    private codeBreaker codeBreakerTest;

    @BeforeEach
    void setUp() {
        board playBoard = new board();
        this.codeBreakerTest = new codeBreaker(playBoard);
        this.codeBreakerTest.playBoard.GAME_STATE = GAME_STATES.BREAKER_TURN;
    }

    /**
     * Test method for catchGuess method in codeBreaker
     *
     * @throws gameException if method is called in wrong state
     */
    @Test
    void catchGuess() throws gameException {
        //Checks that alphas are not accepted
        //assertThrows(IllegalArgumentException.class, () -> this.codeBreakerTest.gradeGuess("asdf"));

        //Checks that decimals are not accepted
        //assertThrows(IllegalArgumentException.class, () -> this.codeBreakerTest.gradeGuess("1001.0"));

        //Checks that an integer 1000 or less is not accepted
        assertThrows(gameException.class, () -> this.codeBreakerTest.catchGuess("123"));

        //Checks that an integer 10000 or greater is not accepted
        assertThrows(gameException.class, () -> this.codeBreakerTest.catchGuess("123123"));

        //Check that a correct input works
        this.codeBreakerTest.catchGuess("1234");
        assertEquals("1234", this.codeBreakerTest.guess);

        //Checks that method can only be accessed in GAME_STATE.BREAKER_TURN
        this.codeBreakerTest.playBoard.GAME_STATE = GAME_STATES.GAME_UNINITIALIZED;
        assertThrows(gameException.class, () -> this.codeBreakerTest.catchGuess("1234"));
    }

    /**
     * Test method for placeCodingPegs method in codeBreaker
     *
     * @throws gameException if method is called in wrong state
     */
    @Test
    void placeCodingPegs() throws gameException {
        codeBreakerTest.catchGuess("1234");
        //Checks that correct String representation of code pegs are in the board
        codeBreakerTest.placeCodingPegs();
        assertEquals("1234", codeBreakerTest.playBoard.codePegs);

        //Checks that method can only be called in GAME_STATE.BREAKER_TURN
        codeBreakerTest.playBoard.GAME_STATE = GAME_STATES.GAME_UNINITIALIZED;
        assertThrows(gameException.class, () -> codeBreakerTest.placeCodingPegs());

    }
}