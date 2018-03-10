import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;

public class UnitHeaderView extends JPanel {


    private RXTable table;
    private JScrollPane scrollPane;
    private UnitView parent;
    UnitHeaderTableModel unitHeaderTableModel;

    public UnitHeaderView(UnitView parent, String[][] data){
        //NEM URES KONSTRUKTOR

        this.parent = parent;
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        String[] columns = Finals.UNIT_TABLE_HEADER;




        table = new RXTable(data,columns);
        table.setSelectAllForEdit(true);




        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSelectionAllowed(false);

        unitHeaderTableModel = new UnitHeaderTableModel(data,columns);
        table.setModel(unitHeaderTableModel);

        JTableUtilities.setCellsAlignment(table, SwingConstants.CENTER,unitHeaderTableModel);

        unitHeaderTableModel.setCellEditable(0,2,false);

        Action action = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                TableCellListener tcl = (TableCellListener)e.getSource();



                if(userInputWrong((String)tcl.getNewValue())){

                    //if input contains the file structure token TOK_D, then revert changes and throw
                    table.getModel().setValueAt(tcl.getOldValue(),tcl.getRow(),tcl.getColumn());

                    JOptionPane.showMessageDialog(parent.getParent().myFrame,
                            "Caracterul " + Finals.TOK_D + " nu este permis!",
                            "Caracter invalid",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                notifyChangeInHeader(tcl);
            }
        };
        TableCellListener tcl = new TableCellListener(table, action);

//        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
//            //https://stackoverflow.com/questions/35431232/jtable-cell-text-color-changing
//            @Override
//            public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,
//                                                           int row,int column) {
//                Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
//                if(unitHeaderTableModel.isCellEditable(row,column)){
//                    c.setForeground(Color.GREEN);
//                }
//                else{
//                    c.setForeground(Color.RED);
//                }
//                return c;
//            }
//        });




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
        //table.setPreferredScrollableViewportSize(new Dimension(600,450));



        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
        //JTableUtilities.setCellsAlignment(table, SwingConstants.CENTER);

        int numOfRows = table.getRowCount() + 2;
        int rowHeight = table.getRowHeight();

        int width = 610;
        int height = numOfRows * rowHeight +10;
        //height = 80;
        scrollPane.setPreferredSize(new Dimension(width,height));
        this.setPreferredSize(new Dimension(width,height));

        this.setBackground(Color.BLUE);



        this.add(scrollPane);
        //this.add(new JButton("delete"));
    }


    public UnitHeaderView(UnitView parent) {
        //URES HEADER KONSTRUKTOR
        this.parent = parent;
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        String[] columns = Finals.UNIT_TABLE_HEADER;


        String[][] data = {{"","", "", "", "",
                "","","","","","",""}};

        table = new RXTable(data,columns);
        table.setSelectAllForEdit(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSelectionAllowed(false);

        unitHeaderTableModel = new UnitHeaderTableModel(data,columns);
        table.setModel(unitHeaderTableModel);

        JTableUtilities.setCellsAlignment(table, SwingConstants.CENTER,unitHeaderTableModel);



        Action action = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                TableCellListener tcl = (TableCellListener)e.getSource();

                if(userInputWrong((String)tcl.getNewValue())){

                    //if input contains the file structure token TOK_D, then revert changes and throw
                    table.getModel().setValueAt(tcl.getOldValue(),tcl.getRow(),tcl.getColumn());

                    JOptionPane.showMessageDialog(parent.getParent().myFrame,
                            "Caracterul " + Finals.TOK_D + " nu este permis!",
                            "Caracter invalid",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(tcl.getColumn() == 2){
                    // "Cod" changed
                    String candidateCode = table.getModel().getValueAt(0,2).toString();//getValueAt returns Object

                    //nem kell ellenorizni hogy ures string-e mert ures string beutesenel nem triggerelodik az action
                    unitHeaderTableModel.setCellEditable(0,2,false);
                    notifyController(candidateCode);



                }
                else{
                    table.getModel().setValueAt("",tcl.getRow(),tcl.getColumn());
                    

                }

            }
        };
        TableCellListener tcl = new TableCellListener(table, action);





        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            //https://stackoverflow.com/questions/35431232/jtable-cell-text-color-changing
            @Override
            public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,
                                                           int row,int column) {
                Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
                if(unitHeaderTableModel.isCellEditable(row,column)){
                    c.setForeground(Color.GREEN);
                }
                else{
                    c.setForeground(Color.RED);
                }
                return c;
            }
        });



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
        //table.setPreferredScrollableViewportSize(new Dimension(600,450));



        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();

        int numOfRows = table.getRowCount() + 2;
        int rowHeight = table.getRowHeight();

        int width = 610;
        int height = numOfRows * rowHeight +10;
        //height = 80;
        scrollPane.setPreferredSize(new Dimension(width,height));
        this.setPreferredSize(new Dimension(width,height));

        this.setBackground(Color.BLUE);



        this.add(scrollPane);
    }


    private boolean userInputWrong(String input){
        return input.contains(Finals.TOK_D);
    }

    private void notifyChangeInHeader(TableCellListener tcl){
        if(!parent.getParent().myController.taskViewEdited(parent.getMyIndex(),-1,-1,
                tcl.getColumn(),(String) tcl.getNewValue())){
            JOptionPane.showMessageDialog(parent.getParent().myFrame,
                    "Format incorect.",
                    "Caracter invalid",
                    JOptionPane.ERROR_MESSAGE);
        }
        parent.buildFromModel();
    }
    private void notifyController(String candidateCode){

        parent.notifyController(candidateCode);
    }
}
