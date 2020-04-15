package temp;/* temp.ControlDeskEvent.java
 *
 *  Version:
 *  		$Id$
 *
 *  Revisions:
 * 		$Log$
 *
 */

/**
 * Class that represents control desk event
 */

import java.util.Queue;

public class ControlDeskEvent {

    /** A representation of the wait queue, containing party names */
    private Queue<String> partyQueue;

    /**
     * Constructor for the temp.ControlDeskEvent
     *
     * @param partyQueue a Vector of Strings containing the names of the parties in
     *                   the wait queue
     *
     */

    public ControlDeskEvent(Queue<String> partyQueue) {
        this.partyQueue = partyQueue;
    }

    /**
     * Accessor for partyQueue
     *
     * @return a Vector of Strings representing the names of the parties in the wait
     *         queue
     *
     */

    public Queue<String> getPartyQueue() {
        return partyQueue;
    }
}
