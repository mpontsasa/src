import java.util.ArrayList;


public class TaskUnit {

    private ArrayList<TaskSubUnit> subUnits;
    private String unitHeader;//test celbol

    private int currentSubUnit;//jeloli hogy melyik subunit belsejeben vagyunk savekor

    public TaskUnit(String unitHeader) {
        subUnits = new ArrayList<>();
        currentSubUnit = -1;
        this.unitHeader = unitHeader;
    }

    //ezt a konstruktort arra hasznaljuk, hogy egy uj ures sort hozzunk letre insert eseten :P
    public TaskUnit() {
        subUnits = new ArrayList<>();
        currentSubUnit = -1;
        this.unitHeader = "New Unit";
    }

    public String getHeader(){
        return unitHeader;
    }

    public String saveLine(){
        if (currentSubUnit == -1){  // ha a unit kezdeten vagyunk
            currentSubUnit ++;
            return "U" + getHeader();
        }
        else if(currentSubUnit == subUnits.size()){
            currentSubUnit = -1;    // visszaallitjuk mintha meg nem vettunk volna ki elemet
            return Finals.NO_MORE_ITEMS;//mert tulhaladtuk az osszes subunitot
        }
        else{
            String currentLine = subUnits.get(currentSubUnit).saveLine();
            if(currentLine.equals(Finals.NO_MORE_ITEMS)){
                //kifogytunk ebbol a sub unitbol, ezert leptetem a subunitokat es meghivom megegyszer
                currentSubUnit++;
                return this.saveLine();
            }
            else{
                return currentLine;
            }
        }
    }

    public void loadLine(String line) throws Exception{
        System.out.println(line + " Task Unit");

        //megnezzuk hogy egy uj betoltendo subunit kovetkezik-e
        if(line.substring(0,1).equals(Finals.SUB_UNIT_INITAL)){
            //****dobd feldolgozasra


            subUnits.add(new TaskSubUnit(line.substring(1)));
        }
        else{
            if(subUnits.isEmpty()){
                throw new MissingSubUnitException(line);//rowt kaptunk de meg nincs uj subunit
            }
            else{
                subUnits.get(subUnits.size() - 1).loadLine(line);//az utolso unitnak tovabbdobjuk
            }
        }

    }



    public void insertSubUnit(int subUnitIndex){

        if(subUnitIndex == subUnits.size()){
            subUnits.add(new TaskSubUnit());
        }
        else{
            subUnits.add(subUnitIndex,new TaskSubUnit());
        }
    }

    public void deleteSubUnit(int subUnitIndex){
        subUnits.remove(subUnitIndex);
    }

    public void insertRow(int subUnitIndex, int rowIndex){

        subUnits.get(subUnitIndex).insertRow(rowIndex);
    }

    public void deleteRow(int subUnitIndex, int rowIndex){

        subUnits.get(subUnitIndex).deleteRow(rowIndex);
    }
}
