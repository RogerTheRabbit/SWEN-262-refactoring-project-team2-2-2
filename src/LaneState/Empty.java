package LaneState;

import FileWriting.Bowler;
import Observers.PinsetterObserver.PinsetterEvent;

/**
 * Represents the state of lane that is not being used.
 */
public class Empty implements LaneStatus {
    private final Lane lane;

    public Empty(Lane lane) {
        this.lane = lane;
    }

    /**
     * When a Lane is unused, it does nothing in the run.
     */
    @Override
    public void run() {

    }

    /**
     * When a Lane is unused, it does nothing in the receivePinsetterEvent().
     * Pins can't be knocked over when the lane is not being used and therefore
     * the pinsetter events can be ignored.
     * 
     * @param pinsetterEvent Represents pins changing.
     */
    @Override
    public void receivePinsetterEvent(PinsetterEvent pinsetterEvent) {

    }

    /**
     * Assigns a party to the lane, sets up the lane and switches the state to Running.
     * 
     * @param theParty the party to be assigned
     */
    @Override
    public void assignParty(Party theParty) {
        lane.party = theParty;
        lane.resetBowlerIterator();
        int partySize = lane.party.getMembers().size();
        lane.cumuliScores = new int[partySize][10];
        lane.finalScores = new int[partySize][128]; // Hardcoding a max of 128 games, bite me.
        for (Bowler bowler : theParty.getMembers()) {
            lane.scores.addPlayer(bowler);
        }
        lane.gameNumber = 0;

        resetScores();
        lane.setStatus(new Running(lane));
    }

    /**
     * If a lane is empty, by definition it does not have a party assigned.
     * 
     * @return false if no party is assigned true otherwise.
     */
    @Override
    public boolean isPartyAssigned() {
        return false;  // Empty means there is no party assigned.
    }

    /**
     * When a Lane is unused, it does nothing in the maintenanceCallToggle(). In general,
     * this class should not be called when no one is using the lane.
     */
    @Override
    public void maintenanceCallToggle() {

    }

    /**
     * resetScores()
     * <p>
     * resets the scoring mechanism, must be called before scoring starts
     * <p>
     * pre: the party has been assigned
     * post: scoring system is initialized
     */
    private void resetScores() {
        lane.resetScores();
    }
}
