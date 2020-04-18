package FileWriting;
/*
 * FileWriting.Bowler.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log: FileWriting.Bowler.java,v $
 *     Revision 1.3  2003/01/15 02:57:40  ???
 *     Added accessors and and equals() method
 *
 *     Revision 1.2  2003/01/12 22:23:32  ???
 *     *** empty log message ***
 *
 *     Revision 1.1  2003/01/12 19:09:12  ???
 *     Adding LaneState.Party, LaneState.Lane, FileWriting.Bowler, and Alley.
 *
 */

/**
 * Class that holds all bowler info
 */

public class Bowler {

    private String fullName;
    private String nickName;
    private String email;

    public Bowler(String nick, String full, String mail) {
        nickName = nick;
        fullName = full;
        email = mail;
    }

    /**
     * Getter for the nickname of the bowler.
     * 
     * @return the nickname of the bowler
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Getter for the fullname of the bowler
     * 
     * @return the fullname of the bowler
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Getter for the email of the bowler
     * 
     * @return email of the bowler
     */
    public String getEmail() {
        return email;
    }

    /**
     * Checks of two bowlers are equal
     * 
     * @param b bowler to compare to
     * @return true of equal false otherwise
     */
    public boolean equals(Bowler b) {
        return ((nickName.equals(b.getNickName())) &&
        (fullName.equals(b.getFullName())) &&
        (email.equals(b.getEmail())));
    }
}