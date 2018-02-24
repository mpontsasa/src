import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseWheelEvent;

public class SumTableView extends JPanel {
    private JScrollPane totalScrollPane;
    private JScrollPane MMUTScrollPane;
    private JTable totalsTable;
    private JTable MMUTTable;// material manopera utilaj transport

    public SumTableView() {

        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        String[] columns= {" TOTAL COST DIRECT (RON) ", " CHELTUIELI INDIRECTE (RON) ", " PROFIT (RON) ",
                " TOTAL VALOARE LUCRARE FARA TVA (RON) ", " TVA (RON) ", " TOTAL LUCRARE VALOARE CU TVA (RON) "
                };
        String[][] data = {
                {"589,6587.46","589,6587.46","589,6587.46","589,6587.46","589,6587.46","589,6587.46"},
                {"","0.12","0.03","","0.19","","","","","",""}
                };

        String[] MMUTColumns = {" MATERIAL ", " MANOPERA ", " UTILAJ ", " TRANSPORT ",
                " Total cost direct de recapitulat "};
        String[][] MMUTData = {{"589,6587.46",
                "589,6587.46","589,6587.46","589,6587.46","589,6587.46"}};

        totalsTable = new JTable(data,columns);
        totalsTable.getTableHeader().setReorderingAllowed(false);
        totalsTable.setRowSelectionAllowed(false);

        SumsTableModel stm = new SumsTableModel(data,columns);
        totalsTable.setModel(stm);//bellitom azt hogy csak a szrozokat lehet editalni


        MMUTTable = new JTable(MMUTData,MMUTColumns);
        MMUTTable.getTableHeader().setReorderingAllowed(false);
        MMUTTable.setRowSelectionAllowed(false);


        //set the editing of cells in mmutable to false
        MMUTTable.setDefaultEditor(Object.class, null);

        //

        // https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value
        JTableUtilities.setCellsAlignment(totalsTable, SwingConstants.CENTER,stm);
        //JTableUtilities.setCellsAlignment(MMUTTable, SwingConstants.CENTER,stm);



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
}
