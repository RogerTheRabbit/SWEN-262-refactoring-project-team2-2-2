package LaneState;

import temp.*;

public class Finished implements LaneStatus {
    private Lane lane;

    public Finished(Lane lane) {
        this.lane = lane;
    }

    @Override
    public void run() {
        EndGamePrompt endGamePrompt = new EndGamePrompt((party.getMembers().get(0)).getNickName() + "'s temp.Party");
        int result = endGamePrompt.getResult();
        endGamePrompt.distroy();

        System.out.println("result was: " + result);

        // TODO: send record of scores to control desk
        if (result == 1) { // yes, want to play again
            resetScores();
            resetBowlerIterator();

        } else if (result == 2) {// no, don't want to play another game
            ArrayList<String> printVector;
            Bowler bowler = party.getMembers().get(0);
            EndGameReport endGameReport = new EndGameReport(bowler.getNickName() + "'s temp.Party", party);
            printVector = endGameReport.getResult();
            Iterator<Bowler> scoreIt = party.getMembers().iterator();
            party = null;

            publish(lanePublish());

            int myIndex = 0;
            while (scoreIt.hasNext()) {
                Bowler thisBowler = scoreIt.next();
                ScoreReport scoreReport = new ScoreReport(thisBowler, finalScores[myIndex++], gameNumber);
                scoreReport.sendEmail(thisBowler.getEmail());
                for (String nickNames : printVector) {
                    if (thisBowler.getNickName().equals(nickNames)) {
                        System.out.println("Printing " + thisBowler.getNickName());
                        scoreReport.sendPrintout();
                    }
                }

            }
        }
    }

    @Override
    public void receivePinsetterEvent() {

    }

    @Override
    public void assignParty(Party theParty) {
        party = theParty;
        resetBowlerIterator();
        int partySize = party.getMembers().size();
        cumuliScores = new int[partySize][10];
        finalScores = new int[partySize][128]; // Hardcoding a max of 128 games, bite me.
        gameNumber = 0;

        resetScores();
    }

    @Override
    public boolean isPartyAssigned() {
        return false;
    }

    @Override
    public void subscribe(LaneObserver adding) {

    }

    @Override
    public void publish(LaneEvent event) {

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
}
