import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


public class TaskUnit {

    private ArrayList<TaskSubUnit> subUnits;
    private String unitCode;

    private String unitTitle;
    private float pretUnitar;
    private String unitateMetric;
    private float cantitate;
    private float ore;
    private float pretTotal;
    private float material;
    private float manopera;
    private float utilaj;
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
            subUnits.add(new TaskSubUnit(i, this));
        }
    }

    public String getHeader(){
//        return Finals.UNIT_INITAL + Finals.TOK_D + unitTitle + Finals.TOK_D + pretUnitar + Finals.TOK_D + unitateMetric
//                + Finals.TOK_D + cantitate + Finals.TOK_D + ore + Finals.TOK_D + pretTotal + Finals.TOK_D
//                + material + Finals.TOK_D + manopera + Finals.TOK_D + utilaj + Finals.TOK_D
//                + transport;
//
        return Finals.UNIT_INITAL + Finals.TOK_D + unitTitle + Finals.TOK_D + unitateMetric
                + Finals.TOK_D + cantitate + Finals.TOK_D + ore + Finals.TOK_D;
    }

    public void processHeader(String line) throws Exception{

        String[] tokens = line.split(Finals.TOK_D);

        if (tokens.length != Finals.NR_OF_FIELDS_IN_UNIT){
            System.out.println("" + tokens.length);

            for (String token : tokens)
            {
                System.out.println("sor: " + token);
            }

            throw new InvalidUnitHeaderException("incorrect number of fields in header");
        }

        int i = 1;

        unitTitle = tokens[i];
        i++;

//        String nextTok = tokens[i];
//        pretUnitar = Float.parseFloat(nextTok);
//        i++;

        unitateMetric = tokens[i];
        i++;

        String nextTok = tokens[i];
        cantitate = Float.parseFloat(nextTok);
        i++;

        nextTok = tokens[i];
        ore = Float.parseFloat(nextTok);
//        i++;

//        nextTok = tokens[i];
//        pretTotal = Float.parseFloat(nextTok);
//        i++;
//
//        nextTok = tokens[i];
//        material = Float.parseFloat(nextTok);
//        i++;
//
//        nextTok = tokens[i];
//        manopera = Float.parseFloat(nextTok);
//        i++;
//
//        nextTok = tokens[i];
//        utilaj = Float.parseFloat(nextTok);
//        i++;
//
//        nextTok = tokens[i];
//        transport = Float.parseFloat(nextTok);
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
            throw new MissingUnitHeaderException("URES FILE");
        }

        while(scan.hasNextLine()){
            String line = scan.nextLine();
            if(line.equals(Finals.UNIT_END_STRING)){
                break;
            }

            if (line.substring(0,1).equals(Finals.SUB_UNIT_INITAL)){
                currentSubUnit ++;
                subUnits.get(currentSubUnit).processHeader(line);
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

        fw.write(getHeader() + "\n");


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
