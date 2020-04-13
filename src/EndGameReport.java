


import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class EndGameReport implements ActionListener, ListSelectionListener {

    private JFrame win;
    private JButton printButton, finished;
    private JList memberList;
    private ArrayList<String> retVal;

    private int result;

    private String selectedMember;

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
            bowlerNames.add(bowler.getNick());
        }

        memberList = new JList(bowlerNames.toArray());
        memberList.setFixedCellWidth(120);
        memberList.setVisibleRowCount(5);
        memberList.addListSelectionListener(this);
        JScrollPane partyPane = new JScrollPane(memberList);
        // partyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        partyPanel.add(partyPane);

        partyPanel.add(memberList);

        // Button Panel
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));

        Insets buttonMargin = new Insets(4, 4, 4, 4);

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

    public void valueChanged(ListSelectionEvent e) {
        selectedMember = ((String) ((JList) e.getSource()).getSelectedValue());
    }

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
