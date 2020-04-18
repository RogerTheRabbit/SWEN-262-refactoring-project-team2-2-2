package scoring.FrameState;

/**
 * This class represents the tenth frame and handles the different way
 * scores are handled in the 10th frame vs other frames.
 * @author Trey Pachucki ttp2542@g.rit.edu
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

        if(scores[0] == -1){
            scores[0] = ballThrown;

        }else if(scores[1] == -1){
            scores[1] = ballThrown;
            if(this.getScore() >= 10){
                this.thirdThrow = true;
            }else{
                frame.setFinished();
            }
        }else if(thirdThrow){
            scores[2] = ballThrown;
            frame.setFinished();
        }
    }

    /**
     * Returns the throws done in a frame.
     * @return Throws in int array form
     */
    @Override
    public int[] getThrows() {
        int[] score = new int[3];
        for (int i = 0; i < 3; i++) {
            score[i] = scores[i];
        }
        return score;
    }

    /**
     * This function returns a String array representation of the balls thrown
     * for a specific frame.
     * @return The String array
     */
    @Override
    public String[] getStrings(){
        String[] scoreString = new String[3];
        for(int i = 0; i < 3; i++){
            if(scores[i] != -1){
                scoreString[i] = Integer.toString(scores[i]);
            }else{
                scoreString[i] = "";
            }
        }

        if(scores[0] == 10){
            scoreString[0] = "X";
        }else if(scores[0] + scores[1] == 10){
            scoreString[1] = "/";
        }

        return scoreString;
    }
}
