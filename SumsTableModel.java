import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;

public class SumsTableModel extends DefaultTableModel {
    //https://stackoverflow.com/questions/12776021/how-to-make-jtable-cell-editable-noneditable-dynamically

    private boolean[][] editable_cells; // 2d array to represent rows and columns

    public SumsTableModel(String[][] data, String[] columns) { // constructor
        super(data, columns);
        this.editable_cells = new boolean[data.length][columns.length];


        editable_cells[1][1] = true;
        editable_cells[1][2] = true;
        editable_cells[1][4] = true;


    }

    @Override
    public boolean isCellEditable(int row, int column) { // custom isCellEditable function
        return this.editable_cells[row][column];
    }

    public void setCellEditable(int row, int col, boolean value) {
        this.editable_cells[row][col] = value; // set cell true/false
        //this.fireTableCellUpdated(row, col);//ha ezt kiszedem mukodik. nagyon szegyellem magam
    }



}