import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class OrarGridView extends JPanel {

    JTable table;
    JScrollPane scrollPane;

    public OrarGridView() {
        int ii = 3; int jj = 36;
        String[] columns = buildHeader(jj);
        String[][] data = new String[ii][jj];

        for(int i = 0;i < data.length; i++){
            for(int j = 0; j < data[0].length; j++){
                data[i][j] = "";
            }

        }

         table = new JTable(data,columns);
        OrarGridViewTableModel orarGridViewTableModel = new OrarGridViewTableModel(data,columns);
        table.setModel(orarGridViewTableModel);


        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                System.out.println(row + " " + col);
                orarGridViewTableModel.setCellEditable(row,col,!orarGridViewTableModel.getEditRights(row,col));
                orarGridViewTableModel.fireTableDataChanged();
            }
        });

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer(){
            //https://stackoverflow.com/questions/35431232/jtable-cell-text-color-changing
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
                if(orarGridViewTableModel.getEditRights(row,column)){
                    c.setBackground(Color.GREEN);
                }
                else{
                    c.setBackground(Color.RED);
                }
                return c;
            }
        };


        //TableModel tableModel = table.getModel();


        for (int columnIndex = 0; columnIndex < orarGridViewTableModel.getColumnCount(); columnIndex++)
        {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }



        scrollPane = new JScrollPane(table);

        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();

        resize();

        this.add(scrollPane);



    }

    public String[] buildHeader(int numOfDays){

        final String[] days = {"L", "M", "M", "J", "V"};

        String[] res = new String[numOfDays + 5];

        Integer week = 1;
        int dayIndex  = 0;
        while(numOfDays > 0){

            if (numOfDays >= 5) {

                for(int i = 0; i < 5; i++){
                    res[dayIndex] = week.toString() + days[i];
                    dayIndex++;
                    numOfDays--;

                }
                week++;

            }
            else{
                for (int i = 0; i < numOfDays; i++){
                    res[dayIndex] = week.toString() + days[i];
                    dayIndex++;
                    numOfDays--;

                }
                week++;
            }

        }
        return res;

    }


    public void resize(){
        //resize after changes have been made

        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();


        int numOfRows = table.getRowCount() + 2;
        int rowHeight = table.getRowHeight();



        int width = table.getColumnCount() * 22;



        int height = numOfRows * rowHeight + 50;
        scrollPane.setPreferredSize(new Dimension(width,height));
        this.setPreferredSize(new Dimension(width,height));
        this.revalidate();
        this.repaint();
    }
}
