package LaneState;

import temp.Bowler;
import temp.Party;
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
        for (Bowler bowler : theParty.getMembers()) {
            lane.scores.addPlayer(bowler);
        }
        lane.gameNumber = 0;

        resetScores();
        lane.setStatus(new Running(lane));
    }

    @Override
    public boolean isPartyAssigned() {
        return false;  // Empty means there is no party assigned.
    }

    @Override
    public void maintenanceCallToggle() {

    }

    @Override
    public boolean isGameFinished() {
        return true;
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
