import javax.swing.table.DefaultTableModel;

public class UnitHeaderTableModel extends DefaultTableModel {
    //https://stackoverflow.com/questions/12776021/how-to-make-jtable-cell-editable-noneditable-dynamically

    private boolean[][] editable_cells; // 2d array to represent rows and columns

    public UnitHeaderTableModel(String[][] data, String[] columns) { // constructor
        super(data, columns);
        this.editable_cells = new boolean[data.length][columns.length];


        //editable_cells[0][0] = true;
        editable_cells[0][1] = true;
        editable_cells[0][2] = true;
        editable_cells[0][4] = true;
        editable_cells[0][5] = true;
        editable_cells[0][6] = true;

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