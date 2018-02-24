import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseWheelEvent;

public class UnitHeaderView extends JPanel {


    private JTable table;
    private JScrollPane scrollPane;
    private UnitView parent;
    UnitHeaderTableModel unitHeaderTableModel;

    public UnitHeaderView(UnitView parent, String[][] data){
        //NEM URES KONSTRUKTOR

        this.parent = parent;
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        String[] columns = Finals.UNIT_TABLE_HEADER;




        table = new JTable(data,columns);




        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSelectionAllowed(false);

        unitHeaderTableModel = new UnitHeaderTableModel(data,columns);
        table.setModel(unitHeaderTableModel);

        unitHeaderTableModel.setCellEditable(0,2,false);

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


    public UnitHeaderView(UnitView parent) {
        //URES HEADER KONSTRUKTOR
        this.parent = parent;
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        String[] columns = Finals.UNIT_TABLE_HEADER;


        String[][] data = {{"","", "", "", "",
                "","","","","","",""}};

        table = new JTable(data,columns);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSelectionAllowed(false);

        unitHeaderTableModel = new UnitHeaderTableModel(data,columns);
        table.setModel(unitHeaderTableModel);



        table.getModel().addTableModelListener(e -> {

            if(e.getColumn() == 2){
                // "Cod" changed
                String candidateCode = table.getModel().getValueAt(0,2).toString();//getValueAt returns Object
                if(!candidateCode.equals("")){
                    unitHeaderTableModel.setCellEditable(0,2,false);
                    notifyController(candidateCode);
                }

            }
        });


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


    private void notifyController(String candidateCode){

        parent.notifyController(candidateCode);
    }
}
