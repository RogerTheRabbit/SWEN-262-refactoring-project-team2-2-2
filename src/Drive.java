import GUI.ControlDeskView;
import Observers.ControlDeskObserver.ControlDesk;

/**
 * The entry point for the application. Responsible for initializing some 
 * parameters and starting the system.
 */
public class Drive {

    public static void main(String[] args) {

        int numLanes = 3;
        int maxPatronsPerParty = 5;

        ControlDesk controlDesk = new ControlDesk(numLanes);

        ControlDeskView cdv = new ControlDeskView(controlDesk, maxPatronsPerParty);
        controlDesk.subscribe(cdv);

    }
}
