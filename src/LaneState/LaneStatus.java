package LaneState;

import temp.LaneEvent;
import temp.LaneObserver;
import temp.Party;
import temp.Pinsetter;
import temp.PinsetterEvent;

public interface LaneStatus {
    void run();
    void receivePinsetterEvent(PinsetterEvent pinsetterEvent);
    void assignParty(Party theParty);
    boolean isPartyAssigned();
    void subscribe(LaneObserver adding);
    Pinsetter getPinsetter();
    void maintenanceCallToggle();
    boolean isGameFinished();
}
