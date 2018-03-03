public class ScheduleTableCreator {

    private TaskModel taskModel;

    private boolean weeksTable[][];
    private String tasksTable[][];
    private int maxDays;

    ScheduleTableCreator(TaskModel taskModel) {
        this.taskModel = taskModel;
        maxDays = findMaxDays();
        createWeeksTable();
        createtaskTable();
    }

    void createtaskTable () {

        tasksTable = new String[taskModel.getTaskUnits().size()][Finals.LENGTH_OF_TASKS_TABLE];

        for (int i = 0; i < taskModel.getTaskUnits().size(); i++) {
            tasksTable[i][0] = "" + (i + 1);
            tasksTable[i][1] = taskModel.getTaskUnits().get(i).getUnitTitle();
            tasksTable[i][2] = taskModel.getTaskUnits().get(i).getUnitateMetric();
            tasksTable[i][3] = "" + taskModel.getTaskUnits().get(i).getCantitate();
            tasksTable[i][4] = "" + taskModel.getTaskUnits().get(i).getOre();
        }
    }

    void createWeeksTable(){

        weeksTable = new boolean[taskModel.getTaskUnits().size()][maxDays + Finals.EXTRA_ORAR_DAYS +1]; // +1 mert 0-tol indexeljuk a napokat

        for(boolean[] bt : weeksTable){
            for (int i = 0; i < bt.length; i++){
                bt[i] = false;
            }
        }

        for (int i = 0; i < taskModel.getTaskUnits().size(); i++){

            TaskUnit tu = taskModel.getTaskUnits().get(i);
            for (int j = 0; j < tu.getSchedules().size(); j ++){
                weeksTable[i][tu.getSchedules().get(j)] = true;
            }
        }
    }

    int findMaxDays() {
        int max = 0;
        for(TaskUnit tu : taskModel.getTaskUnits()){
            for(int i : tu.getSchedules()){
                if (i > max){
                    max = i;
                }
            }
        }
        return max;
    }

    public boolean[][] getWeeksTable() {
        return weeksTable;
    }

    public String[][] getTasksTable() {
        return tasksTable;
    }

    public int getMaxDays() {
        return maxDays;
    }
}
