package LaneState;

import Observers.PinsetterObserver.PinsetterEvent;

/**
 * Represents the state of a lane when it is under maintenance
 */
public class Maintenance implements LaneStatus {
    private final Lane lane;

    Maintenance(Lane lane) {
        this.lane = lane;
        lane.gameIsHalted = true;
    }

    /**
     * When a lane is under maintenance, no bowling occurs
     * and parties can not leave or be added to the lane.  Therefore
     * the run does nothing when in Maintenance.
     */
    @Override
    public void run() {
    }

    /**
     * When a Lane is in maintenance, it does nothing in the receivePinsetterEvent().
     * Pins can't be knocked over when the lane is not being used and therefore
     * the pinsetter events can be ignored.
     * 
     * @param pinsetterEvent Represents pins changing.
     */
    @Override
    public void receivePinsetterEvent(PinsetterEvent pinsetterEvent) {

    }
    /**
     * When a Lane is in maintenance, it does nothing in the assignParty().
     * Parties can not be added when a lane is not working.
     * 
     * @param theParty the party to assign to this lane
     */
    @Override
    public void assignParty(Party theParty) {

    }

    /**
     * Returns if a party is using the lane or not.
     * 
     * @return false if no party is assigned true otherwise.
     */
    @Override
    public boolean isPartyAssigned() {
        return lane.party != null;
    }

    /**
     * Toggles the maintenance state to running.
     */
    @Override
    public void maintenanceCallToggle() {
        lane.setStatus(new Running(lane));
    }
}