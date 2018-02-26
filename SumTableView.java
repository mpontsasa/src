import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;

public class SumTableView extends JPanel {
    private JScrollPane totalScrollPane;
    private JScrollPane MMUTScrollPane;
    private JTable totalsTable;
    private JTable MMUTTable;// material manopera utilaj transport
    private TaskView parent;

    public SumTableView(TaskView parent) {

        this.parent = parent;

        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        TaskTableCreator taskTableCreator = new TaskTableCreator(parent.getMyModel());

        String[] columns = Finals.SUM_TOTAL_TABLE_HEADER;
        String[][] data = taskTableCreator.getSumTotalT();


        String [] MMUTColumns = Finals.SUM_MMUT_TABLE_HEADER;
        String[][] MMUTData = taskTableCreator.getSumMMUTT();

        totalsTable = new JTable(data,columns);
        totalsTable.getTableHeader().setReorderingAllowed(false);
        totalsTable.setRowSelectionAllowed(false);

        SumsTableModel stm = new SumsTableModel(data,columns);
        totalsTable.setModel(stm);//bellitom azt hogy csak a szrozokat lehet editalni



        //jelezd a valtozasokat a szorzokon
        Action action = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                TableCellListener tcl = (TableCellListener)e.getSource();
                amplifiersEdited(tcl.getColumn(), (String)tcl.getNewValue());

            }
        };
        TableCellListener tcl = new TableCellListener(totalsTable, action);


        MMUTTable = new JTable(MMUTData,MMUTColumns);
        MMUTTable.getTableHeader().setReorderingAllowed(false);
        MMUTTable.setRowSelectionAllowed(false);


        //set the editing of cells in mmutable to false
        MMUTTableModel mmutTableModel = new MMUTTableModel(MMUTData,MMUTColumns);
        MMUTTable.setModel(mmutTableModel);
        //MMUTTable.setDefaultEditor(Object.class, null);

        //

        // https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value
        JTableUtilities.setCellsAlignment(totalsTable, SwingConstants.CENTER,stm);
        JTableUtilities.setCellsAlignment(MMUTTable, SwingConstants.CENTER,mmutTableModel);



        totalScrollPane = new JScrollPane(totalsTable){

            @Override
            protected void processMouseWheelEvent(MouseWheelEvent e) {
                //https://stackoverflow.com/questions/12911506/why-jscrollpane-does-not-react-to-mouse-wheel-events
                if (!isWheelScrollingEnabled()) {
                    if (getParent() != null)
                        getParent().dispatchEvent(
                                SwingUtilities.convertMouseEvent(this, e, getParent()));
                    return;
                }
                super.processMouseWheelEvent(e);
            }

        };
        totalScrollPane.setWheelScrollingEnabled(false); ;

        MMUTScrollPane = new JScrollPane(MMUTTable){

            @Override
            protected void processMouseWheelEvent(MouseWheelEvent e) {
                //https://stackoverflow.com/questions/12911506/why-jscrollpane-does-not-react-to-mouse-wheel-events
                if (!isWheelScrollingEnabled()) {
                    if (getParent() != null)
                        getParent().dispatchEvent(
                                SwingUtilities.convertMouseEvent(this, e, getParent()));
                    return;
                }
                super.processMouseWheelEvent(e);
            }

        };
        MMUTScrollPane.setWheelScrollingEnabled(false); ;




        totalsTable.setFillsViewportHeight(true);
        totalsTable.setPreferredScrollableViewportSize(new Dimension(600,450));

//        totalsTable.getModel().addTableModelListener(e -> {
//            System.out.println(e.getFirstRow() + " " + e.getColumn() + " " + e.getType());
//
//        });

        MMUTTable.setFillsViewportHeight(true);
        MMUTTable.setPreferredScrollableViewportSize(new Dimension(600,450));



        totalsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(totalsTable);
        tca.adjustColumns();

        MMUTTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tca = new TableColumnAdjuster(MMUTTable);
        tca.adjustColumns();

        int numOfRows = totalsTable.getRowCount() + 2;
        int rowHeight = totalsTable.getRowHeight();

        int width = 559;
        int height = numOfRows * rowHeight * 2 + 10;
        totalScrollPane.setPreferredSize(new Dimension(width,height));
        MMUTScrollPane.setPreferredSize(new Dimension(width,100));
        this.setPreferredSize(new Dimension(width,height));


        //this.setBackground(Color.BLACK);

        this.add(MMUTScrollPane);
        this.add(totalScrollPane);
    }

    public void amplifiersEdited(int amplifierIndex, String data){
        parent.amplifiersEdited(amplifierIndex,data);
    }

}
