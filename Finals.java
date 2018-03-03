//import org.omg.CORBA.PUBLIC_MEMBER;



import java.awt.*;

public class Finals {
    public final static String END_OF_PROJECT = "END_OF_PROJECT";
    public final static String NO_MORE_ITEMS = "NO_MORE_ITEMS";
    public final static String PROJECT_INITAL = "P";
    public final static String UNIT_INITAL = "U";
    public final static String SUB_UNIT_INITAL = "S";
    public final static String ROW_INITAL = "R";
    public final static String UNIT_END_STRING = "END_OF_UNIT";

    public final static String PROJECTS_PATH = "projects/";
    public final static String UNITS_PATH = "units/";

    public final static String[] SUB_UNIT_TITLES = {"MATERIAL", "MANOPERA", "UTILAJ", "TRANSPORT"};
    public final static String[] SUB_UNIT_NECESARS = {"NECESAR MATERIAL", "NECESAR MANOPERA", "NECESAR UTILAJ", "NECESAR TRANSPORT"};
    public final static int NUMBER_OF_SUBUNITS = 4;

    public final static int NR_OF_FIELDS_IN_PROJECT = 11;
    public final static int NR_OF_FIELDS_IN_UNIT = 5;
    public final static int NR_OF_FIELDS_IN_SUB_UNIT = 1;
    public final static int NR_OF_FIELDS_IN_ROW = 7;
    public final static String TOK_D = "@"; //token delimiter

    public final static int LENGTH_OF_UNIT_TABLE = 12;
    public final static String[] UNIT_TABLE_HEADER = {"Nr.", "Articol", "Cod", "Pret Unitar", "um",
            "Cantitate", "Ore", "Pret Total", "Material", "Manopera", "Utilaj", "Transport"};

    public final static int LENGTH_OF_SUB_UNIT_TABLE = 9;
    public final static String[] SUB_UNIT_TABLE_HEADER = {"Nr.", "Element", "um", "Cantitate Unitara",
            "Pret Unitar", "Pret Total Untar", "Cantitate Totala", "Pret Total", "Furnizor"};

    public final static int LENGTH_OF_EXTENDED_SUB_UNIT_TABLE = 10;
    public final static String[] EXTENDED_SUB_UNIT_TABLE_HEADER = {"Nr.", "Element", "um", "Cantitate Unitara",
            "Pret Unitar", "Pret Total Untar", "Cantitate Totala", "Pret Total", "Furnizor", "Nr. de alocatii", "Nr. de ore necesare"};


    public final static String[] MATERIAL_TABLE_HEADER = {"Nr.", "Material", "um", "Cantitate Unitara",
            "Pret Unitar", "Pret Total Untar", "Cantitate Totala", "Pret Total", "Furnizor"};

    public final static String[] MANOPERA_TABLE_HEADER = {"Nr.", "Operatiune", "um", "Norma de timp",
            "Pret Unitar", "Pret Total Untar", "Cantitate Totala", "Pret Total", "Nr. de alocatii", "Nr. de ore necesare"};

    public final static String[] UTILAJ_TABLE_HEADER = {"Nr.", "Denumire utilaj", "um", "Norma de timp",
            "Pret Unitar", "Pret Total Untar", "Cantitate Totala", "Pret Total", "Nr. de utilaje alocate", "Nr. de ore necesare"};

    public final static String[] TRANSPORT_TABLE_HEADER = {"Nr.", "Operatiune", "um", "Cantitate Unitara",
            "Pret Unitar", "Pret Total Untar", "Cantitate Totala", "Pret Total", "Furnizor"};


    public final static int LENGTH_OF_SUM_TOTAL_TABLE = 6;
    public final static String[] SUM_TOTAL_TABLE_HEADER = {" TOTAL COST DIRECT (RON) ",
            " CHELTUIELI INDIRECTE (RON) ", " PROFIT (RON) ",
            " TOTAL VALOARE LUCRARE FARA TVA (RON) ", " TVA (RON) ", " TOTAL LUCRARE VALOARE CU TVA (RON) "
    };

    public final static int LENGTH_OF_TASKS_TABLE = 5;
    public final static String[] TASKS_TABLE_HEADER = {"Nr.", "Articol de lucrare", "um", "Cantitate", "Ore"};
    public final static String[] WEEK_HEADER = {"L", "M", "M", "J", "V", "S", "D"};

    public final static int LENGTH_OF_SUM_MMUT_TABLE = 4;
    public final static String[] SUM_MMUT_TABLE_HEADER = {"Material", "Manopera", "Utilaj", "Transport"};

    public final static Color UNIT_PADDING_COLOR = new Color(180,209,7);

    public final static int MATERIAL_SUB_UNIT_TYPE = 0;
    public final static int MANOPERA_SUB_UNIT_TYPE = 1;
    public final static int UTILAJ_SUB_UNIT_TYPE = 2;
    public final static int TRANSPORT_SUB_UNIT_TYPE = 3;

    public final static String PRET_TOTAL_UNITAR_TEXT = "PRET TOTAL UNITAR: ";

    public final static int NO_VIEW_ACTIVE = 0;
    public final static int TASK_VIEW_ACTIVE = 1;
    public final static int SCHEDULE_VIEW_ACTIVE = 2;

    //public final static int NUMBER_OF_WEEKS_OON_ONE_PAGE = 5;
}
