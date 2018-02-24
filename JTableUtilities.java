
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class JTableUtilities
{


    public static void setCellsAlignment(JTable table, int alignment, DefaultTableModel tableModel)
    {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer(){
            //https://stackoverflow.com/questions/35431232/jtable-cell-text-color-changing
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
                if(tableModel.isCellEditable(row,column)){
                    c.setForeground(Color.GREEN);
                }
                else{
                    c.setForeground(Color.RED);
                }
                return c;
            }
        };
        rightRenderer.setHorizontalAlignment(alignment);

        //TableModel tableModel = table.getModel();


        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++)
        {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }
    }
}
