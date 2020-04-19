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
     * This adds a ballThrown to the current score. Changes functionality based on
     * the current score.
     * @param ballThrown The points scored by the ballThrown thrown
     */
    void addThrow(int ballThrown);

    /**
     * Returns the throws done in a frame.
     * 
     * @return Throws in int array form
     */
    int[] getThrows();

    /**
     * This function returns a String array representation of the balls thrown
     * for a specific frame.
     * @return The String array
     */
    String[] getStrings();
}
