package LaneState;

import temp.Party;
import temp.Pinsetter;
import temp.PinsetterEvent;

public class Empty implements LaneStatus {
    private final Lane lane;

    public Empty(Lane lane) {
        this.lane = lane;
    }

    @Override
    public void run() {

    }

    @Override
    public void receivePinsetterEvent(PinsetterEvent pinsetterEvent) {

    }

    @Override
    public void assignParty(Party theParty) {
        lane.party = theParty;
        lane.resetBowlerIterator();
        int partySize = lane.party.getMembers().size();
        lane.cumuliScores = new int[partySize][10];
        lane.finalScores = new int[partySize][128]; // Hardcoding a max of 128 games, bite me.
        lane.gameNumber = 0;

        lane.resetScores();
        lane.setStatus(new Running(lane));
    }

    @Override
    public boolean isPartyAssigned() {
        return false;  // Empty means there is no party assigned.
    }

    @Override
    public Pinsetter getPinsetter() {
        return null;
    }

    @Override
    public void maintenanceCallToggle() {
        lane.setStatus(new Running(lane));
    }

    @Override
    public boolean isGameFinished() {
        return true;
    }

    /**
     * resetBowlerIterator()
     * <p>
     * sets the current bower iterator back to the first bowler
     * <p>
     * pre: the party as been assigned
     * post: the iterator points to the first bowler in the party
     */
    private void resetBowlerIterator() {
        lane.bowlerIterator = lane.party.getMembers().iterator();
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
