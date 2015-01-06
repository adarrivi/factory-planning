package com.adarrivi.factory.view.planning;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.adarrivi.factory.planning.ShiftType;
import com.adarrivi.factory.planning.WorkerDay;

public class WorkDayCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        // Cells are by default rendered as a JLabel.
        JLabel cellLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        Object cellValue = table.getModel().getValueAt(row, col);
        if (cellValue instanceof WorkerDay) {
            WorkerDay workerDay = (WorkerDay) cellValue;
            cellLabel.setBackground(getCellColor(workerDay));
            cellLabel.setText(getCellValue(workerDay));
        } else {
            cellLabel.setBackground(Color.WHITE);
        }
        cellLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return cellLabel;

    }

    private Color getCellColor(WorkerDay workerDay) {
        if (workerDay.isHoliday()) {
            return new Color(197, 199, 189);
        }
        if (ShiftType.EARLY.equals(workerDay.getShiftType())) {
            return new Color(200, 240, 2);
        }
        if (ShiftType.LATE.equals(workerDay.getShiftType())) {
            return new Color(130, 156, 0);
        }
        return new Color(200, 250, 249);
    }

    private String getCellValue(WorkerDay workerDay) {
        if (workerDay.isFree()) {
            return "F";
        }
        if (workerDay.isHoliday()) {
            return "H";
        }
        return workerDay.getLine();
    }
}
