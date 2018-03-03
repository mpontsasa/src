import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//a projekt tablazathoz hasznaljuk
public class TaskModel implements SuperModel {

    private ArrayList<TaskUnit> taskUnits;
    private int currentSaveUnit;

    private String detaliiProiect;
    private float material;
    private float manopera;
    private float utilaj;
    private float transport;
    private float totalCostDirect;
    private float cheltuileIndirecte;
    private float cheltuileIndirecteAmplifier;
    private float profit;
    private float profitAmplifier;
    private float totalValoareLucrareFaraTVA;
    private float TVA;
    private float TVAAmplifier;
    private float totalValoareLucrareCuTVA;



    public TaskModel() {
        taskUnits = new ArrayList<>();
        currentSaveUnit = 0;
    }

    public void loadProject(String projectName) throws Exception {

        Scanner scan = new Scanner(new File(Finals.PROJECTS_PATH + projectName + "_task.txt"));

        if(scan.hasNextLine()) {
            String line = scan.nextLine();

            if(!line.substring(0,1).equals(Finals.PROJECT_INITAL)){
                throw new MissingProjectHeaderException("nincs hader");
            }
            else {

                processHeader(line);
            }
        }
        else
        {
            throw new MissingProjectHeaderException("URES FILE");
        }

        if(scan.hasNextLine()) {
            String line = scan.nextLine();

            if(!line.substring(0,1).equals(Finals.DETALII_INITAL)){
                throw new MissingDetaliiException("nincs detalii");
            }
            else {

                processDetalii(line);
            }
        }
        else
        {
            throw new MissingDetaliiException("csak projekt header van");
        }

        while(scan.hasNextLine()){
            //ez a line egy unitCode lesz
            String line = scan.nextLine();
            taskUnits.add(new TaskUnit(line, this));
        }
        scan.close();

        calculateAll();
    }

    public void processHeader(String line) throws Exception{
        String[] tokens = line.split(Finals.TOK_D);

        if (tokens.length != Finals.NR_OF_FIELDS_IN_PROJECT){
            System.out.println("" + tokens.length);

            for (String token : tokens)
            {
                System.out.println("sor: " + token);
            }

            throw new InvalidProjectHeaderException("incorrect number of fields in header");
        }

        int i = 1;

        String nextTok = tokens[i];
        material = Float.parseFloat(nextTok);
        i++;

        nextTok = tokens[i];
        manopera = Float.parseFloat(nextTok);
        i++;

        nextTok = tokens[i];
        utilaj = Float.parseFloat(nextTok);
        i++;

        nextTok = tokens[i];
        transport = Float.parseFloat(nextTok);
        i++;

        nextTok = tokens[i];
        totalCostDirect = Float.parseFloat(nextTok);
        i++;

        nextTok = tokens[i];
        cheltuileIndirecte = Float.parseFloat(nextTok);
        i++;

        nextTok = tokens[i];
        profit = Float.parseFloat(nextTok);
        i++;

        nextTok = tokens[i];
        totalValoareLucrareFaraTVA = Float.parseFloat(nextTok);
        i++;

        nextTok = tokens[i];
        TVA = Float.parseFloat(nextTok);
        i++;

        nextTok = tokens[i];
        totalValoareLucrareCuTVA = Float.parseFloat(nextTok);
        //i++;
    }

    public void processDetalii(String line) throws Exception{

        String[] tokens = line.split(Finals.TOK_D);

        if (tokens.length < 1){
            System.out.println("" + tokens.length);

            for (String token : tokens)
            {
                System.out.println("sor: " + token);
            }

            throw new InvalidDetaliiException("incorrect number of fields in header");
        }

        detaliiProiect = "";

        for (int i = 1; i < tokens.length; i++)
        {
            detaliiProiect += tokens[i] + "\n";
        }
    }

    public String getHeader(){
        return Finals.PROJECT_INITAL + Finals.TOK_D
                + material + Finals.TOK_D
                + manopera + Finals.TOK_D
                + utilaj + Finals.TOK_D
                + transport + Finals.TOK_D
                + totalCostDirect + Finals.TOK_D
                + cheltuileIndirecte + Finals.TOK_D
                + profit + Finals.TOK_D
                + totalValoareLucrareFaraTVA + Finals.TOK_D
                + TVA + Finals.TOK_D
                + totalValoareLucrareCuTVA + Finals.TOK_D;
    }

    public String getDetaliiHeader(){
        String dh = Finals.DETALII_INITAL + Finals.TOK_D + detaliiProiect;
        dh.replaceAll("\n", Finals.TOK_D);

        return dh;
    }

    public String[] getSumTotalTableHeader() {
        String[] res = new String[Finals.LENGTH_OF_SUM_TOTAL_TABLE];

        res[0] = "" + totalCostDirect;
        res[1] = "" + cheltuileIndirecte;
        res[2] = "" + profit;
        res[3] = "" + totalValoareLucrareFaraTVA;
        res[4] = "" + TVA;
        res[5] = "" + totalValoareLucrareCuTVA;

        return res;
    }

    public String[] getSumAmplifierTableHeader() {
        String[] res = new String[Finals.LENGTH_OF_SUM_TOTAL_TABLE];

        res[0] = "";
        res[1] = "" + cheltuileIndirecteAmplifier;
        res[2] = "" + profitAmplifier;
        res[3] = "";
        res[4] = "" + TVAAmplifier;
        res[5] = "";

        return res;
    }

    public String[] getSumMMUTTableHeader() {
        String[] res = new String[Finals.LENGTH_OF_SUM_TOTAL_TABLE];

        res[0] = "" + material;
        res[1] = "" + manopera;
        res[2] = "" + utilaj;
        res[3] = "" + transport;
        //res[4] = "" + totalCostDirect;
        return res;
    }

    public void saveUnits() throws Exception {
       for (TaskUnit unit : taskUnits) {
           unit.saveUnit();
       }
   }

    public  void saveTaskToFile(String projectName) throws IOException {
        FileWriter fw = new FileWriter(Finals.PROJECTS_PATH + projectName + "_task.txt");

        fw.write(getHeader() + "\n");
        fw.write(getDetaliiHeader() + "\n");

        for(TaskUnit tu : taskUnits) {
            fw.write(tu.getProjectFileLine() + "\n");
        }

        fw.close();
    }

    public void insertUnit(int unitIndex) throws Exception {
        if(unitIndex== taskUnits.size()){
            taskUnits.add(new TaskUnit(this));
        }
        else{
            taskUnits.add(unitIndex,new TaskUnit(this));
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

    //......setters

    public void setCheltuileIndirecteAmplifier(float cheltuileIndirecteAmplifier) {
        this.cheltuileIndirecteAmplifier = cheltuileIndirecteAmplifier;

        calculateCheltuileIndirecte();
    }

    public void setProfitAmplifier(float profitAmplifier) {
        this.profitAmplifier = profitAmplifier;

        calculateProfit();
    }

    public void setTVAAmplifier(float TVAAmplifier) {
        this.TVAAmplifier = TVAAmplifier;

        calculateTVA ();
    }

    public void setDetaliiProiect(String detaliiProiect) {
        this.detaliiProiect = detaliiProiect;
    }

    //...........calculations

    public void calculateMaterial(){

        float res = 0;

        for (TaskUnit tu : taskUnits){
            res += tu.getMaterial();
        }

        material = res;

        calculateTotalCostDirect();
    }

    public void calculateManopera(){

        float res = 0;

        for (TaskUnit tu : taskUnits){
            res += tu.getManopera();
        }

        manopera = res;

        calculateTotalCostDirect();
    }

    public void calculateUtilaj(){

        float res = 0;

        for (TaskUnit tu : taskUnits){
            res += tu.getUtilaj();
        }

        utilaj = res;

        calculateTotalCostDirect();
    }

    public void calculateTransport(){

        float res = 0;

        for (TaskUnit tu : taskUnits){
            res += tu.getTransport();
        }

        transport = res;

        calculateTotalCostDirect();
    }

    public void calculateTotalCostDirect() {
        totalCostDirect = material + manopera + utilaj + transport;

        calculateCheltuileIndirecte();
        calculateProfit();
        calculateTotalValoareLucrareFaraTVA();
    }

    public void calculateCheltuileIndirecte() {
        cheltuileIndirecte = cheltuileIndirecteAmplifier * totalCostDirect;

        calculateTotalValoareLucrareFaraTVA();
    }

    public void calculateProfit() {
        profit = profitAmplifier * totalCostDirect;

        calculateTotalValoareLucrareFaraTVA();
    }

    public void calculateTotalValoareLucrareFaraTVA() {
        totalValoareLucrareFaraTVA = profit + cheltuileIndirecte + totalCostDirect;

        calculateTVA ();
        calculateTotalValoareLucareCuTVA ();
    }

    public void calculateTVA () {
        TVA = totalValoareLucrareFaraTVA * TVAAmplifier;

        calculateTotalValoareLucareCuTVA ();
    }

    public void calculateTotalValoareLucareCuTVA () {
        totalValoareLucrareCuTVA = totalValoareLucrareFaraTVA + TVA;
    }

    public  void calculateAll() {
        for (TaskUnit tu :taskUnits){
            tu.clculateAll();
        }
    }

    //........getters

    public ArrayList<TaskUnit> getTaskUnits() {
        return taskUnits;
    }

    public String getDetaliiProiect() {
        return detaliiProiect;
    }
}
