package LaneState;

import temp.Bowler;
import temp.Party;
import temp.PinsetterEvent;
import temp.Score;

import java.util.Date;

public class Running implements LaneStatus {

    private final Lane lane;

    public Running(Lane lane) {
        this.lane = lane;
        lane.gameIsHalted = false;
    }

    @Override
    public void run() {
        if (lane.bowlerIterator.hasNext()) {
            lane.currentThrower = lane.bowlerIterator.next();

            lane.canThrowAgain = true;
            lane.tenthFrameStrike = false;
            lane.ball = 0;
            while (lane.canThrowAgain) {
                lane.setter.ballThrown(); // simulate the thrower's ball hitting
                lane.ball++;
            }

            if (lane.frameNumber == 9) {
                lane.finalScores[lane.bowlIndex][lane.gameNumber] = lane.cumuliScores[lane.bowlIndex][9];

                Date date = new Date();
                String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth()
                        + "/" + date.getDay() + "/" + (date.getYear() + 1900);
                Score score = new Score(lane.currentThrower.getNickName(), dateString,
                        Integer.toString(lane.cumuliScores[lane.bowlIndex][9]));
                score.addScoreToFile();

            }

            lane.setter.reset();
            lane.bowlIndex++;

        } else {
            lane.frameNumber++;
            lane.resetBowlerIterator();
            lane.bowlIndex = 0;
            if (lane.frameNumber > 9) {
                lane.gameFinished = true;
                lane.gameNumber++;

                // This is roughly where the game finishes so state transition
                lane.setStatus(new Finished(lane));
            }
        }
    }

    @Override
    public void receivePinsetterEvent(PinsetterEvent pinsetterEvent) {
        if (pinsetterEvent.pinsDownOnThisThrow() >= 0) { // this is a real throw
            markScore(lane.currentThrower, pinsetterEvent.getThrowNumber(), pinsetterEvent.pinsDownOnThisThrow());
            if(pinsetterEvent.totalPinsDown() == 10){
                lane.setter.reset();
            }
            lane.canThrowAgain = lane.scores.canThrowAgain(lane.currentThrower, lane.frameNumber);

        }
    }

    @Override
    public void assignParty(Party theParty) {

    }

    @Override
    public boolean isPartyAssigned() {
        return true; // Can only be running if party is assigned.
    }


    @Override
    public void maintenanceCallToggle() {
        lane.setStatus(new Maintenance(lane));
    }

    /**
     * markScore()
     * <p>
     * Method that marks a bowlers score on the board.
     *
     * @param currentBowler The current bowler
     * @param frame         The frame that bowler is on
     * @param score         The bowler's score
     */
    private void markScore(Bowler currentBowler, int frame, int score) {
        lane.scores.addThrow(currentBowler, score);
        getScore(currentBowler);
        lane.publish(lane.lanePublish());
    }

    /**
     * Method that calculates a bowlers score
     *
     * @param currentBowler The bowler that is currently up
     */
    private void getScore(Bowler currentBowler) {
        lane.cumuliScores[lane.bowlIndex] = lane.scores.getFramePoints(currentBowler);
    }
}
