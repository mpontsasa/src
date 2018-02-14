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
    public void loadLine(String line) throws Exception {
        System.out.println(line+" Task");



        //megnezzuk hogy egy uj betoltendo unit kovetkezik-e
        if(line.substring(0,1).equals(Finals.UNIT_INITAL)){
            //****dobd feldolgozasra


            taskUnits.add(new TaskUnit(line.substring(1)));
        }
        else{
            if(taskUnits.isEmpty()){
                throw new MissingUnitException(line);//subunitot vagy rowt kaptunk de meg nincs uj unit
            }
            else{
                taskUnits.get(taskUnits.size() - 1).loadLine(line);//az utolso unitnak tovabbdobjuk
            }
        }

    }

    @Override
    public String saveLine(){

        if(currentSaveUnit == taskUnits.size()){
            currentSaveUnit = 0;    // visszaallitjuk mintha meg nem vettunk volna ki elemet
            return Finals.END_OF_PROJECT;//mert tulhaladtuk az osszes unitot
        }
        else{
            String currentLine = taskUnits.get(currentSaveUnit).saveLine();
            if(currentLine.equals(Finals.NO_MORE_ITEMS)){
                //kifogytunk ebbol a sub unitbol, ezert leptetem a subunitokat es meghivom megegyszer
                currentSaveUnit++;
                return this.saveLine();
            }
            else{
                return currentLine;
            }
        }
    }




    public void insertUnit(int unitIndex){
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

        taskUnits.get(unitIndex).insertSubUnit(subUnitIndex);
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
