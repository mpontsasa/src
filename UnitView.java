import javax.swing.*;
import java.awt.*;

public class UnitView extends JPanel {


    private JTable table;
    private JScrollPane scrollPane;

    public UnitView() {
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        String[] columns= {"Nr.", "Element", "um", "cantitate unitara", "pret unitar", "pret total unitar",
                "cantitate totala", "pret total", "furnizor"};
        String[][] data = {
                {"1", "oi", "kg", "51", "1.23", "845",
                        "78", "0", "matyi"},

                {"2", "yeah", "meter", "5", "77", "777",
                        "7777", "yess", "sasa"},
                {"2", "yeah", "meter", "5", "77", "777",
                        "7777", "yess", "sasa"}};
        table = new JTable(data,columns);
        table.getTableHeader().setReorderingAllowed(false);
        scrollPane = new JScrollPane(table);
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
        
        this.add(scrollPane);
    }
}
