import javax.swing.*;
import javax.swing.event.TableModelEvent;
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

        MMUTTable = new JTable(MMUTData,MMUTColumns);
        MMUTTable.getTableHeader().setReorderingAllowed(false);

        // https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value
        JTableUtilities.setCellsAlignment(totalsTable, SwingConstants.CENTER);
        JTableUtilities.setCellsAlignment(MMUTTable, SwingConstants.CENTER);



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
