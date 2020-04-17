package scoring.FrameMediator;

import scoring.FrameState.Frame;
import temp.Bowler;

import java.util.HashMap;

/**
 * This class is responsible for managing scoring.
 */
public class ScoreMediator {
    private HashMap<Bowler, Frame[]> frames;

    public ScoreMediator(){
        this.frames = new HashMap<>();
    }

    /**
     * Adds a player to the lane to start keeping track
     * of their bowling scores.
     * 
     * @param bowler the bowler to be added
     */
    public void addPlayer(Bowler bowler){
        Frame[] newScores = new Frame[10];
        for(int i = 0; i < 9; i++){
            newScores[i] = new Frame(false);
        }
        newScores[9] = new Frame(true);
        this.frames.put(bowler, newScores);
    }

    /**
     * Returns if a player can throw again for a given frame.
     * 
     * @param bowler Bowler to check
     * @param frame Frame to check
     * @return true if bowler can throw again false otherwise
     */
    public boolean canThrowAgain(Bowler bowler, int frame){
        Frame currentFrame = this.frames.get(bowler)[frame];
        return !currentFrame.getFinished();
    }

    /**
     * Resets the scores for all the bowlers in the lane.
     */
    public void resetGame(){
        for(Bowler bowler: frames.keySet()){
            this.addPlayer(bowler);
        }
    }

    /**
     * Handles a throw made by a bowler
     * 
     * @param bowler Bowler that made throw
     * @param ballThrown Ball that they threw
     */
    public void addThrow(Bowler bowler, int ballThrown){
        //get the frames for a specific bowler
        Frame[] throwerFrames = this.frames.get(bowler);
        for(int i = 0; i < 10; i++){
            //find the most recent unfinished frame for a bowler
            if(!throwerFrames[i].getFinished()){
                for(int j = 0; j <= i; j++){
                    //let all past frames know about the last ball (including
                    //the most recent one to be needed)
                    throwerFrames[j].addBall(ballThrown);
                }
                break;
            }
        }
    }

    /**
     * Returns all of the throws that the bowler made.
     * 
     * @param bowler Bowler to get throws for
     * @return Throws made by the bowler
     */
    public int[] getAllThrows(Bowler bowler){
        Frame[] bowlerFrames = frames.get(bowler);
        int[] allThrows = new int[21];
        for(int i = 0; i < 10; i ++){
            int[] frameThrows = bowlerFrames[i].getThrows();
            allThrows[2 * i] = frameThrows[0];
            allThrows[(2 * i) + 1] = frameThrows[1];
            if(i == 9){
                allThrows[20] = frameThrows[2];
            }
        }
        return allThrows;
    }

    /**
     * Gets an array of all the points for each frame.
     * 
     * @param bowler Bowler to get frame points for
     * @return An array where each value is the score for that frame.
     */
    public int[] getFramePoints(Bowler bowler){
        Frame[] bowlerFrames = frames.get(bowler);
        int[] framePoints = new int[10];
        int total = 0;
        for(int i = 0; i < 10; i++){
            total += bowlerFrames[i].getScore();
            framePoints[i] = total;
        }
        return framePoints;
    }
}
