package Observers.ControlDeskObserver;

/* Observers.ControlDeskObserver.ControlDeskObserver.ControlDeskEvent.java
 *
 *  Version:
 *  		$Id$
 *
 *  Revisions:
 * 		$Log$
 *
 */

import java.util.Queue;

/**
 * Class that represents control desk event
 */
public class ControlDeskEvent {

    /** A representation of the wait queue, containing party names */
    private Queue<String> partyQueue;

    /**
     * Constructor for the Observers.ControlDeskObserver.ControlDeskObserver.ControlDeskEvent
     *
     * @param partyQueue a Vector of Strings containing the names of the parties in
     *                   the wait queue
     *
     */

    ControlDeskEvent(Queue<String> partyQueue) {
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
