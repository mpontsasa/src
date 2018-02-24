import com.sun.jdi.ObjectCollectedException;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;

public class SubUnitView extends JPanel {

    private JScrollPane scrollPane;
    private JTable table;
    private SubUnitTableModel subUnitTableModel;
    private boolean edited;

    public SubUnitView(String header) {

        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        String[] columns= {"Nr.", "Element", "um", "cantitate unitara", "pret unitar", "pret total unitar",
                            "cantitate totala", "pret total", "furnizor"};
//        String[][] data = {
//                {"1", "oi", "kg", "51", "1.23", "845",
//                "78", "0", "matyi"},
//
//                {"2", "yeah", "meter", "5", "77", "777",
//                        "7777", "yess", "sasa"},
//                {"2", "yeah", "meter", "5", "77", "777",
//                        "7777", "yess", "sasa"}};

        String[][] data = {
                {"1", "", "", "", "", "",
                        "", "", ""}
        };

        edited = false;
        table = new JTable(data,columns);
        table.getTableHeader().setReorderingAllowed(false);
        subUnitTableModel = new SubUnitTableModel(data,columns);
        table.setModel(subUnitTableModel);


        Action action = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                TableCellListener tcl = (TableCellListener)e.getSource();
                if(subUnitTableModel.getRowCount() - 1 == tcl.getRow()){//last row was edited
                    //insert new row
                    String[] oldRow = (String[])getRowAt(tcl.getRow());
                    Integer newIndex = Integer.parseInt(oldRow[0]) + 1;
                    subUnitTableModel.addRowToBooleanMatrix();
                    subUnitTableModel.addRow(new String[]{newIndex.toString(), "", "", "", "", "", "", "", ""});

                }
                resizeSubunit();
            }
        };
        TableCellListener tcl = new TableCellListener(table, action);



        //ez nem megy :(
//        table.getModel().addTableModelListener(e->{
//            int modifiedColumn = e.getColumn();
//            if((
//                    (modifiedColumn == 1)||
//                    (modifiedColumn == 2)||
//                    (modifiedColumn == 3)||
//                    (modifiedColumn == 4)||
//                    (modifiedColumn == 8)) && (!edited)){
//
//                //row has been edited for the first time
//                //String[] = (String[])getRowAt()
//                System.out.println(subUnitTableModel.getEditedColumn());
//                System.out.println(subUnitTableModel.getEditedRow());
//
//            }
//        });

       // https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value
        JTableUtilities.setCellsAlignment(table, SwingConstants.CENTER);



        scrollPane = new JScrollPane(table){

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
        scrollPane.setWheelScrollingEnabled(false); ;



        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(600,450));



        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();

        int numOfRows = table.getRowCount() + 2;
        int rowHeight = table.getRowHeight();

        int width = 559;
        int height = numOfRows * rowHeight + 30;
        scrollPane.setPreferredSize(new Dimension(width,height));
        this.setPreferredSize(new Dimension(width,height));


        //this.setBackground(Color.BLACK);
        this.add(new JLabel(header));
        this.add(scrollPane);
    }

    public void resizeSubunit(){
        //resize after changes have been made

        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();


        int numOfRows = table.getRowCount() + 2;
        int rowHeight = table.getRowHeight();

        int width = 559;
        int height = numOfRows * rowHeight + 30;
        scrollPane.setPreferredSize(new Dimension(width,height));
        this.setPreferredSize(new Dimension(width,height));
        this.revalidate();
        this.repaint();
    }

    public Object[] getRowAt(int row) {
        Object[] result = new String[Finals.LENGTH_OF_SUB_UNIT_TABLE];

        for (int i = 0; i < Finals.LENGTH_OF_SUB_UNIT_TABLE; i++) {
            result[i] = table.getModel().getValueAt(row, i);
        }

        return result;
    }
}
