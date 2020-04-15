package scoring.FrameState;

/**
 * Possibly not needed, we'll see...
 */
public class TenthFrame implements FrameStatus {

    private final Frame frame;
    private int[] scores;

    public TenthFrame(Frame frame){
        this.frame = frame;
        this.scores = new int[3];
        for(int i = 0; i < scores.length; i++){
            scores[i] = -1;
        }
    }
    /**
     * This returns the total score of the frame (as of time of calling)
     *
     * @return the score
     */
    @Override
    public int getScore() {
        return 0;
    }

    /**
     * This adds a ball to the current score. Changes functionality based on
     * the current score.
     *
     * @param ball The points scored by the ball thrown
     */
    @Override
    public void addBall(int ball) {
        for(int i = 0; i < scores.length; i++){
            if(scores[i] == -1){
                scores[i] = ball;

            }
        }
    }
}
