package com.adarrivi.factory.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.adarrivi.factory.annealing.AnnealingPlanningSolver;
import com.adarrivi.factory.problem.DefaultPlanningProblem;
import com.adarrivi.factory.problem.PlanningProblemProperties;
import com.adarrivi.factory.view.info.ScoreInfoPanel;
import com.adarrivi.factory.view.planning.GridPlanningPanel;

public class MainPlanningPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private AnnealingPlanningSolver solver;
    private JButton restartButton;
    private PlanningProblemProperties problem;

    public MainPlanningPanel() {
        setLayout(new BorderLayout(0, 0));
        DefaultPlanningProblem testProblem = new DefaultPlanningProblem();
        problem = testProblem.createDefaultProblem();
        solver = new AnnealingPlanningSolver(problem);
        createRestartPanel();
        createGridPlanningPanel();
        createScoreInfoPanel();
    }

    private void createRestartPanel() {
        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                startNewRandomProblem();
                restartButton.setText("Looking for the best solution...");
                restartButton.setEnabled(false);

            }
        });
        panel.add(restartButton);
    }

    private void createGridPlanningPanel() {
        GridPlanningPanel gridPlanningPanel = new GridPlanningPanel(problem.getPlanning());
        solver.addObserver(gridPlanningPanel);
        add(gridPlanningPanel, BorderLayout.CENTER);
    }

    private void createScoreInfoPanel() {
        ScoreInfoPanel panel = new ScoreInfoPanel();
        solver.addObserver(panel);
        add(panel, BorderLayout.SOUTH);
    }

    public void startNewRandomProblem() {
        Thread thread = new Thread(() -> {
            solver.solve();
            restartButton.setText("Restart");
            restartButton.setEnabled(true);
        });

        thread.start();
    }
}
