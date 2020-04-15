package LaneState;

import temp.*;

import static java.lang.Thread.sleep;

public class Maintenance implements LaneStatus {
    private Lane lane;

    public Maintenance(Lane lane) {
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

    }

    @Override
    public boolean isPartyAssigned() {
        return false;
    }

    @Override
    public void subscribe(LaneObserver adding) {

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
        return false;
    }
}
