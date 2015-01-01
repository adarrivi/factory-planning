package com.adarrivi.factory.view;

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
        setLayout(null);
        setVisible(true);
        DefaultPlanningProblem testProblem = new DefaultPlanningProblem();
        problem = testProblem.createDefaultProblem();
        solver = new AnnealingPlanningSolver(problem);
    }

    public void startNewRandomProblem() {
        Thread thread = new Thread(() -> {
            solver.solve();
            restartButton.setEnabled(true);
        });

        thread.start();
    }

    public void draw() {
        createRestartButton();
        createPlanningPanel();
    }

    private void createPlanningPanel() {
        PlanningPanel planningPanel = new PlanningPanel(10, 34, 800, 200, problem.getPlanning());
        add(planningPanel);
        solver.addObserver(planningPanel);
    }

    private void createRestartButton() {
        restartButton = new JButton("Restart");
        restartButton.setBounds(10, 11, 89, 23);
        restartButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                startNewRandomProblem();
                restartButton.setEnabled(false);

            }
        });
        add(restartButton);
    }
}
