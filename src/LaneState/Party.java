package LaneState;
/*
 * prompts.Party.java
 *
 * Version:
 *   $Id$
 *
 * Revisions:
 *   $Log: prompts.Party.java,v $
 *   Revision 1.3  2003/02/09 21:21:31  ???
 *   Added lots of comments
 *
 *   Revision 1.2  2003/01/12 22:23:32  ???
 *   *** empty log message ***
 *
 *   Revision 1.1  2003/01/12 19:09:12  ???
 *   Adding prompts.Party, LaneState.Lane, FileWriting.Bowler, and Alley.
 *
 */

import FileWriting.Bowler;

import java.util.ArrayList;

/**
 * Container that holds bowlers
 */
public class Party {

    /** Vector of bowlers in this party */
    private final ArrayList<Bowler> myBowlers;

    /**
     * Constructor for a prompts.Party
     *
     * @param bowlers Vector of bowlers that are in this party
     */

    public Party(ArrayList<Bowler> bowlers) {
        myBowlers = new ArrayList<>(bowlers);
    }

    /**
     * Accessor for members in this party
     *
     * @return A vector of the bowlers in this party
     */

    public ArrayList<Bowler> getMembers() {
        return myBowlers;
    }
}
