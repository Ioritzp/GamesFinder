package Utilities;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class HyperlinkRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row, int column){
        super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
        if(value!=null){
            String url = value.toString();
            setText("<html><a href='" + url + "' style='color: #1a0dab; text-decoration: underline;'>" + url + "</a></html>");

        }
        return this;
    }




}
