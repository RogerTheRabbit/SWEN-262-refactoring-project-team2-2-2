package scoring.FrameState;

public class Strike implements FrameStatus {

    private int[] scores;
    private final Frame frame;

    public Strike(Frame frame){
        this.frame = frame;
        scores = new int[3];
        scores[0] = 10;
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
        for(int i = 1; i < 3; i++){
            if(scores[i] != -1){
                totalScores += 2 * scores[i];
            }
        }
        return totalScores;
    }

    /**
     * This adds a ball to the current score. Changes functionality based on
     * the current score.
     *
     * @param ball The points scored by the ball thrown
     */
    @Override
    public void addBall(int ball) {
        if(scores[1] == -1){
            scores[1] = ball;
        }else if(scores[2] == -1){
            scores[2] = ball;
            frame.setStatus(new PointsFinished(frame, scores));
        }
    }
}
