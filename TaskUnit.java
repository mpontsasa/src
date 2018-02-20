import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


public class TaskUnit {

    private ArrayList<TaskSubUnit> subUnits;
    private String unitHeader;
    private String unitCode;

    private String unitTitle;
    private float pretUnitar;
    private String unitateMetric;
    private float cantitate;
    private int ore;
    private float pretTotal;
    private float material;
    private float manopera;
    private float utlaj;
    private float transport;


    public TaskUnit() {
        subUnits = new ArrayList<>();
        this.unitCode = "new";
        initialiseSubUnits();
    }

    public TaskUnit(String unitCode) throws Exception {
        this.unitCode = unitCode;
        subUnits = new ArrayList<>();
        initialiseSubUnits();
        loadUnit(unitCode);
    }

    public void initialiseSubUnits() {
        for (int i = 0; i < 4; i ++)
        {
            subUnits.add(new TaskSubUnit(i));
        }
    }

    public String getHeader(){
        return unitCode;
    }

    public void processHeader(String line){

        StringTokenizer multiTokenizer = new StringTokenizer(line, "@");
        while (multiTokenizer.hasMoreTokens())
        {
            System.out.println(multiTokenizer.nextToken());
        }
    }

    public void loadUnit(String unitCode) throws Exception{

        int currentSubUnit = -1;

        Scanner scan = new Scanner(new File(Finals.UNITS_PATH + unitCode + ".txt"));

        if(scan.hasNextLine()) {
            String line = scan.nextLine();

            if(!line.substring(0,1).equals(Finals.UNIT_INITAL)){
                throw new MissingUnitHeaderException("nincs hader");
            }
            else {

                processHeader(line);
            }
        }
        else
        {
            //ures file error
        }

        while(scan.hasNextLine()){
            String line = scan.nextLine();
            if(line.equals(Finals.UNIT_END_STRING)){
                break;
            }

            if (line.substring(0,1).equals(Finals.SUB_UNIT_INITAL)){
                currentSubUnit ++;
            }
            else{
                if (currentSubUnit == -1){
                    throw new MissingSubUnitException("missing subunit");
                }

                try {
                    subUnits.get(currentSubUnit).loadLine(line);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        scan.close();
    }

    public void saveUnit() throws Exception{
        FileWriter fw = new FileWriter(Finals.UNITS_PATH + unitCode + ".txt");

        fw.write(Finals.UNIT_INITAL + getHeader() + "\n");


        for (int i = 0; i < Finals.NUMBER_OF_SUBUNITS; i++){
            String currentLine = subUnits.get(i).saveLine();

            while(!currentLine.equals(Finals.NO_MORE_ITEMS))
            {
                fw.write(currentLine + "\n");
                currentLine = subUnits.get(i).saveLine();
            }
        }
        fw.write(Finals.UNIT_END_STRING);
        fw.close();

    }

//    public void insertSubUnit(int subUnitIndex){
//
//        if(subUnitIndex == subUnits.size()){
//            subUnits.add(new TaskSubUnit());
//        }
//        else{
//            subUnits.add(subUnitIndex,new TaskSubUnit());
//        }
//    }

    public void deleteSubUnit(int subUnitIndex){
        subUnits.remove(subUnitIndex);
    }

    public void insertRow(int subUnitIndex, int rowIndex){

        subUnits.get(subUnitIndex).insertRow(rowIndex);
    }

    public void deleteRow(int subUnitIndex, int rowIndex){

        subUnits.get(subUnitIndex).deleteRow(rowIndex);
    }

    public String getUnitCode() {
        return unitCode;
    }

//    private void setUnitCode(String unitCode) {
//        this.unitCode = unitCode;
//    }
}
