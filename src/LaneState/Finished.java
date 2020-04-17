package LaneState;

import temp.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents the state of a lane when a game has just finished.
 * In this state, the lane needs to be reset and send out score reports.
 */
public class Finished implements LaneStatus {
    private final Lane lane;

    public Finished(Lane lane) {
        this.lane = lane;
    }

    /**
     * When a lane is finished, the run handles asking the user if they want to play again,
     * send out reports and change state to either Running or Empty depending on how the
     * players respond to the EndGamePrompt.
     */
    @Override
    public void run() {
        EndGamePrompt endGamePrompt = new EndGamePrompt((lane.party.getMembers().get(0)).getNickName() + "'s temp.Party");
        int result = endGamePrompt.getResult();
        endGamePrompt.distroy();

        System.out.println("result was: " + result);

        // TODO: send record of scores to control desk
        if (result == 1) { // yes, want to play again
            lane.resetScores();
            lane.resetBowlerIterator();
            lane.setStatus(new Running(lane));
        } else if (result == 2) {// no, don't want to play another game
            ArrayList<String> printVector;
            Bowler bowler = lane.party.getMembers().get(0);
            EndGameReport endGameReport = new EndGameReport(bowler.getNickName() + "'s temp.Party", lane.party);
            printVector = endGameReport.getResult();
            Iterator<Bowler> scoreIt = lane.party.getMembers().iterator();
            lane.party = null;

            lane.publish(lane.lanePublish());

            int myIndex = 0;
            while (scoreIt.hasNext()) {
                Bowler thisBowler = scoreIt.next();
                ScoreReport scoreReport = new ScoreReport(thisBowler, lane.finalScores[myIndex++], lane.gameNumber);
                scoreReport.sendEmail(thisBowler.getEmail());
                for (String nickNames : printVector) {
                    if (thisBowler.getNickName().equals(nickNames)) {
                        System.out.println("Printing " + thisBowler.getNickName());
                        scoreReport.sendPrintout();
                    }
                }

            }
            lane.setStatus(new Empty(lane));
        }
    }

    /**
     * When a Lane is finished, it does nothing in the receivePinsetterEvent().
     * Pins can't be knocked over when the lane is not being used and therefore
     * the pinsetter events can be ignored.
     *
     * @param pinsetterEvent Represents pins changing.
     */
    @Override
    public void receivePinsetterEvent(PinsetterEvent pinsetterEvent) {

    }

    /**
     * When a game is Finished, there is still a party in the lane
     * and therefore a new party can't be assigned to the lane.
     *
     * @param theParty
     */
    @Override
    public void assignParty(Party theParty) {

    }

    /**
     * Returns when a part is using the lane or not.
     * Theoretically, this should always be true.
     *
     * @return true if party is assigned, false otherwise
     */
    @Override
    public boolean isPartyAssigned() {
        return lane.party != null;
    }

    /**
     * When a Lane is finished, it does nothing in the maintenanceCallToggle(). In general,
     * this class should not be called when a lane is in the Finished state.
     */
    @Override
    public void maintenanceCallToggle() {

    }
}
