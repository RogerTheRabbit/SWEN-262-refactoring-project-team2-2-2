package scoring.FrameState;

/**
 * Interface for the different states of the Frame.
 */
public interface FrameStatus {
    /**
     * This returns the total score of the frame (as of time of calling)
     * @return the score
     */
    int getScore();

    /**
     * This adds a ball to the current score. Changes functionality based on
     * the current score.
     * @param ball The points scored by the ball thrown
     */
    void addBall(int ball);
}
