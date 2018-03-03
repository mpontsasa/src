import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class OrarGridView extends JPanel {

    JTable gridTable;
    JScrollPane gridScrollPane;
    OrarGridViewTableModel orarGridViewTableModel;
    private Controller myController;

//    JTable resumeTable;
//    JScrollPane resumeScrollPane;


    public OrarGridView(ScheduleTableCreator stc, Controller myController) {

        this.myController = myController;

        int numOfRows = stc.getTasksTable().length;
        int numOfColumns = stc.getMaxDays() + Finals.EXTRA_ORAR_DAYS;//+ ket het

        String[] columns = buildHeader(numOfColumns);
        String[][] data = new String[numOfRows][numOfColumns];

        for(int i = 0;i < data.length; i++){
            for(int j = 0; j < data[0].length; j++){
                data[i][j] = "";
            }

        }

         gridTable = new JTable(data,columns);
        orarGridViewTableModel = new OrarGridViewTableModel(data,columns);
        orarGridViewTableModel.setEditable_cells(stc.getWeeksTable());
        gridTable.setModel(orarGridViewTableModel);


        gridTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = gridTable.rowAtPoint(evt.getPoint());
                int col = gridTable.columnAtPoint(evt.getPoint());

                notifyController(row,col,!orarGridViewTableModel.getEditRights(row,col));

                
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
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        };


        //TableModel tableModel = table.getModel();


        for (int columnIndex = 0; columnIndex < orarGridViewTableModel.getColumnCount(); columnIndex++)
        {
            gridTable.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }



        gridScrollPane = new JScrollPane(gridTable);

        gridTable.setFillsViewportHeight(true);
        gridTable.setRowSelectionAllowed(false);
        gridTable.setColumnSelectionAllowed(false);

        gridTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(gridTable);
        tca.adjustColumns();

        resize();

        this.add(gridScrollPane,BorderLayout.CENTER);



    }

    private void notifyController(int row, int col, boolean value){
        myController.orarGridChanged(row,col,value);
    }

    public String[] buildHeader(int numOfDays){

        final String[] days = {"L", "M", "M", "J", "V"};

        String[] res = new String[numOfDays];
//        res[0] = "Nr.";
//        res[1] = "Articol de lucrare";
//        res[2] = "um";
//        res[3] = "cantitate";
//        res[4] = "ore";

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

        TableColumnAdjuster tca = new TableColumnAdjuster(gridTable);
        tca.adjustColumns();


        int numOfRows = gridTable.getRowCount() + 2;
        int rowHeight = gridTable.getRowHeight();



        int width = gridTable.getColumnCount() * 22;



//        int height = numOfRows * rowHeight + 600;
//        height = this.getHeight();
//        gridScrollPane.setPreferredSize(new Dimension(width,height));

        //this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.BLACK);
        this.revalidate();
        this.repaint();
    }

    public JScrollPane getGridScrollPane() {
        return gridScrollPane;
    }
}
