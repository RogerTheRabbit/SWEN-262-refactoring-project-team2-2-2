package LaneState;

import temp.Bowler;
import temp.Party;
import temp.Pinsetter;
import temp.PinsetterEvent;

import java.util.ArrayList;

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
        ArrayList<Bowler> bowlers = theParty.getMembers();
        for(int i = 0; i < bowlers.size(); i++){
            lane.scores.addPlayer(bowlers.get(i));
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
    public Pinsetter getPinsetter() {
        return null;
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
