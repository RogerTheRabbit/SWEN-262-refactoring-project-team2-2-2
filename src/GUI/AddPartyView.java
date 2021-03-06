package GUI;

/* AddPartyView.java
 *
 *  Version:
 * 		 $Id$
 *
 *  Revisions:
 * 		$Log: AddPartyView.java,v $
 * 		Revision 1.7  2003/02/20 02:05:53  ???
 * 		Fixed addPatron so that duplicates won't be created.
 *
 * 		Revision 1.6  2003/02/09 20:52:46  ???
 * 		Added comments.
 *
 * 		Revision 1.5  2003/02/02 17:42:09  ???
 * 		Made updates to migrate to observer model.
 *
 * 		Revision 1.4  2003/02/02 16:29:52  ???
 * 		Added ControlDeskEvent and ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of ControlDesk.
 *
 *
 */

/**
 * Class for GUI components need to add a party
 */

import FileWriting.Bowler;
import FileWriting.BowlerFile;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Constructor for GUI used to Add Parties to the waiting party queue.
 *
 */

public class AddPartyView implements ActionListener, ListSelectionListener {

    private int maxSize;

    private JFrame win;
    private JButton addPatron, newPatron, removePatron, finished;
    private JList partyList, allBowlers;
    private ArrayList<String> party, bowlerdb;

    private ControlDeskView controlDesk;

    private String selectedNick, selectedMember;

    /**
     * Creates an instance of the party view from the ControlDesk and the max number
     * of parties
     * @param controlDesk: The source of information for the view
     * @param max: The max number of parties
     */
    public AddPartyView(ControlDeskView controlDesk, int max) {

        this.controlDesk = controlDesk;
        maxSize = max;

        win = new JFrame("Add Party");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 3));

        // temp.Party Panel
        JPanel partyPanel = new JPanel();
        partyPanel.setLayout(new FlowLayout());
        partyPanel.setBorder(new TitledBorder("Your Party"));

        party = new ArrayList<>();
        ArrayList<String> empty = new ArrayList<>();
        empty.add("(Empty)");

        String[] emptyArray = new String[empty.size()];
        emptyArray = empty.toArray(emptyArray);
        partyList = new JList<>(emptyArray);
        partyList.setFixedCellWidth(120);
        partyList.setVisibleRowCount(5);
        partyList.addListSelectionListener(this);
        JScrollPane partyPane = new JScrollPane(partyList);
        // partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        partyPanel.add(partyPane);

        // FileWriting.Bowler Database
        JPanel bowlerPanel = new JPanel();
        bowlerPanel.setLayout(new FlowLayout());
        bowlerPanel.setBorder(new TitledBorder("Bowler Database"));

        try {
            bowlerdb = new ArrayList<>(BowlerFile.getBowlers());
        } catch (Exception e) {
            System.err.println("File Error");
            bowlerdb = new ArrayList<>();
        }
        String[] bowlerArray = new String[bowlerdb.size()];
        bowlerArray = bowlerdb.toArray(bowlerArray);
        allBowlers = new JList<>(bowlerArray);
        allBowlers.setVisibleRowCount(8);
        allBowlers.setFixedCellWidth(120);
        JScrollPane bowlerPane = new JScrollPane(allBowlers);
        bowlerPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        allBowlers.addListSelectionListener(this);
        bowlerPanel.add(bowlerPane);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        addPatron = new JButton("Add to Party");
        JPanel addPatronPanel = new JPanel();
        addPatronPanel.setLayout(new FlowLayout());
        addPatron.addActionListener(this);
        addPatronPanel.add(addPatron);

        removePatron = new JButton("Remove Member");
        JPanel remPatronPanel = new JPanel();
        remPatronPanel.setLayout(new FlowLayout());
        removePatron.addActionListener(this);
        remPatronPanel.add(removePatron);

        newPatron = new JButton("New Patron");
        JPanel newPatronPanel = new JPanel();
        newPatronPanel.setLayout(new FlowLayout());
        newPatron.addActionListener(this);
        newPatronPanel.add(newPatron);

        finished = new JButton("Finished");
        JPanel finishedPanel = new JPanel();
        finishedPanel.setLayout(new FlowLayout());
        finished.addActionListener(this);
        finishedPanel.add(finished);

        buttonPanel.add(addPatronPanel);
        buttonPanel.add(remPatronPanel);
        buttonPanel.add(newPatronPanel);
        buttonPanel.add(finishedPanel);

        // Clean up main panel
        colPanel.add(partyPanel);
        colPanel.add(bowlerPanel);
        colPanel.add(buttonPanel);

        win.getContentPane().add("Center", colPanel);

        win.pack();

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();

    }

    /**
     * This function handles action events based on the buttons pressed
     * @param e: the action event to be handled
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addPatron)) {
            handleAddPatron();
        }
        else if (e.getSource().equals(removePatron)) {
            handleRemovePatron();
        }
        else if (e.getSource().equals(finished)) {
            handleFinished();
        }
    }

    /**
     * Helper function that helps adding the new patron.
     */
    private void handleAddPatron(){
        if (selectedNick != null && party.size() < maxSize) {
            if (party.contains(selectedNick)) {
                System.err.println("Member already in Party");
            } else {
                party.add(selectedNick);
                String[] partyArray = new String[party.size()];
                partyArray = party.toArray(partyArray);
                partyList.setListData(partyArray);
            }
        }
    }

    /**
     * Helper function that helps remove a patron.
     */
    private void handleRemovePatron(){
        if (selectedMember != null) {
            party.remove(selectedMember);
            String[] partyArray = new String[party.size()];
            partyArray = party.toArray(partyArray);
            partyList.setListData(partyArray);
        }
    }

    /**
     * Helper function that helps finish adding/removing new patrons.
     */
    private void handleFinished(){
        if (party != null && party.size() > 0) {
            controlDesk.updateAddParty(this);
        }
        win.hide();
    }

    /**
     * Handler for List actions
     *
     * @param e the ListActionEvent that triggered the handler
     */
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource().equals(allBowlers)) {
            selectedNick = ((String) ((JList) e.getSource()).getSelectedValue());
        }
        if (e.getSource().equals(partyList)) {
            selectedMember = ((String) ((JList) e.getSource()).getSelectedValue());
        }
    }

    /**
     * Called by NewPatronView to notify AddPartyView to update
     *
     * @param newPatron the NewPatronView that called this method
     */
    public void updateNewPatron(NewPatronView newPatron) {
        try {
            Bowler checkBowler = BowlerFile.getBowlerInfo(newPatron.getNick());
            if (checkBowler == null) {
                BowlerFile.putBowlerInfo(newPatron.getNick(), newPatron.getFull(), newPatron.getEmail());
                bowlerdb = new ArrayList<>(BowlerFile.getBowlers());
                String[] bowlerArray = new String[bowlerdb.size()];
                bowlerArray = bowlerdb.toArray(bowlerArray);
                allBowlers.setListData(bowlerArray);
                party.add(newPatron.getNick());
                String[] partyArray = new String[party.size()];
                partyArray = party.toArray(partyArray);
                partyList.setListData(partyArray);
            } else {
                System.err.println("A FileWriting.Bowler with that name already exists.");
            }
        } catch (Exception e2) {
            System.err.println("File I/O Error");
        }
    }

    /**
     * Accessor for Party
     */
    public ArrayList<String> getParty() {
        return party;
    }
}
