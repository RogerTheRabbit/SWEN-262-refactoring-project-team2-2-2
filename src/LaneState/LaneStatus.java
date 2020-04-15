package LaneState;

import temp.LaneEvent;
import temp.LaneObserver;
import temp.Party;
import temp.Pinsetter;

public interface LaneStatus {
    public void run();
    public void receivePinsetterEvent();
    public void assignParty(Party theParty);
    public boolean isPartyAssigned();
    public void subscribe(LaneObserver adding);
    public void publish(LaneEvent event);
    public Pinsetter getPinsetter();
    public void maintenanceCallToggle();
    public boolean isGameFinished();
}
