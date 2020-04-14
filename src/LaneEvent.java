/*  $Id$
 *
 *  Revisions:
 *    $Log: LaneEvent.java,v $
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

import java.util.HashMap;

public class LaneEvent {

    private final Party party;
    private int ballNum;
    private Bowler currentBowler;
    private int[][] cumuliScore;
    private HashMap<Bowler, int[]> score;
    private int index;
    private int frameNum;
    private boolean mechanicalProblem;

    public LaneEvent(Party party, int index, Bowler currentBowler, int[][] cumuliScore, HashMap<Bowler, int[]> score,
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

    public boolean isMechanicalProblem() {
        return mechanicalProblem;
    }

    public int getFrameNum() {
        return frameNum;
    }

    public HashMap<Bowler, int[]> getScore() {
        return score;
    }

    public int getIndex() {
        return index;
    }

    public int getBallNum() {
        return ballNum;
    }

    public int[][] getCumuliScore() {
        return cumuliScore;
    }

    public Party getParty() {
        return party;
    }

    public Bowler getCurrentBowler() {
        return currentBowler;
    }
}
