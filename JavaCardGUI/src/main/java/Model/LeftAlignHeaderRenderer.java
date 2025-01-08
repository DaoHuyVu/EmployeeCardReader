/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author KIDDO
 */
public class LeftAlignHeaderRenderer extends DefaultTableCellRenderer {
    public LeftAlignHeaderRenderer() {
        setHorizontalAlignment(LEFT); // Align header content to the right
    }

    @Override
    public Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setFont(getFont().deriveFont(getFont().getStyle() | java.awt.Font.BOLD)); 
        return this;
    }
}
