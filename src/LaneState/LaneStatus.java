package LaneState;

import temp.Party;
import temp.Pinsetter;

public interface LaneStatus {
    void run();
    // void receivePinsetterEvent(PinsetterEvent pinsetterEvent);
    void assignParty(Party theParty);
    boolean isPartyAssigned();
    Pinsetter getPinsetter();
    void maintenanceCallToggle();
    boolean isGameFinished();
}
