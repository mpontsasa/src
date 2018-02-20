public class TaskRow {

    private String rowTitle;
    private String unitateDeMasura;
    private float cantitateUnitara;
    private float pretUnitara;
    private float pretTotalUnitara;
    private float cantitateTotala;
    private float pretTotal;
    private String furnizor;
    private float lastField;    //dunno what is this

    TaskRow (String line) throws Exception{
        processRowHeader(line);
    }


    public TaskRow() {
        this.rowTitle = "";
        this.unitateDeMasura = "";
        this.cantitateUnitara = 0;
        this.pretUnitara = 0;
        this.pretTotalUnitara = 0;
        this.cantitateTotala = 0;
        this.pretTotal = 0;
        this.furnizor = "";
        this.lastField = 0;
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

        nextTok = tokens[i];
        pretTotalUnitara = Float.parseFloat(nextTok);
        i++;

        nextTok = tokens[i];
        cantitateTotala = Float.parseFloat(nextTok);
        i++;

        nextTok = tokens[i];
        pretTotal = Float.parseFloat(nextTok);
        i++;

        furnizor = tokens[i];
        i++;

        nextTok = tokens[i];
        lastField = Float.parseFloat(nextTok);
        i++;
    }

    public String getHeader(){
        return Finals.ROW_INITAL + Finals.TOK_D + rowTitle + Finals.TOK_D + unitateDeMasura + Finals.TOK_D
                + cantitateUnitara + Finals.TOK_D + pretUnitara + Finals.TOK_D
                + pretTotalUnitara + Finals.TOK_D + cantitateTotala + Finals.TOK_D
                + pretTotal + Finals.TOK_D + furnizor + Finals.TOK_D + lastField + Finals.TOK_D;

    }

    public String saveLine(){
        return getHeader();
    }

}
