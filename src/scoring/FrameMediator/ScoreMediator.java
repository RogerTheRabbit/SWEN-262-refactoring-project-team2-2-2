package scoring.FrameMediator;

import scoring.FrameState.Frame;
import temp.Bowler;

import java.util.HashMap;

public class ScoreMediator {
    private HashMap<Bowler, Frame[]> frames;

    public ScoreMediator(){
        this.frames = new HashMap<>();
    }

    public void addPlayer(Bowler bowler){
        Frame[] newScores = new Frame[10];
        for(int i = 0; i < 9; i++){
            newScores[i] = new Frame(false);
        }
        newScores[9] = new Frame(true);
        this.frames.put(bowler, newScores);
    }

    public boolean canThrowAgain(Bowler bowler){
        Frame lastFrame = frames.get(bowler)[9];
        return !lastFrame.getFinished();
    }

    public void resetGame(){
        for(Bowler bowler: frames.keySet()){
            this.addPlayer(bowler);
        }
    }

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

    public int[] getFramePoints(Bowler bowler){
        Frame[] bowlerFrames = frames.get(bowler);
        int[] framePoints = new int[10];
        int total = 0;
        for(int i = 0; i < 10; i++){
            total += bowlerFrames[i].getScore();
            //comment out this if statement to fix bug from before the refactoring
            if(i == 0){
                int[] theThrows = bowlerFrames[i].getThrows();
                total -= theThrows[1];
            }
            framePoints[i] = total;

        }
        return framePoints;
    }
}
