package LaneState;

import temp.Party;
import temp.PinsetterEvent;

public class Maintenance implements LaneStatus {
    private final Lane lane;

    public Maintenance(Lane lane) {
        this.lane = lane;
        lane.gameIsHalted = true;
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
        return lane.party != null;
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