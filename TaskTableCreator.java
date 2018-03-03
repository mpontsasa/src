public class TaskTableCreator {

    private String[][][] unitHeaderTs;  //[unit][sor][mezo]
    private String[][][][] subUnitTs;   //[unitszama][subunitszama][sor][mezo]
    private String[] pretTotalUnitars;
    private String[][] sumTotalT;
    private String[][] sumMMUTT;


    private TaskModel taskModel;

    public TaskTableCreator(TaskModel taskModel) {
        this.taskModel = taskModel;

        createPretTotalUnitar();
        crateUnitHeaderTs();
        createSubUnitTs();
        createSumTotalT();
        createSumMMUTT();
    }

    public void crateUnitHeaderTs() {
        unitHeaderTs = new String[taskModel.getTaskUnits().size()][][];

        for (int i = 0; i < unitHeaderTs.length; i ++) {
            unitHeaderTs[i] = new String[1][];

            //unitHeaderTs[i][0] = Finals.UNIT_TABLE_HEADER;
            unitHeaderTs[i][0] = taskModel.getTaskUnits().get(i).getTableHeader();

            unitHeaderTs[i][0][0] = "" + (i + 1); // set index, 1-tol indexelve
        }
    }

    public void createSubUnitTs() {
        subUnitTs = new String[taskModel.getTaskUnits().size()][][][];

        for (int unit = 0; unit < subUnitTs.length; unit ++){
            subUnitTs[unit] = new String[Finals.NUMBER_OF_SUBUNITS][][];    //letrehozzuk a subunit matrixokat

            for (int su = 0; su < subUnitTs[unit].length; su++) {
                subUnitTs[unit][su] = new String[taskModel.getTaskUnits().get(unit).getSubUnits().get(su).getTaskRows().size()][];

                if (su == 0 || su == 3){    //material or transport

                    //subUnitTs[unit][su][0] = Finals.SUB_UNIT_TABLE_HEADER;

                    for (int row = 0; row < subUnitTs[unit][su].length; row++) {
                        subUnitTs[unit][su][row] = taskModel.getTaskUnits().get(unit).getSubUnits().get(su).getTaskRows().get(row).getTableHeader();
                        subUnitTs[unit][su][row][0] = "" + (row + 1); //set index
                    }
                }
                else {  //manopera or utilaj
                    //subUnitTs[unit][su][0] = Finals.EXTENDED_SUB_UNIT_TABLE_HEADER;

                    for (int row = 0; row < subUnitTs[unit][su].length; row++) {
                        subUnitTs[unit][su][row] = taskModel.getTaskUnits().get(unit).getSubUnits().get(su).getTaskRows().get(row).getExtendedTableHeader();
                        subUnitTs[unit][su][row][0] = "" + (row + 1); //set index

                    }
                }
            }
        }
    }

    public void createPretTotalUnitar(){

        pretTotalUnitars = new String[Finals.NUMBER_OF_SUBUNITS];
        for(TaskUnit tu : taskModel.getTaskUnits()){

        }
    }

    public void createSumTotalT() {
        sumTotalT = new String[2][];

        //sumTotalT[0] = Finals.SUM_TOTAL_TABLE_HEADER;
        sumTotalT[0] = taskModel.getSumTotalTableHeader();
        sumTotalT[1] = taskModel.getSumAmplifierTableHeader();

    }

    public void createSumMMUTT() {
        sumMMUTT = new String[2][];
        sumMMUTT[0] = taskModel.getSumMMUTTableHeader();
    }

    public String[][][] getUnitHeaderTs() {
        return unitHeaderTs;
    }

    public String[][][][] getSubUnitTs() {
        return subUnitTs;
    }

    public String[][] getSumTotalT() {
        return sumTotalT;
    }

    public String[][] getSumMMUTT() {
        return sumMMUTT;
    }

    public String[] getPretTotalUnitars() {
        return pretTotalUnitars;
    }
}
