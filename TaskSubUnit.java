import java.util.ArrayList;
import java.util.Iterator;

public class TaskSubUnit {

    private ArrayList<TaskRow> taskRows;
    private Iterator<TaskRow> saveIterator;

    private int subUnitIndex;
    private TaskUnit parent;

    private float sumPretTotalUnitar;

    public TaskSubUnit(int subUnitIndex, TaskUnit parent) {
        taskRows = new ArrayList<>();
        saveIterator = null;

        this.subUnitIndex = subUnitIndex;
        this.sumPretTotalUnitar = 0;
        this.parent = parent;
    }

    private TaskSubUnit() { //private mert csak a unit hozht lete subunitot csak indexel (erre nins szukseg)
        taskRows = new ArrayList<>();
        saveIterator = null;

        this.subUnitIndex = -1;
    }

    private String getSubUnitHeader(){
//        return Finals.SUB_UNIT_INITAL + Finals.TOK_D + sumPretTotalUnitar;
        return Finals.SUB_UNIT_INITAL + Finals.TOK_D;
    }

    public void processHeader(String line) throws Exception{

//        String[] tokens = line.split(Finals.TOK_D);
//
//        if (tokens.length != Finals.NR_OF_FIELDS_IN_SUB_UNIT){
//            System.out.println("" + tokens.length);
//
//            for (String token : tokens)
//            {
//                System.out.println("sor: " + token);
//            }
//
//            throw new InvalidSubUnitHeaderException("incorrect number of fields in subunit");
//        }
//
//        int i = 1;
//        String nextTok = tokens[i];
//        sumPretTotalUnitar = Float.parseFloat(nextTok);
//        //i++;

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
            taskRows.add(new TaskRow(line.substring(1), this));
        }
        else{
            System.out.println("the wrong line:"+line);
            throw new InvalidRowHeaderException(line);
        }
    }

    public void insertRow(int rowIndex){
        if(rowIndex == taskRows.size()){
            taskRows.add(new TaskRow(this));
        }
        else{
            taskRows.add(rowIndex, new TaskRow(this));
        }
    }

    public void deleteRow(int rowIndex){

        taskRows.remove(rowIndex);
    }

    public TaskUnit getParent() {
        return parent;
    }

    public void calculateSumPretTotalUnitar() {

        float res = 0;

        for(TaskRow tr : taskRows){
            res += tr.getPretTotalUnitara();
        }

        sumPretTotalUnitar = res;

        parent.calculatePretUnitar();

        switch (subUnitIndex)
        {
            case 0:
                parent.calculateMaterial();
            case 1:
                parent.calculateManopera();
            case 2:
                parent.calculateUtilaj();
            case 3:
                parent.calculateTransport();
        }
    }

    public float getSumPretTotalUnitar() {
        return sumPretTotalUnitar;
    }
}
