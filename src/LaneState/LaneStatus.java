package LaneState;

import temp.Party;
import temp.PinsetterEvent;

/**
 * Interface that represents the functionality that every
 * state a lane can be in must implement.
 */
public interface LaneStatus {
    void run();
    void receivePinsetterEvent(PinsetterEvent pinsetterEvent);
    void assignParty(Party theParty);
    boolean isPartyAssigned();
    void maintenanceCallToggle();
}
