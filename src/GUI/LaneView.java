package GUI;

/*
 *  constructs a prototype LaneState.Lane View
 *
 */

import FileWriting.Bowler;
import LaneState.Lane;
import Observers.LaneObserver.LaneEvent;
import Observers.LaneObserver.LaneObserver;
import LaneState.Party;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * This class is responsible for displaying and handling the 
 * lane and it's events
 */
public class LaneView implements LaneObserver, ActionListener {

    private boolean initDone = true;

    JFrame frame;
    Container cPanel;
    ArrayList<Bowler> bowlers;

    JPanel[][] balls;
    JLabel[][] ballLabel;
    JPanel[][] scores;
    JLabel[][] scoreLabel;
    JPanel[][] ballGrid;
    JPanel[] pins;

    JButton maintenance;
    Lane lane;

    public LaneView(Lane lane, int laneNum) {

        this.lane = lane;

        frame = new JFrame("Lane " + laneNum + ":");
        cPanel = frame.getContentPane();
        cPanel.setLayout(new BorderLayout());

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });

        cPanel.add(new JPanel());

    }

    public void show() {
        frame.setVisible(true);
    }

    public void hide() {
        frame.dispose();
    }

    /**
     * Creates a new frame for the party
     * 
     * @param party party to make frame for
     * @return a new JPanel representing the new frame
     */
    private JPanel makeFrame(Party party) {

        initDone = false;
        bowlers = party.getMembers();
        int numBowlers = bowlers.size();

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(0, 1));

        balls = new JPanel[numBowlers][23];
        ballLabel = new JLabel[numBowlers][23];
        scores = new JPanel[numBowlers][10];
        scoreLabel = new JLabel[numBowlers][10];
        ballGrid = new JPanel[numBowlers][10];
        pins = new JPanel[numBowlers];

        for (int i = 0; i < numBowlers; i++) {
            for (int j = 0; j != 23; j++) {
                ballLabel[i][j] = new JLabel(" ");
                balls[i][j] = new JPanel();
                balls[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                balls[i][j].add(ballLabel[i][j]);
            }
        }

        for (int i = 0; i < numBowlers; i++) {
            for (int j = 0; j != 9; j++) {
                ballGrid[i][j] = new JPanel();
                ballGrid[i][j].setLayout(new GridLayout(0, 3));
                ballGrid[i][j].add(new JLabel("  "), BorderLayout.EAST);
                ballGrid[i][j].add(balls[i][2 * j], BorderLayout.EAST);
                ballGrid[i][j].add(balls[i][2 * j + 1], BorderLayout.EAST);
            }
            int j = 9;
            ballGrid[i][j] = new JPanel();
            ballGrid[i][j].setLayout(new GridLayout(0, 3));
            ballGrid[i][j].add(balls[i][2 * j]);
            ballGrid[i][j].add(balls[i][2 * j + 1]);
            ballGrid[i][j].add(balls[i][2 * j + 2]);
        }

        for (int i = 0; i != numBowlers; i++) {
            pins[i] = new JPanel();
            pins[i].setBorder(BorderFactory.createTitledBorder(bowlers.get(i).getNickName()));
            pins[i].setLayout(new GridLayout(0, 10));
            for (int k = 0; k != 10; k++) {
                scores[i][k] = new JPanel();
                scoreLabel[i][k] = new JLabel("  ", SwingConstants.CENTER);
                scores[i][k].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                scores[i][k].setLayout(new GridLayout(0, 1));
                scores[i][k].add(ballGrid[i][k], BorderLayout.EAST);
                scores[i][k].add(scoreLabel[i][k], BorderLayout.SOUTH);
                pins[i].add(scores[i][k], BorderLayout.EAST);
            }
            panel.add(pins[i]);
        }

        initDone = true;
        return panel;
    }

    /**
     * Receives and handles lane events.
     * 
     * @param laneEvent the event to receive
     */
    public void receiveLaneEvent(LaneEvent laneEvent) {
        if (lane.isPartyAssigned()) {
            while (!initDone) {
                try {
                    Thread.sleep(1);
                } catch (Exception ignored) {
                }
            }

            if (laneEvent.getFrameNum() == 1 && laneEvent.getBallNum() == 0 && laneEvent.getIndex() == 0) {
                System.out.println("Making the frame.");
                cPanel.removeAll();
                cPanel.add(makeFrame(laneEvent.getParty()), "Center");

                // Button Panel
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout());

                maintenance = new JButton("Maintenance Call");
                JPanel maintenancePanel = new JPanel();
                maintenancePanel.setLayout(new FlowLayout());
                maintenance.addActionListener(this);
                maintenancePanel.add(maintenance);

                buttonPanel.add(maintenancePanel);

                cPanel.add(buttonPanel, "South");

                frame.pack();

            }
            
            int[][] laneEventScores = laneEvent.getCumuliScore();
            ArrayList<Bowler> bowlers = laneEvent.getParty().getMembers();
            int k = laneEvent.getIndex();
            Bowler bowler = bowlers.get(k);
            for (int i = 0; i < laneEvent.getFrameNum(); i++) {
                if (laneEventScores[k][i] != 0) {
                    scoreLabel[k][i].setText((Integer.valueOf(laneEventScores[k][i])).toString());
                }
            }
            for (int i = 0; i < 21; i++) {
                int[] currentRow = laneEvent.getScore().getAllThrows(bowler);
                if (currentRow[i] != -1) {
                    if (currentRow[i] == 10 && (i % 2 == 0 || i == 19)) {
                        ballLabel[k][i].setText("X");
                    } else if (i > 0 && currentRow[i] + currentRow[i - 1] == 10 && i % 2 == 1) {
                        ballLabel[k][i].setText("/");
                    } else if (currentRow[i] == -2) {
                        ballLabel[k][i].setText("F");
                    } else {
                        ballLabel[k][i].setText((Integer.valueOf(currentRow[i])).toString());
                    }
                }
            }
        }
    }

    /**
     * Invoked when an action occurs.
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(maintenance)) {
            if (!lane.isHalted()){
                lane.maintenanceCallToggle();
            }
        }
    }
}
