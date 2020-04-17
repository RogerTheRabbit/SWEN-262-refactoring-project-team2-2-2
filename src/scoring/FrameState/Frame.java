package scoring.FrameState;

/**
 * This class keeps track of each individual frame using a state
 * pattern. Each frame has a state which is used to calculate the score.
 * @author Trey Pachucki
 */
public class Frame {
    //Keeps track of the frames status
    private FrameStatus status;
    private boolean isFinished;

    /**
     * Constructor for a frame, always starts unfinished.
     */
    public Frame(boolean tenth){
        if(tenth) {
            this.status = new TenthFrame(this);
        }else{
            this.status = new Unfinished(this);
        }
        this.isFinished = false;
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
        status.addThrow(ball);
    }

    /**
     * Setter for the status (to be used ONLY by the states).
     * @param status The state to change to (status of the frame)
     */
    protected void setStatus(FrameStatus status){
        this.status = status;
    }

    /**
     * Setter for if the frame is finished or not.
     */
    protected void setFinished(){
        this.isFinished = true;
    }

    /**
     * Getter for isFinished
     * @return returns true if frame is finished, false otherwise.
     */
    public boolean getFinished(){
        return this.isFinished;
    }

    /**
     * Returns the throws done in a frame.
     * @return Throws in int array form
     */
    public int[] getThrows(){
        return status.getThrows();
    }
}
