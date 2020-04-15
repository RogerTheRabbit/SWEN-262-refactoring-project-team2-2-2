package LaneState;
/* $Id$
 *
 * Revisions:
 *   $Log: LaneState.Lane.java,v $
 *   Revision 1.52  2003/02/20 20:27:45  ???
 *   Fouls disables.
 *
 *   Revision 1.51  2003/02/20 20:01:32  ???
 *   Added things.
 *
 *   Revision 1.50  2003/02/20 19:53:52  ???
 *   Added foul support.  Still need to update laneview and test this.
 *
 *   Revision 1.49  2003/02/20 11:18:22  ???
 *   Works beautifully.
 *
 *   Revision 1.48  2003/02/20 04:10:58  ???
 *   temp.Score reporting code should be good.
 *
 *   Revision 1.47  2003/02/17 00:25:28  ???
 *   Added disbale controls for View objects.
 *
 *   Revision 1.46  2003/02/17 00:20:47  ???
 *   fix for event when game ends
 *
 *   Revision 1.43  2003/02/17 00:09:42  ???
 *   fix for event when game ends
 *
 *   Revision 1.42  2003/02/17 00:03:34  ???
 *   Bug fixed
 *
 *   Revision 1.41  2003/02/16 23:59:49  ???
 *   Reporting of sorts.
 *
 *   Revision 1.40  2003/02/16 23:44:33  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.39  2003/02/16 23:43:08  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.38  2003/02/16 23:41:05  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.37  2003/02/16 23:00:26  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.36  2003/02/16 21:31:04  ???
 *   temp.Score logging.
 *
 *   Revision 1.35  2003/02/09 21:38:00  ???
 *   Added lots of comments
 *
 *   Revision 1.34  2003/02/06 00:27:46  ???
 *   Fixed a race condition
 *
 *   Revision 1.33  2003/02/05 11:16:34  ???
 *   Boom-Shacka-Lacka!!!
 *
 *   Revision 1.32  2003/02/05 01:15:19  ???
 *   Real close now.  Honest.
 *
 *   Revision 1.31  2003/02/04 22:02:04  ???
 *   Still not quite working...
 *
 *   Revision 1.30  2003/02/04 13:33:04  ???
 *   LaneState.Lane may very well work now.
 *
 *   Revision 1.29  2003/02/02 23:57:27  ???
 *   fix on pinsetter hack
 *
 *   Revision 1.28  2003/02/02 23:49:48  ???
 *   temp.Pinsetter generates an event when all pins are reset
 *
 *   Revision 1.27  2003/02/02 23:26:32  ???
 *   ControlDesk now runs its own thread and polls for free lanes to assign queue members to
 *
 *   Revision 1.26  2003/02/02 23:11:42  ???
 *   parties can now play more than 1 game on a lane, and lanes are properly released after games
 *
 *   Revision 1.25  2003/02/02 22:52:19  ???
 *   LaneState.Lane compiles
 *
 *   Revision 1.24  2003/02/02 22:50:10  ???
 *   LaneState.Lane compiles
 *
 *   Revision 1.23  2003/02/02 22:47:34  ???
 *   More observering.
 *
 *   Revision 1.22  2003/02/02 22:15:40  ???
 *   Add accessor for pinsetter.
 *
 *   Revision 1.21  2003/02/02 21:59:20  ???
 *   added conditions for the party choosing to play another game
 *
 *   Revision 1.20  2003/02/02 21:51:54  ???
 *   temp.LaneEvent may very well be observer method.
 *
 *   Revision 1.19  2003/02/02 20:28:59  ???
 *   fixed sleep thread bug in lane
 *
 *   Revision 1.18  2003/02/02 18:18:51  ???
 *   more changes. just need to fix scoring.
 *
 *   Revision 1.17  2003/02/02 17:47:02  ???
 *   Things are pretty close to working now...
 *
 *   Revision 1.16  2003/01/30 22:09:32  ???
 *   Worked on scoring.
 *
 *   Revision 1.15  2003/01/30 21:45:08  ???
 *   Fixed speling of received in LaneState.Lane.
 *
 *   Revision 1.14  2003/01/30 21:29:30  ???
 *   Fixed some MVC stuff
 *
 *   Revision 1.13  2003/01/30 03:45:26  ???
 *   *** empty log message ***
 *
 *   Revision 1.12  2003/01/26 23:16:10  ???
 *   Improved thread handeling in lane/controldesk
 *
 *   Revision 1.11  2003/01/26 22:34:44  ???
 *   Total rewrite of lane and pinsetter for R2's observer model
 *   Added LaneState.Lane/temp.Pinsetter Observer
 *   Rewrite of scoring algorythm in lane
 *
 *   Revision 1.10  2003/01/26 20:44:05  ???
 *   small changes
 *
 *
 */

import scoring.FrameMediator.ScoreMediator;
import temp.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.Iterator;

public class Lane extends Thread implements PinsetterObserver {
    private Party party;
    private final Pinsetter setter;
    private ScoreMediator scores;
    private final ArrayList<LaneObserver> subscribers;

    private boolean gameIsHalted;

    private boolean gameFinished;
    private Iterator<Bowler> bowlerIterator;
    private int ball;
    private int bowlIndex;
    private int frameNumber;
    private boolean tenthFrameStrike;

    private int[][] cumuliScores;
    private boolean canThrowAgain;

    private int[][] finalScores;
    private int gameNumber;

    private Bowler currentThrower; // = the thrower who just took a throw

    /**
     * LaneState.Lane()
     * <p>
     * Constructs a new lane and starts its thread
     */
    public Lane() {
        setter = new Pinsetter();
        scores = new ScoreMediator();
        subscribers = new ArrayList<>();

        gameIsHalted = false;

        gameNumber = 0;

        setter.subscribe(this);

        this.start();
    }

    /**
     * run()
     * <p>
     * entry point for execution of this lane
     */
    public void run() {
        try {
            while (true) {
                if (party != null && !gameFinished) { // we have a party on this lane,
                    // so next bower can take a throw

                    while (gameIsHalted) {
                        try {
                            sleep(10);
                        } catch (Exception ignored) {
                        }
                    }

                    if (bowlerIterator.hasNext()) {
                        currentThrower = bowlerIterator.next();

                        canThrowAgain = true;
                        tenthFrameStrike = false;
                        ball = 0;
                        while (canThrowAgain) {
                            setter.ballThrown(); // simulate the thrower's ball hitting
                            ball++;
                        }

                        if (frameNumber == 9) {
                            finalScores[bowlIndex][gameNumber] = cumuliScores[bowlIndex][9];

                            Date date = new Date();
                            String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth()
                                    + "/" + date.getDay() + "/" + (date.getYear() + 1900);
                            Score score = new Score(currentThrower.getNickName(), dateString,
                                    Integer.toString(cumuliScores[bowlIndex][9]));
                            score.addScoreToFile();

                        }

                        setter.reset();
                        bowlIndex++;

                    } else {
                        frameNumber++;
                        resetBowlerIterator();
                        bowlIndex = 0;
                        if (frameNumber > 9) {
                            gameFinished = true;
                            gameNumber++;
                        }
                    }
                } else if (party != null) {

                    EndGamePrompt endGamePrompt = new EndGamePrompt((party.getMembers().get(0)).getNickName() + "'s temp.Party");
                    int result = endGamePrompt.getResult();
                    endGamePrompt.distroy();

                    System.out.println("result was: " + result);

                    // TODO: send record of scores to control desk
                    if (result == 1) { // yes, want to play again
                        resetScores();
                        resetBowlerIterator();

                    } else if (result == 2) {// no, don't want to play another game
                        ArrayList<String> printVector;
                        Bowler bowler = party.getMembers().get(0);
                        EndGameReport endGameReport = new EndGameReport(bowler.getNickName() + "'s temp.Party", party);
                        printVector = endGameReport.getResult();
                        Iterator<Bowler> scoreIt = party.getMembers().iterator();
                        party = null;

                        publish(lanePublish());

                        int myIndex = 0;
                        while (scoreIt.hasNext()) {
                            Bowler thisBowler = scoreIt.next();
                            ScoreReport scoreReport = new ScoreReport(thisBowler, finalScores[myIndex++], gameNumber);
                            scoreReport.sendEmail(thisBowler.getEmail());
                            for (String nickNames : printVector) {
                                if (thisBowler.getNickName().equals(nickNames)) {
                                    System.out.println("Printing " + thisBowler.getNickName());
                                    scoreReport.sendPrintout();
                                }
                            }

                        }
                    }
                }

                try {
                    sleep(10);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * receivePinsetterEvent()
     * <p>
     * receives the thrown event from the pinsetter
     *
     * @param pinsetterEvent The pinsetter event that has been received.
     *                       pre: none
     *                       post: the event has been acted upon if desired
     */
    public void receivePinsetterEvent(PinsetterEvent pinsetterEvent) {

        if (pinsetterEvent.pinsDownOnThisThrow() >= 0) { // this is a real throw
            markScore(currentThrower, frameNumber, pinsetterEvent.pinsDownOnThisThrow());

            // next logic handles the ?: what conditions dont allow them another throw?
            // handle the case of 10th frame first
            if (frameNumber == 9) {
                if (pinsetterEvent.totalPinsDown() == 10) {
                    setter.resetPins();
                    if (pinsetterEvent.getThrowNumber() == 1) {
                        tenthFrameStrike = true;
                    }
                }

                if ((pinsetterEvent.totalPinsDown() != 10) && (pinsetterEvent.getThrowNumber() == 2 && !tenthFrameStrike)) {
                    canThrowAgain = false;
                    // publish( lanePublish() );
                }

                if (pinsetterEvent.getThrowNumber() == 3) {
                    canThrowAgain = false;
                    // publish( lanePublish() );
                }
            } else { // its not the 10th frame

                if (pinsetterEvent.pinsDownOnThisThrow() == 10) { // threw a strike
                    canThrowAgain = false;
                    // publish( lanePublish() );
                } else if (pinsetterEvent.getThrowNumber() == 2) {
                    canThrowAgain = false;
                    // publish( lanePublish() );
                } else if (pinsetterEvent.getThrowNumber() == 3)
                    System.out.println("I'm here...");
            }
        }
    }

    /**
     * resetBowlerIterator()
     * <p>
     * sets the current bower iterator back to the first bowler
     * <p>
     * pre: the party as been assigned
     * post: the iterator points to the first bowler in the party
     */
    private void resetBowlerIterator() {
        bowlerIterator = party.getMembers().iterator();
    }

    /**
     * resetScores()
     * <p>
     * resets the scoring mechanism, must be called before scoring starts
     * <p>
     * pre: the party has been assigned
     * post: scoring system is initialized
     */
    private void resetScores() {
        scores.resetGame();
        gameFinished = false;
        frameNumber = 0;
    }

    /**
     * assignParty()
     * <p>
     * assigns a party to this lane
     *
     * @param theParty temp.Party to be assigned
     *                 pre: none
     *                 post: the party has been assigned to the lane
     */
    public void assignParty(Party theParty) {
        party = theParty;
        resetBowlerIterator();
        int partySize = party.getMembers().size();
        cumuliScores = new int[partySize][10];
        finalScores = new int[partySize][128];// Hardcoding a max of 128 games, bite me.
        ArrayList<Bowler> bowlers = theParty.getMembers();
        for(int i = 0; i < bowlers.size(); i++){
            scores.addPlayer(bowlers.get(i));
        }
        gameNumber = 0;

        resetScores();
    }

    /**
     * markScore()
     * <p>
     * Method that marks a bowlers score on the board.
     *
     * @param currentBowler The current bowler
     * @param frame         The frame that bowler is on
     * @param score         The bowler's score
     */
    private void markScore(Bowler currentBowler, int frame, int score) {
        scores.addThrow(currentBowler, score);
        getScore(currentBowler, frame);
        publish(lanePublish());
    }

    /**
     * lanePublish()
     * <p>
     * Method that creates and returns a newly created laneEvent
     *
     * @return The new lane event
     */
    private LaneEvent lanePublish() {
        return new LaneEvent(party, bowlIndex, currentThrower, cumuliScores, scores, frameNumber + 1,
                ball, gameIsHalted);
    }

    /**
     * Method that calculates a bowlers score
     *
     * @param currentBowler The bowler that is currently up
     * @param frame         The frame the current bowler is on
     */
    private void getScore(Bowler currentBowler, int frame) {
        cumuliScores[bowlIndex] = scores.getFramePoints(currentBowler);
//        if(bowlIndex > party.getMembers().size()){
//            bowlIndex = 0;
//        }else{
//            bowlIndex++;
//        }
//        int[] curScore;
//        int strikeballs;
//        curScore = new int[100];
//        for (int i = 0; i != 10; i++) {
//            cumuliScores[bowlIndex][i] = 0;
//        }
//        int current = 2 * (frame - 1) + ball - 1;
//        // Iterate through each ball until the current one.
//        for (int i = 0; i != current + 2; i++) {
//            // Spare:
//            if (i % 2 == 1 && curScore[i - 1] + curScore[i] == 10 && i < current - 1 && i < 19) {
//                // This ball was a the second of a spare.
//                // Also, we're not on the current ball.
//                // Add the next ball to the ith one in cumul.
//                cumuliScores[bowlIndex][(i / 2)] += curScore[i + 1] + curScore[i];
//            } else if (i < current && i % 2 == 0 && curScore[i] == 10 && i < 18) {
//                strikeballs = 0;
//                // This ball is the first ball, and was a strike.
//                // If we can get 2 balls after it, good add them to cumul.
//                if (curScore[i + 2] != -1) {
//                    strikeballs = 1;
//                    if (curScore[i + 3] != -1) {
//                        // Still got em.
//                        strikeballs = 2;
//                    } else if (curScore[i + 4] != -1) {
//                        // Ok, got it.
//                        strikeballs = 2;
//                    }
//                }
//
//                if (strikeballs == 2) {
//                    // Add up the strike.
//                    // Add the next two balls to the current cumulScore.
//                    cumuliScores[bowlIndex][i / 2] += 10;
//                    if (curScore[i + 1] != -1) {
//                        cumuliScores[bowlIndex][i / 2] += curScore[i + 1] + cumuliScores[bowlIndex][(i / 2) - 1];
//                        if (curScore[i + 2] != -1) {
//                            if (curScore[i + 2] != -2) {
//                                cumuliScores[bowlIndex][(i / 2)] += curScore[i + 2];
//                            }
//                        } else {
//                            if (curScore[i + 3] != -2) {
//                                cumuliScores[bowlIndex][(i / 2)] += curScore[i + 3];
//                            }
//                        }
//                    } else {
//                        if (i / 2 > 0) {
//                            cumuliScores[bowlIndex][i / 2] += curScore[i + 2] + cumuliScores[bowlIndex][(i / 2) - 1];
//                        } else {
//                            cumuliScores[bowlIndex][i / 2] += curScore[i + 2];
//                        }
//
//                        if (curScore[i + 3] != -1) {
//                            if (curScore[i + 3] != -2) {
//                                cumuliScores[bowlIndex][(i / 2)] += curScore[i + 3];
//                            }
//                        } else {
//                            cumuliScores[bowlIndex][(i / 2)] += curScore[i + 4];
//                        }
//                    }
//                } else {
//                    break;
//                }
//            } else {
//                // We're dealing with a normal throw, add it and be on our way.
//                if (i % 2 == 0 && i < 18) {
//
//                    if (i / 2 == 0) {
//                        // First frame, first ball. Set his cumul score to the first ball
//                        if (curScore[i] != -2) {
//                            cumuliScores[bowlIndex][i / 2] += curScore[i];
//                        }
//                    } else {
//                        // add his last frame's cumul to this ball, make it this frame's cumul.
//                        if (curScore[i] != -2) {
//                            cumuliScores[bowlIndex][i / 2] += cumuliScores[bowlIndex][i / 2 - 1] + curScore[i];
//                        } else {
//                            cumuliScores[bowlIndex][i / 2] += cumuliScores[bowlIndex][i / 2 - 1];
//                        }
//                    }
//
//                } else if (i < 18) {
//                    if (curScore[i] != -1 && i > 2) {
//                        if (curScore[i] != -2) {
//                            cumuliScores[bowlIndex][i / 2] += curScore[i];
//                        }
//                    }
//                }
//
//                if (i / 2 == 9) {
//                    if (i == 18) {
//                        cumuliScores[bowlIndex][9] += cumuliScores[bowlIndex][8];
//                    }
//                    if (curScore[i] != -2) {
//                        cumuliScores[bowlIndex][9] += curScore[i];
//                    }
//                } else if (i / 2 == 10) {
//                    if (curScore[i] != -2) {
//                        cumuliScores[bowlIndex][9] += curScore[i];
//                    }
//                }
//            }
//        }
    }

    /**
     * isPartyAssigned()
     * <p>
     * checks if a party is assigned to this lane
     *
     * @return true if party assigned, false otherwise
     */
    public boolean isPartyAssigned() {
        return party != null;
    }

    /**
     * subscribe
     * <p>
     * Method that will add a subscriber
     *
     * @param adding Observer that is to be added
     */

    public void subscribe(LaneObserver adding) {
        subscribers.add(adding);
    }

    /**
     * publish
     * <p>
     * Method that publishes an event to subscribers
     *
     * @param event Event that is to be published
     */

    public void publish(LaneEvent event) {
        for (LaneObserver subscriber : subscribers) {
            subscriber.receiveLaneEvent(event);
        }
    }

    /**
     * Accessor to get this LaneState.Lane's pinsetter
     *
     * @return A reference to this lane's pinsetter
     */

    public Pinsetter getPinsetter() {
        return setter;
    }

    /**
     * Pause the execution of this game
     */
    public void pauseGame() {
        gameIsHalted = true;
        publish(lanePublish());
    }

    /**
     * Resume the execution of this game
     */
    public void unPauseGame() {
        gameIsHalted = false;
        publish(lanePublish());
    }
}
