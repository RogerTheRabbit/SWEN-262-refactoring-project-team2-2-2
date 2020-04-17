package temp;

import GUI.ControlDeskView;
import Observers.ControlDeskObserver.ControlDesk;

public class Drive {

    public static void main(String[] args) {

        int numLanes = 3;
        int maxPatronsPerParty = 5;

        ControlDesk controlDesk = new ControlDesk(numLanes);

        ControlDeskView cdv = new ControlDeskView(controlDesk, maxPatronsPerParty);
        controlDesk.subscribe(cdv);

    }
}
