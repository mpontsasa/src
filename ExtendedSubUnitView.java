import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;

public class ExtendedSubUnitView extends JPanel {

    private JScrollPane scrollPane;
    private JTable table;

    public ExtendedSubUnitView(String header) {

        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        String[] columns= {"Nr.", "Element", "um", "cantitate unitara", "pret unitar", "pret total unitar",
                "cantitate totala", "pret total", "furnizor", "nr. de alocatii", ""};
//        String[][] data = {
//                {"1", "oi", "kg", "51", "1.23", "845",
//                        "78", "0", "matyi", "vagany ceg", "szeeerussz"},
//
//                {"2", "yeah", "meter", "5", "77", "777",
//                        "7777", "yess", "sasa", "vagany ceg", "szeeerussz"},
//                {"2", "yeah", "meter", "5", "77", "777",
//                        "7777", "yess", "sasa", "vagany ceg", "szeeerussz"}};

        String[][] data = {
                {"1", "", "", "", "", "",
                        "", "", "", "", ""},

                {"2", "", "", "", "", "",
                        "", "", "", "", ""}
        };

        table = new JTable(data,columns);
        table.getTableHeader().setReorderingAllowed(false);

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
}
