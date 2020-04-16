package scoring.FrameState;

/**
 * This class represents a strike who's total points is unknown (because the
 * final balls haven't been thrown). Gets switched to from Unfinished
 * @author Trey Pachucki ttp2542@g.rit.edu
 */

public class Strike implements FrameStatus {

    private int[] scores;
    private final Frame frame;

    /**
     * Constructor for a Strike
     * @param frame The frame who's state gets updated.
     */
    public Strike(Frame frame){
        this.frame = frame;
        scores = new int[3];
        //First throw had to be 10
        scores[0] = 10;
        //default values
        scores[1] = -1;
        scores[2] = -1;
    }

    /**
     * This returns the total score of the frame (as of time of calling)
     *
     * @return the score
     */
    @Override
    public int getScore() {
        int totalScores = 10;
        totalScores += scores[1] + scores[2];
        return totalScores;
    }

    /**
     * This adds a ball to the current score. Changes functionality based on
     * the current score.
     *
     * @param ballThrown The points scored by the ball thrown
     */
    @Override
    public void addThrow(int ballThrown) {
        if(scores[1] == -1){
            scores[1] = ballThrown;
        }else if(scores[2] == -1){
            scores[2] = ballThrown;
            frame.setStatus(new PointsFinished(frame, scores, this.getScore()));
        }
    }

    @Override
    public int[] getThrows() {
        int[] score = new int[2];
        score[0] = 10;
        score[1] = -1;
        return score;
    }
}
