/* ****************************************
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
 * Class: board
 *
 * Description:
 * Class representation of the board object that the codeMaker and codeBreaker objects are interacting.
 * Stores round number, and representations of code pegs and score pegs
 *
 * ****************************************
 */
package hw01;

enum GAME_STATES{
    GAME_UNINITIALIZED,
    GAME_INITIALIZED,
    MAKER_TURN,
    BREAKER_TURN,
    GAME_COMPLETE

}

public class board {

     int guessesRemaining;
     String codePegs;
     String scorePegs;
     GAME_STATES GAME_STATE;


    public board(){
        this.guessesRemaining = 12;
        this.codePegs = "";
        this.scorePegs = "";
        this.GAME_STATE = GAME_STATES.GAME_UNINITIALIZED;
    }


}