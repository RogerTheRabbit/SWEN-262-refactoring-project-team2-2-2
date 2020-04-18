package GUI;

import LaneState.Lane;
import Observers.LaneObserver.LaneEvent;
import Observers.LaneObserver.LaneObserver;
import Observers.PinsetterObserver.Pinsetter;
import Observers.PinsetterObserver.PinsetterEvent;
import Observers.PinsetterObserver.PinsetterObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  Responsible for displaying the status of the bowling alleys.
 */
public class LaneStatusView implements ActionListener, LaneObserver, PinsetterObserver {

    private final JPanel jp;

    private final JLabel curBowler;
    private final JLabel pinsDown;
    private final JButton viewLane;
    private final JButton viewPinSetter;
    private final JButton maintenance;

    private final LaneView lv;
    private final Lane lane;

    private boolean laneShowing;

    LaneStatusView(Lane lane, int laneNum) {

        this.lane = lane;

        laneShowing = false;

        PinSetterView psv = new PinSetterView(laneNum);
        Pinsetter ps = lane.getPinsetter();
        ps.subscribe(psv);

        lv = new LaneView(lane, laneNum);
        lane.subscribe(lv);

        jp = new JPanel();
        jp.setLayout(new FlowLayout());
        JLabel cLabel = new JLabel("Now Bowling: ");
        curBowler = new JLabel("(no one)");
        JLabel pdLabel = new JLabel("Pins Down: ");
        pinsDown = new JLabel("0");

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        viewLane = new JButton("View Lane");
        JPanel viewLanePanel = new JPanel();
        viewLanePanel.setLayout(new FlowLayout());
        viewLane.addActionListener(this);
        viewLanePanel.add(viewLane);

        viewPinSetter = new JButton("Pinsetter");
        JPanel viewPinSetterPanel = new JPanel();
        viewPinSetterPanel.setLayout(new FlowLayout());
        viewPinSetter.addActionListener(this);
        viewPinSetterPanel.add(viewPinSetter);

        maintenance = new JButton("     ");
        maintenance.setBackground(Color.GREEN);
        JPanel maintenancePanel = new JPanel();
        maintenancePanel.setLayout(new FlowLayout());
        maintenance.addActionListener(this);
        maintenancePanel.add(maintenance);

        viewLane.setEnabled(false);
        viewPinSetter.setEnabled(false);

        buttonPanel.add(viewLanePanel);
        buttonPanel.add(viewPinSetterPanel);
        buttonPanel.add(maintenancePanel);

        jp.add(cLabel);
        jp.add(curBowler);
        jp.add(pdLabel);
        jp.add(pinsDown);

        jp.add(buttonPanel);

    }

    /**
     * Getter for the created JPanel
     * 
     * @return the JPanel that represents the status
     */
    JPanel showLane() {
        return jp;
    }

    /**
     * Invoked when an action occurs.
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        if (lane.isPartyAssigned()) {
            if (e.getSource().equals(viewPinSetter)) {
                toggleShowing();
            }
        }
        if (e.getSource().equals(viewLane)) {
            if (lane.isPartyAssigned()) {
                toggleShowing();
            }
        }
        if (e.getSource().equals(maintenance)) {
            if (lane.isPartyAssigned() && lane.isHalted()) {
                lane.maintenanceCallToggle();
                maintenance.setBackground(Color.GREEN);
            }
        }
    }

    private void toggleShowing() {
        if (!laneShowing) {
            lv.show();
            laneShowing = true;
        } else {
            lv.hide();
            laneShowing = false;
        }
    }

    /**
     * Receives and handles lane events.
     * 
     * @param le the event to receive
     */
    public void receiveLaneEvent(LaneEvent le) {
        curBowler.setText(le.getCurrentBowler().getNickName());
        if (le.isMechanicalProblem()) {
            maintenance.setBackground(Color.RED);
        }
        if (!lane.isPartyAssigned()) {
            viewLane.setEnabled(false);
            viewPinSetter.setEnabled(false);
        } else {
            viewLane.setEnabled(true);
            viewPinSetter.setEnabled(true);
        }
    }

    /**
     * receivePinsetterEvent()
     * <p>
     * defines the method for an object to receive a pinsetter event
     */
    public void receivePinsetterEvent(PinsetterEvent pe) {
        pinsDown.setText((Integer.valueOf(pe.totalPinsDown())).toString());
    }
}
