package Observers.ControlDeskObserver;

/* Observers.ControlDeskObserver.ControlDesk.java
 *
 *  Version:
 *  		$Id$
 *
 *  Revisions:
 * 		$Log: Observers.ControlDeskObserver.ControlDesk.java,v $
 * 		Revision 1.13  2003/02/02 23:26:32  ???
 * 		Observers.ControlDeskObserver.ControlDesk now runs its own thread and polls for free lanes to assign queue members to
 *
 * 		Revision 1.12  2003/02/02 20:46:13  ???
 * 		Added " 's LaneState.Party" to party names.
 *
 * 		Revision 1.11  2003/02/02 20:43:25  ???
 * 		misc cleanup
 *
 * 		Revision 1.10  2003/02/02 17:49:10  ???
 * 		Fixed problem in getPartyQueue that was returning the first element as every element.
 *
 * 		Revision 1.9  2003/02/02 17:39:48  ???
 * 		Added accessor for lanes.
 *
 * 		Revision 1.8  2003/02/02 16:53:59  ???
 * 		Updated comments to match javadoc format.
 *
 * 		Revision 1.7  2003/02/02 16:29:52  ???
 * 		Added Observers.ControlDeskObserver.ControlDeskObserver.ControlDeskEvent and Observers.ControlDeskObserver.ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of Observers.ControlDeskObserver.ControlDesk.
 *
 * 		Revision 1.6  2003/02/02 06:09:39  ???
 * 		Updated many classes to support the GUI.ControlDeskView.
 *
 * 		Revision 1.5  2003/01/26 23:16:10  ???
 * 		Improved thread handeling in lane/controldesk
 *
 *
 */

import FileWriting.*;
import LaneState.Lane;
import LaneState.Party;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Class that represents control desk
 */
public class ControlDesk extends Thread {

    /**
     * The collection of Lanes
     */
    private final HashSet<Lane> lanes;

    /**
     * The party wait queue
     */
    private final LinkedList<Party> partyQueue;

    /**
     * The number of lanes represented
     */
    private final int numLanes;

    /**
     * The collection of subscribers
     */
    private final ArrayList<ControlDeskObserver> subscribers;

    /**
     * Constructor for the Observers.ControlDeskObserver.ControlDesk class
     *
     * @param numLanes the number of lanes to be represented
     */

    public ControlDesk(int numLanes) {
        this.numLanes = numLanes;
        lanes = new HashSet<>();
        partyQueue = new LinkedList<>();

        subscribers = new ArrayList<>();

        for (int i = 0; i < numLanes; i++) {
            lanes.add(new Lane());
        }

        this.start();

    }

    /**
     * Main loop for Observers.ControlDeskObserver.ControlDesk's thread
     */
    public void run() {
        while (true) {

            assignLane();

            try {
                sleep(250);
            } catch (Exception e) {
                System.err.println("Error... " + e);
            }
        }
    }

    /**
     * Retrieves a matching FileWriting.Bowler from the bowler database.
     *
     * @param nickName The NickName of the FileWriting.Bowler
     * @return a FileWriting.Bowler object.
     */

    private Bowler registerPatron(String nickName) {
        Bowler patron = null;

        try {
            // only one patron / nick.... no dupes, no checks

            patron = BowlerFile.getBowlerInfo(nickName);

        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException..." + e);
        } catch (IOException e) {
            System.err.println("IOException..." + e);
        }

        return patron;
    }

    /**
     * Iterate through the available lanes and assign the paties in the wait queue
     * if lanes are available.
     */

    public void assignLane() {
        Iterator<Lane> it = lanes.iterator();

        while (it.hasNext() && !partyQueue.isEmpty()) {
            Lane curLane = it.next();

            if (!curLane.isPartyAssigned()) {
                System.out.println("ok... assigning this party");
                curLane.assignParty(partyQueue.removeFirst());
            }
        }
        publish(new ControlDeskEvent(getPartyQueue()));
    }


    /**
     * Creates a party from a Vector of nickNAmes and adds them to the wait queue.
     *
     * @param partyNicks A Vector of NickNames
     */

    public void addPartyQueue(ArrayList<String> partyNicks) {
        ArrayList<Bowler> partyBowlers = new ArrayList<>();
        for (String partyNick : partyNicks) {
            Bowler newBowler = registerPatron(partyNick);
            partyBowlers.add(newBowler);
        }
        Party newParty = new Party(partyBowlers);
        partyQueue.add(newParty);
        publish(new ControlDeskEvent(getPartyQueue()));
    }

    /**
     * Returns a Vector of party names to be displayed in the GUI representation of
     * the wait queue.
     *
     * @return a Vecotr of Strings
     */

    public Queue<String> getPartyQueue() {
        Queue<String> displayPartyQueue = new LinkedList<>();
        for (Party party : partyQueue) {
            ArrayList<Bowler> bowlers = party.getMembers();
            String nextParty = bowlers.get(0).getNickName() + "'s LaneState.Party";
            displayPartyQueue.add(nextParty);
        }
        return displayPartyQueue;
    }

    /**
     * Accessor for the number of lanes represented by the Observers.ControlDeskObserver.ControlDesk
     *
     * @return an int containing the number of lanes represented
     */

    public int getNumLanes() {
        return numLanes;
    }

    /**
     * Allows objects to subscribe as observers
     *
     * @param adding the Observers.ControlDeskObserver.ControlDeskObserver that will be subscribed
     */

    public void subscribe(ControlDeskObserver adding) {
        subscribers.add(adding);
    }

    /**
     * Broadcast an event to subscribing objects.
     *
     * @param event the Observers.ControlDeskObserver.ControlDeskObserver.ControlDeskEvent to broadcast
     */

    public void publish(ControlDeskEvent event) {
        for (ControlDeskObserver subscriber : subscribers) {
            subscriber.receiveControlDeskEvent(event);
        }
    }

    /**
     * Accessor method for lanes
     *
     * @return a HashSet of Lanes
     */

    public HashSet<Lane> getLanes() {
        return lanes;
    }
}
