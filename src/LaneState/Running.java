package LaneState;

import temp.LaneEvent;
import temp.LaneObserver;
import temp.Party;
import temp.Pinsetter;

public class Running implements LaneStatus {
    private Lane lane;

    public Running(Lane lane) {
        this.lane = lane;
    }

    @Override
    public void run() {

    }

    @Override
    public void receivePinsetterEvent() {

    }

    @Override
    public void assignParty(Party theParty) {

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
        return false;
    }
}
