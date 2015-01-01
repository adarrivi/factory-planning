package com.adarrivi.factory.view;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PlanningSwingPanel extends JFrame {
    private static final int PLANNING_SIZE = 300;
    private static final long serialVersionUID = 1L;
    private static final PlanningSwingPanel INSTANCE = new PlanningSwingPanel();

    private static final int X_OFFSET = 100;
    private static final int Y_OFFSET = 130;

    public static void main(String[] args) {
        INSTANCE.run();
        SwingUtilities.invokeLater(() -> INSTANCE.setVisible(true));
    }

    private void run() {
        createPanel();
    }

    private void createPanel() {
        setTitle("Factory Planning");
        Rectangle dialogPos = new Rectangle(100, 100, 300 + X_OFFSET, PLANNING_SIZE + Y_OFFSET);

        setBounds(dialogPos);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        PlanningContainer tspContainer = new PlanningContainer();
        tspContainer.draw();
        getContentPane().add(tspContainer);
    }
}
