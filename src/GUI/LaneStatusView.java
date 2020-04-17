package GUI;
/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

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

public class LaneStatusView implements ActionListener, LaneObserver, PinsetterObserver {

    private final JPanel jp;

    private final JLabel curBowler;
    private final JLabel pinsDown;
    private final JButton viewLane;
    private final JButton viewPinSetter;
    private final JButton maintenance;

    private final PinSetterView psv;
    private final LaneView lv;
    private final Lane lane;
    int laneNum;

    boolean laneShowing;
    boolean psShowing;

    public LaneStatusView(Lane lane, int laneNum) {

        this.lane = lane;
        this.laneNum = laneNum;

        laneShowing = false;
        psShowing = false;

        psv = new PinSetterView(laneNum);
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

    public JPanel showLane() {
        return jp;
    }

    public void actionPerformed(ActionEvent e) {
        if (lane.isPartyAssigned()) {
            if (e.getSource().equals(viewPinSetter)) {
                if (!psShowing) {
                    psv.show();
                    psShowing = true;
                } else {
                    psv.hide();
                    psShowing = false;
                }
            }
        }
        if (e.getSource().equals(viewLane)) {
            if (lane.isPartyAssigned()) {
                if (!laneShowing) {
                    lv.show();
                    laneShowing = true;
                } else {
                    lv.hide();
                    laneShowing = false;
                }
            }
        }
        if (e.getSource().equals(maintenance)) {
            if (lane.isPartyAssigned()) {
                lane.maintenanceCallToggle();
                maintenance.setBackground(Color.GREEN);
            }
        }
    }

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

    public void receivePinsetterEvent(PinsetterEvent pe) {
        pinsDown.setText((Integer.valueOf(pe.totalPinsDown())).toString());
    }
}
