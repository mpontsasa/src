import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;

public class UnitHeaderView extends JPanel {


    private JTable table;
    private JScrollPane scrollPane;

    public UnitHeaderView() {
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        String[] columns= {"Index", "Titlu", "Cod", "    PRET UNITAR    ", "UM", "CANTITATE",
                "ore", "PRET TOTAL", "material","manopera","utilaj","transport"};
        String[][] data = {{"6","Cofrare fundatii demisol cota -3,05", "C456", "85.00", "mp",
                            "112.68","20.00","6.00","5.00","8.00","5.00","9.00"}};
        table = new JTable(data,columns);
        table.getTableHeader().setReorderingAllowed(false);


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
}
