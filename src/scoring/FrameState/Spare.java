package scoring.FrameState;

public class Spare implements FrameStatus {

    private final Frame frame;
    private int[] scores;

    public Spare(Frame frame, int ball1, int ball2){
        this.frame = frame;
        this.scores = new int[3];
        this.scores[0] = ball1;
        this.scores[1] = ball2;
        this.scores[2] = -1;
    }
    /**
     * This returns the total score of the frame (as of time of calling)
     *
     * @return the score
     */
    @Override
    public int getScore() {
        if(scores[2] != -1){
            return 10 + (2 * scores[2]);
        }
        return 10;
    }

    /**
     * This adds a ball to the current score. Changes functionality based on
     * the current score.
     *
     * @param ball The points scored by the ball thrown
     */
    @Override
    public void addBall(int ball) {
        scores[2] = ball;
        frame.setStatus(new PointsFinished(frame, scores));
    }
}
