import javax.swing.table.DefaultTableModel;

public class SubUnitTableModel extends DefaultTableModel {
    //https://stackoverflow.com/questions/12776021/how-to-make-jtable-cell-editable-noneditable-dynamically

    private boolean[][] editable_cells; // 2d array to represent rows and columns
    private int editedRow;
    private int editedColumn;


    public SubUnitTableModel(String[][] data, String[] columns) { // constructor
        super(data, columns);
        this.editable_cells = new boolean[data.length][columns.length];



        for(int i = 0; i < editable_cells.length; i++){
            editable_cells[i][1] = true;
            editable_cells[i][2] = true;
            editable_cells[i][3] = true;
            editable_cells[i][4] = true;
            editable_cells[i][8] = true;
//            if(data[0].length > 9){
//                editable_cells[i][9] = true;
//            }
        }



    }

    @Override
    public boolean isCellEditable(int row, int column) { // custom isCellEditable function
        return this.editable_cells[row][column];
    }
    @Override
    public void setValueAt(Object value, int row, int col){
        super.setValueAt(value,row,col);
        editedRow = row;
        editedColumn = col;
    }

    public void setCellEditable(int row, int col, boolean value) {
        this.editable_cells[row][col] = value; // set cell true/false
        //this.fireTableCellUpdated(row, col);//ha ezt kiszedem mukodik. nagyon szegyellem magam
    }

    public void addRowToBooleanMatrix(){
        boolean[][] editable_cells_new = new boolean[editable_cells.length + 1][editable_cells[0].length];
        for(int i = 0; i < editable_cells.length; i++){
            for (int j = 0; j < editable_cells[0].length; j++){
                editable_cells_new[i][j] = editable_cells[i][j];
            }
        }
        editable_cells = editable_cells_new;

        //beallitom hogy a szerkesztheto mezok szerkeszthetoek legyenek
        int lastRowIndex = editable_cells.length - 1;
        editable_cells[lastRowIndex][1] = true;
        editable_cells[lastRowIndex][2] = true;
        editable_cells[lastRowIndex][3] = true;
        editable_cells[lastRowIndex][4] = true;
        editable_cells[lastRowIndex][8] = true;

        if(editable_cells[0].length > 9){
            editable_cells[lastRowIndex][9] = true;
        }


    }


    public int getEditedRow() {
        return editedRow;
    }

    public int getEditedColumn() {
        return editedColumn;
    }
}