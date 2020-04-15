package LaneState;

import temp.LaneEvent;
import temp.LaneObserver;
import temp.Party;
import temp.Pinsetter;

import static java.lang.Thread.sleep;

public class Maintenance implements LaneStatus {
    private Lane lane;

    public Maintenance(Lane lane) {
        this.lane = lane;
    }

    @Override
    public void run() {
        try {
            sleep(10);
        } catch (Exception ignored) {
        }
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
        lane.setStatus(new Running(lane));
    }

    @Override
    public boolean isGameFinished() {
        return false;
    }
}
