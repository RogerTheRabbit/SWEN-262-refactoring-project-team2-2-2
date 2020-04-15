package LaneState;

import temp.Party;
import temp.Pinsetter;
import temp.PinsetterEvent;

public interface LaneStatus {
    void run();
    void receivePinsetterEvent(PinsetterEvent pinsetterEvent);
    void assignParty(Party theParty);
    boolean isPartyAssigned();
    void maintenanceCallToggle();
    boolean isGameFinished();
}
