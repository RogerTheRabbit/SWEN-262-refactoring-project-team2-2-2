package temp;

/*
 * temp.PinSetterView/.java
 *
 * Version:
 *   $Id$
 *
 * Revision:
 *   $Log$
 */

/**
 * This class creates a GUI to represent the Pins.
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PinSetterView implements PinsetterObserver {

    private ArrayList<JLabel> labelList = new ArrayList<>();
    private JPanel secondRoll;

    /**
     * Constructs a Pin Setter GUI displaying which roll it is with yellow boxes
     * along the top (1 box for first roll, 2 boxes for second) and displays the
     * pins as numbers in this format:
     *
     *                7   8   9   10
     *                  4   5   6
     *                    2   3
     *                      1
     *
     */

    private JFrame frame;

    public PinSetterView(int laneNum) {

        frame = new JFrame("Lane " + laneNum + ":");

        Container cpanel = frame.getContentPane();

        JPanel pins = new JPanel();

        pins.setLayout(new GridLayout(4, 7));

        // ********************Top of GUI indicates first or second roll

        JPanel top = new JPanel();

        JPanel firstRoll = new JPanel();
        firstRoll.setBackground(Color.yellow);

        secondRoll = new JPanel();
        secondRoll.setBackground(Color.black);

        top.add(firstRoll, BorderLayout.WEST);

        top.add(secondRoll, BorderLayout.EAST);

        // ******************************************************************

        // **********************Grid of the pins**************************

        ArrayList<JPanel> panelList = new ArrayList<>();

        // Creates all the panels and labels for the individual pins
        for(int i = 0; i < 10; i++){
            String number = Integer.toString(i + 1);
            JPanel panel = new JPanel();
            JLabel label = new JLabel(number);
            panel.add(label);
            labelList.add(label);
            panelList.add(panel);
        }

        // ******************************Fourth Row**************

        pins.add(panelList.get(6));
        pins.add(new JPanel());
        pins.add(panelList.get(7));
        pins.add(new JPanel());
        pins.add(panelList.get(8));
        pins.add(new JPanel());
        pins.add(panelList.get(9));

        // *****************************Third Row***********

        pins.add(new JPanel());
        pins.add(panelList.get(3));
        pins.add(new JPanel());
        pins.add(panelList.get(4));
        pins.add(new JPanel());
        pins.add(panelList.get(5));

        // *****************************Second Row**************

        pins.add(new JPanel());
        pins.add(new JPanel());
        pins.add(new JPanel());
        pins.add(panelList.get(1));
        pins.add(new JPanel());
        pins.add(panelList.get(2));
        pins.add(new JPanel());
        pins.add(new JPanel());

        // ******************************First Row*****************

        pins.add(new JPanel());
        pins.add(new JPanel());
        pins.add(new JPanel());
        pins.add(panelList.get(0));
        pins.add(new JPanel());
        pins.add(new JPanel());
        pins.add(new JPanel());
        // *********************************************************

        top.setBackground(Color.black);

        cpanel.add(top, BorderLayout.NORTH);

        pins.setBackground(Color.black);
        pins.setForeground(Color.yellow);

        cpanel.add(pins, BorderLayout.CENTER);

        frame.pack();

    }

    /**
     * This method receives a pinsetter event. The event is the current state of the
     * PinSetter and the method changes how the GUI looks accordingly. When pins are
     * "knocked down" the corresponding label is grayed out. When it is the second
     * roll, it is indicated by the appearance of a second yellow box at the top.
     *
     * @param pe The state of the pinsetter is sent in this event.
     */

    public void receivePinsetterEvent(PinsetterEvent event) {
        if (!(event.isFoulCommitted())) {
            JLabel tempPin;
            for (int i = 0; i < 10; i++) {
                boolean pin = event.pinKnockedDown(i);
                tempPin = labelList.get(i);
                if (pin) {
                    tempPin.setForeground(Color.lightGray);
                }
            }
        }
        if (event.getThrowNumber() == 1) {
            secondRoll.setBackground(Color.yellow);
        }
        if (event.pinsDownOnThisThrow() == -1) {
            for (int i = 0; i != 10; i++) {
                (labelList.get(i)).setForeground(Color.black);
            }
            secondRoll.setBackground(Color.black);
        }
    }

    /**
     * This function is called to show the frame that houses the pins
     */
    public void show() {
        frame.show();
    }

    /**
     * This hides the frame that houses the pins
     */
    public void hide() {
        frame.hide();
    }

}
