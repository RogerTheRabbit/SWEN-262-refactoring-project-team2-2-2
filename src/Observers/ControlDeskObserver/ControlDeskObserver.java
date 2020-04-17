package Observers.ControlDeskObserver;

/* Observers.ControlDeskObserver.ControlDeskObserver.java
 *
 *  Version
 *  $Id$
 *
 *  Revisions:
 * 		$Log$
 *
 */

/**
 * Interface for classes that observe control desk events
 */

public interface ControlDeskObserver {

    public void receiveControlDeskEvent(ControlDeskEvent ce);

}
