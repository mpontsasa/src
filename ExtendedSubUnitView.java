import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;

public class ExtendedSubUnitView extends JPanel {

    private JScrollPane scrollPane;
    private JTable table;
    private SubUnitTableModel subUnitTableModel;

    public ExtendedSubUnitView(String header) {

        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        String[] columns = Finals.EXTENDED_SUB_UNIT_TABLE_HEADER;
        String[][] data = {{"1", "", "", "", "", "", "", "", "", "", ""}};

        table = new JTable(data,columns);
        table.getTableHeader().setReorderingAllowed(false);


        subUnitTableModel = new SubUnitTableModel(data,columns);
        table.setModel(subUnitTableModel);

        // https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value
        JTableUtilities.setCellsAlignment(table, SwingConstants.CENTER);


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
                    subUnitTableModel.addRow(new String[]{newIndex.toString(), "", "", "", "", "", "", "", "", ""});

                }
                resizeSubunit();
            }
        };
        TableCellListener tcl = new TableCellListener(table, action);



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
