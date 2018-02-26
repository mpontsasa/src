import javax.swing.table.DefaultTableModel;

public class OrarGridViewTableModel extends DefaultTableModel {
    //https://stackoverflow.com/questions/12776021/how-to-make-jtable-cell-editable-noneditable-dynamically

    private boolean[][] editable_cells; // 2d array to represent rows and columns
    private int editedRow;
    private int editedColumn;


    public OrarGridViewTableModel(String[][] data, String[] columns) { // constructor
        super(data, columns);
        this.editable_cells = new boolean[data.length][columns.length];

    }

    @Override
    public boolean isCellEditable(int row, int column) { // custom isCellEditable function
        //return this.editable_cells[row][column];
        return false;
    }

    public boolean getEditRights(int row, int column) { // custom isCellEditable function
        return this.editable_cells[row][column];
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        super.setValueAt(value, row, col);
        editedRow = row;
        editedColumn = col;
    }

    public void setCellEditable(int row, int col, boolean value) {
        this.editable_cells[row][col] = value; // set cell true/false
        //this.fireTableCellUpdated(row, col);//ha ezt kiszedem mukodik. nagyon szegyellem magam
    }


    public int getEditedRow() {
        return editedRow;
    }

    public int getEditedColumn() {
        return editedColumn;
    }
}
