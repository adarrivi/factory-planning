package com.adarrivi.factory.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.adarrivi.factory.annealing.PlanningSolution;
import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

public class PlanningPanel extends JPanel implements Observer {
    private static final long serialVersionUID = 1L;

    private Planning planning;
    private JTable gridTable;
    private JLabel infoLabel;

    public PlanningPanel(Planning initialPlanning) {
        planning = initialPlanning;
        setLayout(new BorderLayout(0, 0));
        createGridPanel();
        createInfoLabels();
    }

    private void createInfoLabels() {
        JPanel panel = new JPanel();
        add(panel, BorderLayout.SOUTH);
        infoLabel = new JLabel("...");
        panel.add(infoLabel);
    }

    private void createGridPanel() {
        gridTable = new JTable(planning.getAllWorkers().size() + 1, planning.getAllDays().size() + 1);
        drawWorkers();
        add(gridTable, BorderLayout.CENTER);
    }

    private void drawWorkers() {
        drawHeaders();
        List<Worker> workers = planning.getAllWorkers();
        List<Integer> allDays = planning.getAllDays();
        WorkerCellRenderer workerCellRenderer = new WorkerCellRenderer();
        WorkDayCellRenderer workDayCellRenderer = new WorkDayCellRenderer();
        for (int rowIndex = 1; rowIndex <= workers.size(); rowIndex++) {
            Worker worker = workers.get(rowIndex - 1);
            gridTable.setValueAt(worker, rowIndex, 0);
            gridTable.getColumnModel().getColumn(0).setCellRenderer(workerCellRenderer);
            for (int columnIndex : allDays) {
                WorkerDay workerDay = worker.getDay(columnIndex);
                gridTable.setValueAt(workerDay, rowIndex, columnIndex);
                gridTable.getColumnModel().getColumn(columnIndex).setCellRenderer(workDayCellRenderer);
            }
        }
    }

    private void drawHeaders() {
        gridTable.setValueAt("Workers", 0, 0);
        List<Integer> allDays = planning.getAllDays();
        for (int day : allDays) {
            gridTable.setValueAt("Day " + day, 0, day);
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
    }

    @Override
    public void update(Observable o, Object currentSolution) {
        PlanningSolution solution = (PlanningSolution) currentSolution;
        planning = solution.getPlanning();
        drawWorkers();
        infoLabel.setText("Best solution with score " + solution.getScore() + ", at temperature: " + solution.getTemperature()
                + " (plannings created: " + solution.getPlanningsCreatedSoFar() + ")");
        repaint();
    }

}
