import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//a projekt tablazathoz hasznaljuk
public class TaskModel implements SuperModel {

    private ArrayList<TaskUnit> taskUnits;
    private int currentSaveUnit;


    public TaskModel() {
        taskUnits = new ArrayList<>();
        currentSaveUnit = 0;

    }

    @Override
    public void loadLine(String unitCode) throws Exception {    //a projektfilebol kap egy sort, letrehozza a unitot es betolti
        System.out.println(unitCode+" Task");

        taskUnits.add(new TaskUnit(unitCode));
    }

    public void saveUnits() throws Exception {
       for (TaskUnit unit : taskUnits) {
           unit.saveUnit();
       }
   }

    public  void saveTaskToFile(String projectName) throws IOException {
        FileWriter fw = new FileWriter(Finals.PROJECTS_PATH + projectName + "_task.txt");

        for(TaskUnit tu : taskUnits) {
            fw.write(tu.getUnitCode() + "\n");
        }

        fw.close();
    }

    public void insertUnit(int unitIndex) throws Exception {
        if(unitIndex== taskUnits.size()){
            taskUnits.add(new TaskUnit());
        }
        else{
            taskUnits.add(unitIndex,new TaskUnit());
        }
    }

    public void deleteUnit(int unitIndex){
        taskUnits.remove(unitIndex);
    }

    public void insertSubUnit(int unitIndex, int subUnitIndex){

        //taskUnits.get(unitIndex).insertSubUnit(subUnitIndex);
    }

    public void deleteSubUnit(int unitIndex, int subUnitIndex){

        taskUnits.get(unitIndex).deleteSubUnit(subUnitIndex);
    }

    public void insertRow(int unitIndex, int subUnitIndex, int rowIndex){

        taskUnits.get(unitIndex).insertRow(subUnitIndex,rowIndex);
    }

    public void deleteRow(int unitIndex, int subUnitIndex, int rowIndex){

        taskUnits.get(unitIndex).deleteRow(subUnitIndex,rowIndex);
    }
}
