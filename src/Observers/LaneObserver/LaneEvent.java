package Observers.LaneObserver;

/*  $Id$
 *
 *  Revisions:
 *    $Log: Observers.LaneObserver.LaneEvent.java,v $
 *    Revision 1.6  2003/02/16 22:59:34  ???
 *    added mechnanical problem flag
 *
 *    Revision 1.5  2003/02/02 23:55:31  ???
 *    Many many changes.
 *
 *    Revision 1.4  2003/02/02 22:44:26  ???
 *    More data.
 *
 *    Revision 1.3  2003/02/02 17:49:31  ???
 *    Modified.
 *
 *    Revision 1.2  2003/01/30 21:21:07  ???
 *    *** empty log message ***
 *
 *    Revision 1.1  2003/01/19 22:12:40  ???
 *    created laneevent and laneobserver
 *
 *
 */

import FileWriting.Bowler;
import LaneState.Party;
import scoring.FrameMediator.ScoreMediator;

/**
 * This class represents a lane event and stores all of it's parameters.
 */
public class LaneEvent {

    private final Party party;
    private int ballNum;
    private Bowler currentBowler;
    private int[][] cumuliScore;
    private ScoreMediator score;
    private int index;
    private int frameNum;
    private boolean mechanicalProblem;

    public LaneEvent(Party party, int index, Bowler currentBowler, int[][] cumuliScore, ScoreMediator score,
                     int frameNum, int ballNum, boolean mechanicalProblem) {
        this.party = party;
        this.index = index;
        this.currentBowler = currentBowler;
        this.cumuliScore = cumuliScore;
        this.score = score;
        this.frameNum = frameNum;
        this.ballNum = ballNum;
        this.mechanicalProblem = mechanicalProblem;
    }

    /**
     * Getter for if there is a mechanical problem
     * 
     * @return true if there is a mechanical problem, false otherwise.
     */
    public boolean isMechanicalProblem() {
        return mechanicalProblem;
    }

    /**
     * Getter for the frame number
     * 
     * @return the number of the frame
     */
    public int getFrameNum() {
        return frameNum;
    }

    /**
     * Getter for the score
     * 
     * @return the score
     */
    public ScoreMediator getScore() {
        return score;
    }

    /**
     * Getter for the index
     * 
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Getter for the ball number.
     * 
     * @return the ball number
     */
    public int getBallNum() {
        return ballNum;
    }

    /**
     * Getter for the cumulative score
     * 
     * @return the cumulative score
     */
    public int[][] getCumuliScore() {
        return cumuliScore;
    }

    /**
     * Getter for the party
     * 
     * @return the party
     */
    public Party getParty() {
        return party;
    }

    /**
     * Getter for the current bowler
     * 
     * @return the current bowler
     */
    public Bowler getCurrentBowler() {
        return currentBowler;
    }
}
