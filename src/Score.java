/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */

public class Score {

    private String nickName;
    private String date;
    private String score;

    /**
     * Constructor for Score
     * @param nickName: The players nickname
     * @param date: The date the score occurred
     * @param score: The score achieved
     */
    public Score(String nickName, String date, String score) {
        this.nickName = nickName;
        this.date = date;
        this.score = score;
    }

    /**
     * Adds itself to the score file.
     */
    public void addScoreToFile(){
        try {
            ScoreHistoryFile.addScore(this);
        }catch(Exception e){
            System.out.println("Error in writing to score file");
        }
    }
    /**
     * Getter for the date a score was achieved
     * @return the date the score was achieved
     */
    public String getDate() {
        return date;
    }

    /**
     * Getter for the score achieved
     * @return The score achieved
     */
    public String getScore() {
        return score;
    }

    /**
     * Creates a string representation of the score
     * @return The string representation of score
     */
    public String toString() {
        return nickName + "\t" + date + "\t" + score;
    }
}
