package scoring.FrameState;

/**
 * The final state for a frame. If it gets here, the points are finalized for
 * that frame.
 * @author Trey Pachucki ttp2542@g.rit.edu
 */
public class PointsFinished implements FrameStatus {

    private final int[] scores;
    private final int totalScore;
    private final Frame frame;

    /**
     * Constructor for points finished, created by another state.
     * @param frame The frame who's status is updated
     * @param scores The finalized scores array
     */
    public PointsFinished(Frame frame, int[] scores){
        this.scores = scores;
        this.frame = frame;
        int finalScore = 0;
        for(int i = 0; i < scores.length; i++){
            if(i > 1){
                finalScore += scores[i] * 2;
            }else{
                finalScore += scores[i];
            }
        }

        this.totalScore = finalScore;
    }

    /**
     * This returns the total score of the frame (as of time of calling)
     *
     * @return the score
     */
    @Override
    public int getScore() {
        return totalScore;
    }

    /**
     * This adds a ball to the current score. Changes functionality based on
     * the current score.
     *
     * @param ball The points scored by the ball thrown
     */
    @Override
    public void addBall(int ball) {
        //does nothing, the scores are finalized
    }
}
