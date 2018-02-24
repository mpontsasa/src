import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;


//CURRENTLY OBSOLETE
public class ExtendedSubUnitView extends JPanel {

    private JScrollPane scrollPane;
    private JTable table;
    private SubUnitTableModel subUnitTableModel;
    private String header;

    public ExtendedSubUnitView(String header) {
        //URES KONSTRUKTOR

        this.header = header;
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));


        String[][] data = {{"1", "", "", "", "", "", "", "", "", "", ""}};
        setupTable(data);


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


        adjustSizeAndPadding();


    }

    public ExtendedSubUnitView(String header,UnitView parent, int parentIndex, int myIndex) {
        //NEM URES KONSTRUKTOR

        this.header = header;
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        //LOAD DATA
        TaskTableCreator ttc = new TaskTableCreator(parent.getParent().getMyModel());
        String[][] data = ttc.getSubUnitTs()[parentIndex][myIndex];//kinyerem a sorok matrixat

        if(data.length == 0){//ha nincs egy sor sem beszurom az elso sort
            data = new String[][]{{"1","","","","","","","","","",""}};
            setupTable(data);
        }
        else{//ha van sor, betesze  oket s beszurok egy ures sort
            setupTable(data);
            Integer newIndex = data.length + 1;
            insertBlankRow(newIndex);
        }
        //END OF LOAD


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


        adjustSizeAndPadding();


    }


    public void setupTable(String[][] data){

        String[] columns = Finals.EXTENDED_SUB_UNIT_TABLE_HEADER;



        table = new JTable(data,columns);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSelectionAllowed(false);


        subUnitTableModel = new SubUnitTableModel(data,columns);
        table.setModel(subUnitTableModel);

        // https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value
        JTableUtilities.setCellsAlignment(table, SwingConstants.CENTER,subUnitTableModel);


        Action action = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                TableCellListener tcl = (TableCellListener)e.getSource();
                if(subUnitTableModel.getRowCount() - 1 == tcl.getRow()){//last row was edited
                    //insert new row
                    String[] oldRow = (String[])getRowAt(tcl.getRow());
                    Integer newIndex = Integer.parseInt(oldRow[0]) + 1;
                    insertBlankRow(newIndex);

                }
                resizeSubunit();
            }
        };
        TableCellListener tcl = new TableCellListener(table, action);

        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(600,450));



        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
    }




    public void adjustSizeAndPadding(){
        //this.setBackground(Color.BLACK);
        this.add(new JLabel(header));
        this.add(scrollPane);

        JLabel pretTotalLabel = new JLabel("PRET TOTAL UNITAR: 256.2569");
        pretTotalLabel.setFont(new Font("Arial",Font.PLAIN,10));
        this.add(pretTotalLabel);

        JPanel paddingPanel = new JPanel();
        this.add(paddingPanel);
        //this.setBorder(new LineBorder(Color.black));

        resizeSubunit();
    }

    public void insertBlankRow(Integer newIndex){
        subUnitTableModel.addRowToBooleanMatrix();
        subUnitTableModel.addRow(new String[]{newIndex.toString(), "", "", "", "", "", "", "", "", "", ""});
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
