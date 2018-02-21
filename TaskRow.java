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
    private float numarDeAlocati;
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
        numarDeAlocati = Float.parseFloat(nextTok);;
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

    public String saveLine(){
        return getHeader();
    }

    public void calculatePretTotalUnitar(){
        pretTotalUnitara = cantitateUnitara * pretUnitara;
    }

    public void calculateCantitateTotala() {
        
    }

}
