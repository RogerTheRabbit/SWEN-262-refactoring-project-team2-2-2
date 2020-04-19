package prompts;

import FileWriting.Bowler;
import LaneState.Party;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class is responsible for asking the player if they want
 * to receive a report.
 */
public class EndGameReport implements ActionListener, ListSelectionListener {

    private JFrame win;
    private JButton printButton, finished;
    private ArrayList<String> retVal;

    private int result;

    private String selectedMember;

    /**
     * Constructor
     * 
     * @param partyName Name of party  to make report for
     * @param party Party to make report for
     */
    public EndGameReport(String partyName, Party party) {

        result = 0;
        retVal = new ArrayList<>();
        win = new JFrame("End Game Report for " + partyName + "?");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 2));

        // Member Panel
        JPanel partyPanel = new JPanel();
        partyPanel.setLayout(new FlowLayout());
        partyPanel.setBorder(new TitledBorder("Party Members"));

        ArrayList<String> bowlerNames = new ArrayList<>();
        for (Bowler bowler : party.getMembers()) {
            bowlerNames.add(bowler.getNickName());
        }

        JList memberList = new JList<>(bowlerNames.toArray());
        memberList.setFixedCellWidth(120);
        memberList.setVisibleRowCount(5);
        memberList.addListSelectionListener(this);
        JScrollPane partyPane = new JScrollPane(memberList);
        partyPanel.add(partyPane);

        partyPanel.add(memberList);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));

        printButton = new JButton("Print Report");
        JPanel printButtonPanel = new JPanel();
        printButtonPanel.setLayout(new FlowLayout());
        printButton.addActionListener(this);
        printButtonPanel.add(printButton);

        finished = new JButton("Finished");
        JPanel finishedPanel = new JPanel();
        finishedPanel.setLayout(new FlowLayout());
        finished.addActionListener(this);
        finishedPanel.add(finished);

        buttonPanel.add(printButton);
        buttonPanel.add(finished);

        // Clean up main panel
        colPanel.add(partyPanel);
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
     * Invoked when an action occurs.
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(printButton)) {
            // Add selected to the vector.
            retVal.add(selectedMember);
        }
        if (e.getSource().equals(finished)) {
            win.hide();
            result = 1;
        }

    }

  /**
   * Called whenever the value of the selection changes.
   * @param e the event that characterizes the change.
   */
    public void valueChanged(ListSelectionEvent e) {
        selectedMember = ((String) ((JList) e.getSource()).getSelectedValue());
    }

    /**
     * Returns the result of the report.
     * 
     * @return Returns 1 if finished, 0 otherwise.
     */
    public ArrayList<String> getResult() {
        while (result == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println("Interrupted");
            }
        }
        return retVal;
    }

    /**
     * Main entry-point for starting the EndGameReport.
     * 
     * @param args not used
     */
    public static void main(String[] args) {
        ArrayList<Bowler> bowlers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bowlers.add(new Bowler("aaaaa", "aaaaa", "aaaaa"));
        }
        Party party = new Party(bowlers);
        String partyName = "wank";
        EndGameReport e = new EndGameReport(partyName, party);
    }
}