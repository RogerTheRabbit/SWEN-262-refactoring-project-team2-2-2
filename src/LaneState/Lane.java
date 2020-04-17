package LaneState;
/* $Id$
 *
 * Revisions:
 *   $Log: LaneState.Lane.java,v $
 *   Revision 1.52  2003/02/20 20:27:45  ???
 *   Fouls disables.
 *
 *   Revision 1.51  2003/02/20 20:01:32  ???
 *   Added things.
 *
 *   Revision 1.50  2003/02/20 19:53:52  ???
 *   Added foul support.  Still need to update laneview and test this.
 *
 *   Revision 1.49  2003/02/20 11:18:22  ???
 *   Works beautifully.
 *
 *   Revision 1.48  2003/02/20 04:10:58  ???
 *   FileWriting.Score reporting code should be good.
 *
 *   Revision 1.47  2003/02/17 00:25:28  ???
 *   Added disbale controls for View objects.
 *
 *   Revision 1.46  2003/02/17 00:20:47  ???
 *   fix for event when game ends
 *
 *   Revision 1.43  2003/02/17 00:09:42  ???
 *   fix for event when game ends
 *
 *   Revision 1.42  2003/02/17 00:03:34  ???
 *   Bug fixed
 *
 *   Revision 1.41  2003/02/16 23:59:49  ???
 *   Reporting of sorts.
 *
 *   Revision 1.40  2003/02/16 23:44:33  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.39  2003/02/16 23:43:08  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.38  2003/02/16 23:41:05  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.37  2003/02/16 23:00:26  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.36  2003/02/16 21:31:04  ???
 *   FileWriting.Score logging.
 *
 *   Revision 1.35  2003/02/09 21:38:00  ???
 *   Added lots of comments
 *
 *   Revision 1.34  2003/02/06 00:27:46  ???
 *   Fixed a race condition
 *
 *   Revision 1.33  2003/02/05 11:16:34  ???
 *   Boom-Shacka-Lacka!!!
 *
 *   Revision 1.32  2003/02/05 01:15:19  ???
 *   Real close now.  Honest.
 *
 *   Revision 1.31  2003/02/04 22:02:04  ???
 *   Still not quite working...
 *
 *   Revision 1.30  2003/02/04 13:33:04  ???
 *   LaneState.Lane may very well work now.
 *
 *   Revision 1.29  2003/02/02 23:57:27  ???
 *   fix on pinsetter hack
 *
 *   Revision 1.28  2003/02/02 23:49:48  ???
 *   Observers.PinsetterObserver.Pinsetter generates an event when all pins are reset
 *
 *   Revision 1.27  2003/02/02 23:26:32  ???
 *   ControlDesk now runs its own thread and polls for free lanes to assign queue members to
 *
 *   Revision 1.26  2003/02/02 23:11:42  ???
 *   parties can now play more than 1 game on a lane, and lanes are properly released after games
 *
 *   Revision 1.25  2003/02/02 22:52:19  ???
 *   LaneState.Lane compiles
 *
 *   Revision 1.24  2003/02/02 22:50:10  ???
 *   LaneState.Lane compiles
 *
 *   Revision 1.23  2003/02/02 22:47:34  ???
 *   More observering.
 *
 *   Revision 1.22  2003/02/02 22:15:40  ???
 *   Add accessor for pinsetter.
 *
 *   Revision 1.21  2003/02/02 21:59:20  ???
 *   added conditions for the party choosing to play another game
 *
 *   Revision 1.20  2003/02/02 21:51:54  ???
 *   Observers.LaneObserver.LaneEvent may very well be observer method.
 *
 *   Revision 1.19  2003/02/02 20:28:59  ???
 *   fixed sleep thread bug in lane
 *
 *   Revision 1.18  2003/02/02 18:18:51  ???
 *   more changes. just need to fix scoring.
 *
 *   Revision 1.17  2003/02/02 17:47:02  ???
 *   Things are pretty close to working now...
 *
 *   Revision 1.16  2003/01/30 22:09:32  ???
 *   Worked on scoring.
 *
 *   Revision 1.15  2003/01/30 21:45:08  ???
 *   Fixed speling of received in LaneState.Lane.
 *
 *   Revision 1.14  2003/01/30 21:29:30  ???
 *   Fixed some MVC stuff
 *
 *   Revision 1.13  2003/01/30 03:45:26  ???
 *   *** empty log message ***
 *
 *   Revision 1.12  2003/01/26 23:16:10  ???
 *   Improved thread handeling in lane/controldesk
 *
 *   Revision 1.11  2003/01/26 22:34:44  ???
 *   Total rewrite of lane and pinsetter for R2's observer model
 *   Added LaneState.Lane/Observers.PinsetterObserver.Pinsetter Observer
 *   Rewrite of scoring algorythm in lane
 *
 *   Revision 1.10  2003/01/26 20:44:05  ???
 *   small changes
 *
 *
 */

import FileWriting.Bowler;
import Observers.LaneObserver.LaneEvent;
import Observers.LaneObserver.LaneObserver;
import Observers.PinsetterObserver.Pinsetter;
import Observers.PinsetterObserver.PinsetterEvent;
import Observers.PinsetterObserver.PinsetterObserver;
import scoring.FrameMediator.ScoreMediator;
import temp.Party;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a bowling lane. Manages games being played.
 */
public class Lane extends Thread implements PinsetterObserver {
    private LaneStatus laneStatus;

    protected Party party;
    protected final Pinsetter setter;
    protected ScoreMediator scores;
    protected final ArrayList<LaneObserver> subscribers;

    protected boolean gameIsHalted;

    protected boolean gameFinished;
    protected Iterator<Bowler> bowlerIterator;
    protected int ball;
    protected int bowlIndex;
    protected int frameNumber;
    protected boolean tenthFrameStrike;

    protected int[][] cumuliScores;
    protected boolean canThrowAgain;

    protected int[][] finalScores;
    protected int gameNumber;

    protected Bowler currentThrower; // = the thrower who just took a throw

    /**
     * LaneState.Lane()
     * <p>
     * Constructs a new lane and starts its thread
     */
    public Lane() {
        setter = new Pinsetter();
        scores = new ScoreMediator();
        subscribers = new ArrayList<>();

        gameIsHalted = false;

        gameNumber = 0;

        setter.subscribe(this);

        laneStatus = new Empty(this);

        this.start();
    }

    /**
     * run()
     * <p>
     * entry point for execution of this lane
     */
    public void run() {

        try {
            while (true) {
                laneStatus.run();

                if(gameIsHalted){
                    laneStatus.maintenanceCallToggle();
                    gameIsHalted = false;
                }

                try { sleep(10); } catch (Exception ignored) {
                    System.err.println(ignored.getMessage());
                }

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * receivePinsetterEvent()
     * <p>
     * receives the thrown event from the pinsetter
     *
     * @param pinsetterEvent The pinsetter event that has been received.
     *                       pre: none
     *                       post: the event has been acted upon if desired
     */
    public void receivePinsetterEvent(PinsetterEvent pinsetterEvent) {
        laneStatus.receivePinsetterEvent(pinsetterEvent);
        publish(lanePublish());
    }

     /**
      * resetBowlerIterator()
      * <p>
      * sets the current bower iterator back to the first bowler
      * <p>
      * pre: the party as been assigned
      * post: the iterator points to the first bowler in the party
      */
     protected void resetBowlerIterator() {
         bowlerIterator = party.getMembers().iterator();
     }

     /**
      * resetScores()
      * <p>
      * resets the scoring mechanism, must be called before scoring starts
      * <p>
      * pre: the party has been assigned
      * post: scoring system is initialized
      */
     protected void resetScores() {
         scores.resetGame();
         gameFinished = false;
         frameNumber = 0;
     }


    /**
     * assignParty()
     * <p>
     * assigns a party to this lane
     *
     * @param theParty temp.Party to be assigned
     *                 pre: none
     *                 post: the party has been assigned to the lane
     */
    public void assignParty(Party theParty) {
        laneStatus.assignParty(theParty);
    }

    /**
     * lanePublish()
     * <p>
     * Method that creates and returns a newly created laneEvent
     *
     * @return The new lane event
     */
    protected LaneEvent lanePublish() {
        return new LaneEvent(party, bowlIndex, currentThrower, cumuliScores, scores, frameNumber + 1,
                ball, gameIsHalted);
    }

    /**
     * isPartyAssigned()
     * <p>
     * checks if a party is assigned to this lane
     *
     * @return true if party assigned, false otherwise
     */
    public boolean isPartyAssigned() {
        return laneStatus.isPartyAssigned();
    }

    /**
     * subscribe
     * <p>
     * Method that will add a subscriber
     *
     * @param adding Observer that is to be added
     */

    public void subscribe(LaneObserver adding) {
        subscribers.add(adding);
    }

    /**
     * publish
     * <p>
     * Method that publishes an event to subscribers
     *
     * @param event Event that is to be published
     */
    public void publish(LaneEvent event) {
        for (LaneObserver subscriber : subscribers) {
            subscriber.receiveLaneEvent(event);
        }
    }

    /**
     * Accessor to get this LaneState.Lane's pinsetter
     *
     * @return A reference to this lane's pinsetter
     */

    public Pinsetter getPinsetter() {
        return setter;
    }

    /**
     * Toggles the maintenance status of the lane between in maintenance and running
     * 
     * Always sets to true to handle threading issue.
     */
    public void maintenanceCallToggle() {
        gameIsHalted = true;
    }

    /**
     * Sets the state of the lane between Empty, Finished, Running, & Maintenance
     * 
     * @param status the state to change to.
     */
    public void setStatus(LaneStatus status) {
        laneStatus = status;
    }
}
