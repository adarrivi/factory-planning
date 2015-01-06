package com.adarrivi.factory.view;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PlanningSwingPanel extends JFrame {
    private static final int PLANNING_WIDTH = 800;
    private static final int PLANNING_HEIGHT = 450;
    private static final long serialVersionUID = 1L;
    private static final PlanningSwingPanel INSTANCE = new PlanningSwingPanel();

    public static void main(String[] args) {
        INSTANCE.run();
        SwingUtilities.invokeLater(() -> INSTANCE.setVisible(true));
    }

    private void run() {
        createPanel();
    }

    private void createPanel() {
        setTitle("Factory Planning");
        Rectangle dialogPos = new Rectangle(0, 0, PLANNING_WIDTH, PLANNING_HEIGHT);

        setBounds(dialogPos);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        MainPlanningPanel tspContainer = new MainPlanningPanel();
        getContentPane().add(tspContainer);
    }
}
