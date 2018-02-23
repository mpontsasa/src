public class TaskTableCreator {

    String[][][] unitHeaderTs;  //[unit][sor][mezo]
    String[][][][] subUnitTs;   //[unitszama][subunitszama][sor][mezo]

    TaskModel taskModel;

    public TaskTableCreator(TaskModel taskModel) {
        this.taskModel = taskModel;

        crateUnitHeaderTs();
    }

    public void crateUnitHeaderTs() {
        unitHeaderTs = new String[taskModel.getTaskUnits().size()][][];

        for (int i = 0; i < unitHeaderTs.length; i ++) {
            unitHeaderTs[i] = new String[2][];

            unitHeaderTs[i][0] = Finals.UNIT_TABLE_HEADER;
            unitHeaderTs[i][1] = taskModel.getTaskUnits().get(i).getTableHeader();

            unitHeaderTs[i][1][0] = "" + i; // set index
        }
    }
}
