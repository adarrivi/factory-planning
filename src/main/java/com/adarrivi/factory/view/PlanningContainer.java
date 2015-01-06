package com.adarrivi.factory.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.adarrivi.factory.annealing.AnnealingPlanningSolver;
import com.adarrivi.factory.problem.DefaultPlanningProblem;
import com.adarrivi.factory.problem.PlanningProblemProperties;

public class PlanningContainer extends JPanel {

    private static final long serialVersionUID = 1L;
    private AnnealingPlanningSolver solver;
    private JButton restartButton;
    private PlanningProblemProperties problem;

    public PlanningContainer() {
        setLayout(new BorderLayout(0, 0));
        DefaultPlanningProblem testProblem = new DefaultPlanningProblem();
        problem = testProblem.createDefaultProblem();
        solver = new AnnealingPlanningSolver(problem);
        createRestartButton();
        createPlanningPanel();
    }

    public void startNewRandomProblem() {
        Thread thread = new Thread(() -> {
            solver.solve();
            restartButton.setEnabled(true);
        });

        thread.start();
    }

    private void createPlanningPanel() {
        PlanningPanel planningPanel = new PlanningPanel(problem.getPlanning());
        add(planningPanel);
        solver.addObserver(planningPanel);
    }

    private void createRestartButton() {
        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                startNewRandomProblem();
                restartButton.setEnabled(false);

            }
        });
        panel.add(restartButton);
    }
}
