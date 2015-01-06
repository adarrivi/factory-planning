package com.adarrivi.factory.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.adarrivi.factory.planning.Worker;

public class WorkerCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        // Cells are by default rendered as a JLabel.
        JLabel cellLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        Object cellValue = table.getModel().getValueAt(row, col);
        if (cellValue instanceof Worker) {
            Worker worker = (Worker) cellValue;
            cellLabel.setText(getWorkerText(worker));
        }
        cellLabel.setBackground(Color.WHITE);
        return cellLabel;

    }

    private String getWorkerText(Worker worker) {
        return worker.getName() + " [" + String.join(",", worker.getAllowedLines()) + "]";
    }
}
