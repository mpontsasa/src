import java.util.ArrayList;
import java.util.Iterator;

public class TaskSubUnit {

    private ArrayList<TaskRow> taskRows;
    private Iterator<TaskRow> saveIterator;
    private int subUnitIndex;

    private float sumPretTotalUnitar;

    public TaskSubUnit(int subUnitIndex) {
        taskRows = new ArrayList<>();
        saveIterator = null;

        this.subUnitIndex = subUnitIndex;
        this.sumPretTotalUnitar = 0;
    }

    private TaskSubUnit() { //private mert csak a unit hozht lete subunitot csak indexel (erre nins szukseg)
        taskRows = new ArrayList<>();
        saveIterator = null;

        this.subUnitIndex = -1;
    }

    private String getSubUnitHeader(){
        return Finals.SUB_UNIT_INITAL + Finals.TOK_D + sumPretTotalUnitar;
    }

    public void processHeader(String line) throws Exception{
        String[] tokens = line.split(Finals.TOK_D);

        if (tokens.length != Finals.NR_OF_FIELDS_IN_SUB_UNIT){
            System.out.println("" + tokens.length);

            for (String token : tokens)
            {
                System.out.println("sor: " + token);
            }

            throw new InvalidSubUnitHeaderException("incorrect number of fields in subunit");
        }

        int i = 1;
        String nextTok = tokens[i];
        sumPretTotalUnitar = Float.parseFloat(nextTok);
        i++;

    }

    public String saveLine(){
        if (saveIterator == null){
            saveIterator = taskRows.iterator();
            return getSubUnitHeader();
        }
        else if(saveIterator.hasNext()){
            return saveIterator.next().saveLine();
        }
        else{
            saveIterator = null;//reset the itarator
            return Finals.NO_MORE_ITEMS;
        }
    }

    public void loadLine(String line) throws Exception{

        //megnezzuk hogy amit kaptunk row-e vagy anomalia (CSAK ROWT KAPHAT A SUBUNIT)
        if(line.substring(0,1).equals(Finals.ROW_INITAL)){
            taskRows.add(new TaskRow(line.substring(1)));
        }
        else{
            System.out.println("the wrong line:"+line);
            throw new InvalidRowHeaderException(line);
        }
    }

    public void insertRow(int rowIndex){
        if(rowIndex == taskRows.size()){
            taskRows.add(new TaskRow());
        }
        else{
            taskRows.add(rowIndex, new TaskRow());
        }
    }

    public void deleteRow(int rowIndex){

        taskRows.remove(rowIndex);
    }

}
