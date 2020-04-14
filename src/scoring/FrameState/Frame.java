package scoring.FrameState;

/**
 * This class keeps track of each individual frame using a state
 * pattern. Each frame has a state which is used to calculate the score.
 * @author Trey Pachucki
 */
public class Frame {
    //Keeps track of the frames status
    private FrameStatus status;

    /**
     * Constructor for a frame, always starts unfinished.
     */
    public Frame(){
        this.status = new Unfinished(this);
    }

    /**
     * Gets the score of the frame (from the status)
     * @return The score total for this frame.
     */
    public int getScore(){
        return this.status.getScore();
    }

    /**
     * Adds another ball to the frame (dealt with by the states).
     * @param ball How many pins the ball knocked down
     */
    public void addBall(int ball){
        status.addBall(ball);
    }

    /**
     * Setter for the status (to be used ONLY by the states).
     * @param status The state to change to (status of the frame)
     */
    void setStatus(FrameStatus status){
        this.status = status;
    }
}
