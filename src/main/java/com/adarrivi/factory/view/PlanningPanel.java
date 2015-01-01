package com.adarrivi.factory.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.adarrivi.factory.planning.Planning;

public class PlanningPanel extends JPanel implements Observer {
    private static final int BORDER_OFFSET = 20;
    private static final long serialVersionUID = 1L;

    private Graphics drawer;
    private Planning planning;
    private JTable gridTable;

    public PlanningPanel(int mapSize, int xPos, int yPos) {
        int realPanelSize = (BORDER_OFFSET * 2) + mapSize;
        String[] columnNames = { "Worker", "Last Name", "Sport", "# of Years", "Vegetarian" };

        Object[][] data = { { "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
                { "John", "Doe", "Rowing", new Integer(3), new Boolean(true) },
                { "Sue", "Black", "Knitting", new Integer(2), new Boolean(false) },
                { "Jane", "White", "Speed reading", new Integer(20), new Boolean(true) },
                { "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) } };

        gridTable = new JTable(data, columnNames);
        gridTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        gridTable.setFillsViewportHeight(true);

        setBounds(xPos, yPos, realPanelSize, realPanelSize);

        // Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(gridTable);

        // Add the scroll pane to this panel.
        add(scrollPane);

    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawer = graphics;
    }

    int val;

    @Override
    public void update(Observable o, Object currentPlanning) {
        planning = (Planning) currentPlanning;
        gridTable.setValueAt(val++, 1, 1);
        repaint();
    }

}
