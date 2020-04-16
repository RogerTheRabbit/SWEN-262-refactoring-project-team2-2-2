package scoring.FrameState;

/**
 * Possibly not needed, we'll see...
 */
public class TenthFrame implements FrameStatus {

    private final Frame frame;
    private int[] scores;
    private boolean thirdThrow;

    public TenthFrame(Frame frame){
        this.frame = frame;
        this.scores = new int[3];
        for(int i = 0; i < 3; i++){
            scores[i] = -1;
        }
        this.thirdThrow = false;
    }
    /**
     * This returns the total score of the frame (as of time of calling)
     *
     * @return the score
     */
    @Override
    public int getScore() {
        int score = 0;
        for(int i = 0; i < 3; i++){
            if(scores[i] != -1){
                score += scores[i];
            }
        }
        return score;
    }

    /**
     * This adds a ball to the current score. Changes functionality based on
     * the current score.
     *
     * @param ballThrown The points scored by the ball thrown
     */
    @Override
    public void addThrow(int ballThrown) {
        if(thirdThrow){
            scores[2] = ballThrown;
            frame.setFinished();
        }
        else if(scores[0] == -1){
            scores[0] = ballThrown;
        }else if(scores[1] == -1){
            scores[1] = ballThrown;
            if(this.getScore() == 10){
                this.thirdThrow = true;
            }
        }
    }

    @Override
    public int[] getThrows() {
        int[] score = new int[3];
        for (int i = 0; i < 3; i++) {
            score[i] = scores[i];
        }
        return score;
    }
}
