import com.sun.jdi.ObjectCollectedException;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
    private String header;
    private UnitView parent;
    private int myIndex;
    private int type;

    public SubUnitView(String header, int type) {
        //URES KONSTRUKTOR
        this.type = type;
        this.header = header;


        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        String[][] data;
        switch (type){
            case Finals.SUB_UNIT_TYPE:
                 data = new String[][]{{"1", "", "", "", "", "", "", "", ""}};
                break;
            case Finals.EXTENDED_SUB_UNIT_TYPE:
                data = new String[][]{{"1", "", "", "", "", "", "", "", "", "", ""}};
                break;
            default:
                    data = null;//kene valami exception
        }


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

    public SubUnitView(String header, UnitView parent, int parentIndex, int myIndex, int type ){
        //NEM URES KONSTRUKTOR
        this.myIndex = myIndex;
        this.type = type;
        this.header = header;
        this.parent = parent;

        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        //LOAD DATA
        TaskTableCreator ttc = new TaskTableCreator(parent.getParent().getMyModel());
        String[][] data = ttc.getSubUnitTs()[parentIndex][myIndex];//kinyerem a sorok matrixat

        if(data.length == 0){//ha nincs egy sor sem beszurom az elso sort
            switch (type){
                case Finals.SUB_UNIT_TYPE:
                    data = new String[][]{{"1", "", "", "", "", "", "", "", ""}};
                    break;
                case Finals.EXTENDED_SUB_UNIT_TYPE:
                    data = new String[][]{{"1", "", "", "", "", "", "", "", "", "", ""}};
                    break;
                default:
                    data = null;//kene valami exception
            }

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


        String[] columns;
        if(type == Finals.SUB_UNIT_TYPE){
            columns=Finals.SUB_UNIT_TABLE_HEADER;
        }
        else{
            columns = Finals.EXTENDED_SUB_UNIT_TABLE_HEADER;
        }

        //ne lehessen athelyezni az oszlopokat es egy egesz sort kibalasztani
        table = new JTable(data,columns);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSelectionAllowed(false);

        //lehessen csak bizonyos cellakat modositni
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
                    insertBlankRow(newIndex);
                }
                parent.getParent().myController.taskViewEdited(parent.getMyIndex(),myIndex,tcl.getRow(),tcl.getColumn(),
                        (String)tcl.getNewValue());
                resizeSubunit();
            }
        };
        TableCellListener tcl = new TableCellListener(table, action);


        //a szmaok kozepen jelenjenek meg
        // https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value
        JTableUtilities.setCellsAlignment(table, SwingConstants.CENTER,subUnitTableModel);


        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(600,450));


        //a header szerint meretezze az oszlopokat
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
    }

    public void insertBlankRow(Integer newIndex){
        subUnitTableModel.addRowToBooleanMatrix();
        if(type == Finals.SUB_UNIT_TYPE){
            subUnitTableModel.addRow(new String[]{newIndex.toString(), "", "", "", "", "", "", "", ""});
        }
        else{
            subUnitTableModel.addRow(new String[]{newIndex.toString(), "", "", "", "", "", "", "", "", "", "", ""});
        }

    }

    public void adjustSizeAndPadding(){
        // sok flaska a sizeal kapcsolatosan

        //this.setBackground(Color.BLACK);
        this.add(new JLabel(header));
        //scrollPane.add(new JLabel("PRET TOTAL UNITAR: 263.3"));
        this.add(scrollPane);


        JLabel pretTotalLabel = new JLabel("PRET TOTAL UNITAR: 256.2569");
        pretTotalLabel.setFont(new Font("Arial",Font.PLAIN,10));
        this.add(pretTotalLabel);

        JPanel paddingPanel = new JPanel();
        this.add(paddingPanel);
        //this.setBorder(new LineBorder(Color.black));
        resizeSubunit();
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
