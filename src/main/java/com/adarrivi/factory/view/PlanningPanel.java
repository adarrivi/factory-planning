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
    private static final int BORDER_OFFSET = 20;
    private static final long serialVersionUID = 1L;

    private Planning planning;
    private JTable gridTable;
    private JLabel infoLabel;

    public PlanningPanel(int xPos, int yPos, int width, int height, Planning initialPlanning) {
        planning = initialPlanning;
        setLayout(new BorderLayout(0, 0));
        setBounds(xPos, yPos, (BORDER_OFFSET * 2) + width, (BORDER_OFFSET * 2) + height);
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
        // Create the scroll pane and add the table to it.
        drawWorkers();
        // Add the scroll pane to this panel.
        add(gridTable, BorderLayout.CENTER);
    }

    private void drawWorkers() {
        drawHeaders();
        List<Worker> workers = planning.getAllWorkers();
        List<Integer> allDays = planning.getAllDays();
        for (int rowIndex = 1; rowIndex <= workers.size(); rowIndex++) {
            Worker worker = workers.get(rowIndex - 1);
            gridTable.setValueAt(worker.getName(), rowIndex, 0);
            for (int columnIndex : allDays) {
                WorkerDay workerDay = worker.getDay(columnIndex);
                gridTable.setValueAt(getCellValue(workerDay), rowIndex, columnIndex);
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

    private String getCellValue(WorkerDay workerDay) {
        if (workerDay.isFree()) {
            return "F";
        }
        if (workerDay.isHoliday()) {
            return "H";
        }
        return workerDay.getLine() + "-" + workerDay.getShiftType().name();
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
