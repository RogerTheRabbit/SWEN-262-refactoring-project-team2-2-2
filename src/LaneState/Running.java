package LaneState;

import FileWriting.Bowler;
import Observers.PinsetterObserver.PinsetterEvent;
import FileWriting.Score;

import java.util.Date;

/**
 * Represents the lane state when it is running.
 */
public class Running implements LaneStatus {

    private final Lane lane;

    Running(Lane lane) {
        this.lane = lane;
    }

    /**
     * Handles running the lane when it is being used by bowlers. 
     */
    @Override
    public void run() {
        if (lane.bowlerIterator.hasNext()) {
            lane.currentThrower = lane.bowlerIterator.next();

            lane.canThrowAgain = true;
            lane.tenthFrameStrike = false;
            lane.ball = 0;
            while (lane.canThrowAgain) {
                lane.SETTER.ballThrown(); // simulate the thrower's ball hitting
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

            lane.SETTER.reset();
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

    /**
     * Handles receiving a pinsetter event.
     */
    @Override
    public void receivePinsetterEvent(PinsetterEvent pinsetterEvent) {
        if (pinsetterEvent.pinsDownOnThisThrow() >= 0) { // this is a real throw
            markScore(lane.currentThrower, pinsetterEvent.pinsDownOnThisThrow());
            if(pinsetterEvent.totalPinsDown() == 10){
                lane.SETTER.reset();
            }
            lane.canThrowAgain = lane.scores.canThrowAgain(lane.currentThrower, lane.frameNumber);

        }
    }

    /**
     * When the lane is running, that means a party is using the lane.
     * Therefore, new parties can not be assigned to the lane.
     */
    @Override
    public void assignParty(Party theParty) {

    }

    /**
     * When the lane is running, that means a party is  using the lane.
     * 
     * @return true | By definition, when in the running state, a party is assigned to the lane.
     */
    @Override
    public boolean isPartyAssigned() {
        return true; // Can only be running if party is assigned.
    }


    /**
     * Toggles the state to be in maintenance.
     */
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
     * @param score         The bowler's score
     */
    private void markScore(Bowler currentBowler, int score) {
        lane.scores.addThrow(currentBowler, score);
        lane.cumuliScores[lane.bowlIndex] = lane.scores.getFramePoints(currentBowler);
        lane.publish(lane.lanePublish());
    }

}
