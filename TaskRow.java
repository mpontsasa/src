public class TaskRow {

    private TaskSubUnit parent;

    private String rowTitle;
    private String unitateDeMasura;
    private float cantitateUnitara;
    private float pretUnitara;
    private float pretTotalUnitara;
    private float cantitateTotala;
    private float pretTotal;
    private String furnizor;
    private int numarDeAlocati;
    private float numarDeOreNecesare;

    TaskRow (String line, TaskSubUnit parent) throws Exception{
        this.parent = parent;
        processRowHeader(line);
    }

    TaskRow(TaskSubUnit parent) {
        this.parent = parent;
        this.rowTitle = "";
        this.unitateDeMasura = "";
        this.cantitateUnitara = 0;
        this.pretUnitara = 0;
        this.pretTotalUnitara = 0;
        this.cantitateTotala = 0;
        this.pretTotal = 0;
        this.furnizor = "";
        this.numarDeOreNecesare = 0;
    }

    public void processRowHeader(String line) throws Exception{
        String[] tokens = line.split(Finals.TOK_D);

        if (tokens.length != Finals.NR_OF_FIELDS_IN_ROW){
            System.out.println("" + tokens.length);
            for (String token : tokens)
            {
                System.out.println("sor: " + token);
            }
            throw new InvalidRowHeaderException("incorrect number of fields in row");
        }

        int i = 1;

        rowTitle = tokens[i];
        i++;

        unitateDeMasura = tokens[i];
        i++;

        String nextTok = tokens[i];
        cantitateUnitara = Float.parseFloat(nextTok);
        i++;

        nextTok = tokens[i];
        pretUnitara = Float.parseFloat(nextTok);
        i++;

//        nextTok = tokens[i];
//        pretTotalUnitara = Float.parseFloat(nextTok);
//        i++;
//
//        nextTok = tokens[i];
//        cantitateTotala = Float.parseFloat(nextTok);
//        i++;
//
//        nextTok = tokens[i];
//        pretTotal = Float.parseFloat(nextTok);
//        i++;
        furnizor = tokens[i];
        i++;

        nextTok = tokens[i];
        numarDeAlocati = Integer.parseInt(nextTok);;
//        i++;

//        nextTok = tokens[i];
//        numarDeOreNecesare = Float.parseFloat(nextTok);
//        i++;
    }

    public String getHeader(){
//        return Finals.ROW_INITAL + Finals.TOK_D + rowTitle + Finals.TOK_D + unitateDeMasura + Finals.TOK_D
//                + cantitateUnitara + Finals.TOK_D + pretUnitara + Finals.TOK_D
//                + pretTotalUnitara + Finals.TOK_D + cantitateTotala + Finals.TOK_D
//                + pretTotal + Finals.TOK_D + furnizor + Finals.TOK_D + numarDeOreNecesare + Finals.TOK_D;

        return Finals.ROW_INITAL + Finals.TOK_D + rowTitle + Finals.TOK_D + unitateDeMasura + Finals.TOK_D
                + cantitateUnitara + Finals.TOK_D + pretUnitara + Finals.TOK_D + furnizor + Finals.TOK_D
                + numarDeAlocati + Finals.TOK_D;
    }

    public String[] getTableHeader() { //the row itselve
        String[] res = new String[Finals.LENGTH_OF_SUB_UNIT_TABLE];

        /*res[1] = rowTitle;
        res[2] = unitateDeMasura;
        res[3] = "" + cantitateUnitara;
        res[4] = "" + pretUnitara;
        res[5] = "" + pretTotalUnitara;
        res[6] = "" + cantitateTotala;
        res[7] = "" + pretTotal;
        res[8] = furnizor;*/


        res[1] = rowTitle;
        res[2] = unitateDeMasura;
        res[3] = "" + (double)Math.round(cantitateUnitara * 100d) / 100d;
        res[4] = "" + (double)Math.round(pretUnitara * 100d) / 100d;
        res[5] = "" + (double)Math.round(pretTotalUnitara * 100d) / 100d;
        res[6] = "" + (double)Math.round(cantitateTotala * 100d) / 100d;
        res[7] = "" + (double)Math.round(pretTotal * 100d) / 100d;
        res[8] = furnizor;

        return res;
    }

    public String[] getExtendedTableHeader() {  //the row itselve
        String[] res = new String[Finals.LENGTH_OF_EXTENDED_SUB_UNIT_TABLE];

        res[1] = rowTitle;
        res[2] = unitateDeMasura;
        res[3] = "" + (double)Math.round(cantitateUnitara * 100d) / 100d;
        res[4] = "" + (double)Math.round(pretUnitara * 100d) / 100d;
        res[5] = "" + (double)Math.round(pretTotalUnitara * 100d) / 100d;
        res[6] = "" + (double)Math.round(cantitateTotala * 100d) / 100d;
        res[7] = "" + (double)Math.round(pretTotal * 100d) / 100d;
        res[8] = "" + (double)Math.round(numarDeAlocati * 100d) / 100d;
        res[9] = "" + (double)Math.round(numarDeOreNecesare * 100d) / 100d;

        return res;
    }

    public String saveLine(){
        return getHeader();
    }

//.........calculations
    public void calculatePretTotalUnitar(){
        pretTotalUnitara = cantitateUnitara * pretUnitara;

        parent.calculateSumPretTotalUnitar();
    }

    public void calculateCantitateTotala() {

        if (parent.getSubUnitIndex() == 1 || parent.getSubUnitIndex() == 2)
        {
            cantitateTotala = cantitateUnitara * getParentUnit().getCantitate()/60;
        }
        else
        {
            cantitateTotala = cantitateUnitara * getParentUnit().getCantitate();
        }
        calculatePretTotal();   // fuggoseg miatt
        calculateNumarDeOreNecesare();  // fuggoseg miatt
    }

    public void calculatePretTotal() {
        pretTotal = cantitateTotala * pretUnitara;
    }

    public void calculateNumarDeOreNecesare(){
        numarDeOreNecesare =  cantitateTotala / numarDeAlocati;
    }

    public void clculateAll() {
        calculatePretTotalUnitar();
        calculateCantitateTotala();
    }

//............setters

    public void setRowTitle(String rowTitle) {
        this.rowTitle = rowTitle;
    }

    public void setUnitateDeMasura(String unitateDeMasura) {
        this.unitateDeMasura = unitateDeMasura;
    }

    public void setCantitateUnitara(float cantitateUnitara) {
        this.cantitateUnitara = cantitateUnitara;
        calculatePretTotalUnitar();   //fuggoseg
        calculateCantitateTotala();
    }

    public void setPretUnitara(float pretUnitara) {
        this.pretUnitara = pretUnitara;
        calculatePretTotalUnitar();
        calculatePretTotal();
    }

    public void setFurnizor(String furnizor) {
        this.furnizor = furnizor;
    }

    public void setNumarDeOreNecesare(float numarDeOreNecesare) {
        this.numarDeOreNecesare = numarDeOreNecesare;
    }

    public void setNumarDeAlocati(int numarDeAlocati) {
        this.numarDeAlocati = numarDeAlocati;
        calculateNumarDeOreNecesare();
    }


//............getters


    public String getRowTitle() {
        return rowTitle;
    }

    public String getUnitateDeMasura() {
        return unitateDeMasura;
    }

    public float getCantitateUnitara() {
        return cantitateUnitara;
    }

    public float getPretUnitara() {
        return pretUnitara;
    }

    public float getPretTotalUnitara() {
        return pretTotalUnitara;
    }

    public float getCantitateTotala() {
        return cantitateTotala;
    }

    public float getPretTotal() {
        return pretTotal;
    }

    public String getFurnizor() {
        return furnizor;
    }

    public float getNumarDeAlocati() {
        return numarDeAlocati;
    }

    public float getNumarDeOreNecesare() {
        return numarDeOreNecesare;
    }

    public TaskUnit getParentUnit(){
        return parent.getParent();
    }
}
