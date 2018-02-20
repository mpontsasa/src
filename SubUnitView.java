import javax.swing.*;
import java.awt.*;

public class SubUnitView {

    private JScrollPane scrollPane;
    private JTable table;

    public SubUnitView() {
        String[] columns= {"Nr.", "Element", "um", "cantitate unitara"};
        String[][] data = {{"sasa","16"},{"matyi", "2"},{"matyi", "2"},{"matyi", "2"},{"matyi", "2"},{"matyi", "2"},{"matyi", "2"}};
        table = new JTable(data,columns);
        table.getTableHeader().setReorderingAllowed(false);
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(450,450));
    }
}
