import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ResumeTableView extends JPanel {

    JTable table;
    JScrollPane scrollPane;

//    JTable resumeTable;
//    JScrollPane resumeScrollPane;


    public ResumeTableView() {
        this.setBackground(Color.CYAN);
        int ii = 3; int jj = 36;
        String[] columns = new String[]{"Nr.", "Articol de lucrare", "um", "cantitate", "ore"};
        String[][] data = new String[][]{{"bla","blsxxxscdvfbdfghngfdsdfvdcscdva","bla","bla","bldfvgbfdswdfgba"},{"bla","bla","bla","bla","bla"},{"bla","bla","bla","bla","bla"}};


        JPanel gridbagPanel = new JPanel();

        //this.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.EAST;

        table = new JTable(data,columns);
        OrarGridViewTableModel orarGridViewTableModel = new OrarGridViewTableModel(data,columns);
        table.setModel(orarGridViewTableModel);



        scrollPane = new JScrollPane(table);

        table.setFillsViewportHeight(true);
        //table.setRowSelectionAllowed(false);
        //table.getTableHeader().setResizingAllowed(true);
        //table.getTableHeader().setReorderingAllowed(false);
        //table.setColumnSelectionAllowed(false);

        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();

        //resize();

        JPanel bottomPanel = new JPanel(new GridLayout(1,3));
       // bottomPanel.add(new JPanel());
       // bottomPanel.add(new JPanel());
        bottomPanel.add(scrollPane);

        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);




    }



    public void resize(){
        //resize after changes have been made

        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();


        int numOfRows = table.getRowCount() + 2;
        int rowHeight = table.getRowHeight();



        int width = 559;

        width = 0;
        for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++)
        {
            System.out.println(table.getColumnModel().getColumn(columnIndex).getMinWidth() + " ");
            width += table.getColumnModel().getColumn(columnIndex).getWidth();
        }

        int height = numOfRows * rowHeight + 50;

        int padding = 20;
        scrollPane.setPreferredSize(new Dimension(width + padding,height + padding + 3));
        table.setPreferredSize(new Dimension(width,height));
        this.setPreferredSize(new Dimension(width,height));
        this.revalidate();
        this.repaint();
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
