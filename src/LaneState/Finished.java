package LaneState;

import temp.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Finished implements LaneStatus {
    private final Lane lane;

    public Finished(Lane lane) {
        this.lane = lane;
    }

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

    @Override
    public void receivePinsetterEvent(PinsetterEvent pinsetterEvent) {

    }

    @Override
    public void assignParty(Party theParty) {

    }

    @Override
    public boolean isPartyAssigned() {
        return false;
    }

    @Override
    public void maintenanceCallToggle() {

    }
}
