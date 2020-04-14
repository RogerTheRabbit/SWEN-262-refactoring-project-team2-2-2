package scoring.FrameState;

public class Unfinished implements FrameStatus {

    private int[] scores;

    public Unfinished(){
        this.scores = new int[4];
    }

    /**
     * This returns the total score of the frame (as of time of calling)
     *
     * @return the score
     */
    @Override
    public int getScore() {
        int totalScore = 0;
        for(int i = 0; i < 3; i++){
            totalScore += scores[i];
        }

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

    }
}
