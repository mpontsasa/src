import java.io.File;
import java.io.FileWriter;

public class TaskHtmlFileCreator {

    TaskModel taskModel;
    String content = "";
    TaskTableCreator ttc;

    TaskHtmlFileCreator(TaskModel taskModel, String projectName) throws  Exception{
        this.taskModel = taskModel;
        ttc = new TaskTableCreator(taskModel);
        createHtmlFile(projectName);
    }

    public void createHtmlFile(String projectName) throws Exception{

        content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "table {\n" +
                "    font-family: arial, sans-serif;\n" +
                "    border-collapse: collapse;\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                "td {\n" +
                "    border: 1px solid #dddddd;\n" +
                "    text-align: left;\n" +
                "}\n" +
                "th {\n" +
                "    border: 1px solid #dddddd;\n" +
                "    text-align: center;\n" +
                "    padding: 8px;\n" +
                "}\n" +
                "\n" +
//                "tr:nth-child(even) {\n" +
//                "    background-color: #dddddd;\n" +
//                "}\n" +
                ".unitTitle {\n" +
                "\tbackground-color: DodgerBlue;\n" +
                "\tfont-size: large;\n" +
                "}\n" +
                ".leftSideLine {\n" +
                "\tbackground-color: OrangeRed;\n" +
                "width: 1%;"+
                "}" +
                ".details {\n" +
                "color: SlateBlue;\n" +
                "}" +
                "</style>\n" +
                "</head>\n" +
                "<body>";


        printProjectTitle();
        printProjectDetails();

        for (int i = 0; i < taskModel.getTaskUnits().size(); i++){
            printUnit(i);
            content += "<br><br>";
        }

        content += "</body>\n" +
                "</html>";
//..............print to file

        File file = new File(Finals.PRINT_PATH + projectName + "\\" + projectName + ".html");
        file.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(Finals.PRINT_PATH + projectName + "\\" + projectName + ".html");

        fw.write(content);

        fw.close();
    }

    public void printProjectTitle() {

    }

    public void printProjectDetails(){

        String dt = taskModel.getDetaliiProiect();
        dt = dt.replaceAll("\n", "<br>");

        content += "<section class=\"details\">";
        content += dt;
        content += "</section><br>";
    }

    public void printUnit(int unitIndex){

        printUnitHeader(unitIndex);

        for (int i = 0; i < ttc.getSubUnitTs()[unitIndex].length; i++){
            printSubunit(unitIndex, i);
        }
    }

    public void printUnitHeader(int unitIndex){
        content += "<table>";

        content += "<tr>\n";

        //nr of unit
        content += "<th class=\"unitTitle\" rowspan=\"2\">";
        content += Finals.UNIT_TABLE_HEADER[0] + ttc.getUnitHeaderTs()[unitIndex][0][0];
        content += "</th>\n";

        //title of unit
        content += "<th class=\"unitTitle\" rowspan=\"2\">";
        content += ttc.getUnitHeaderTs()[unitIndex][0][1];
        content += "</th>\n";

        for (int i = 2; i < Finals.UNIT_TABLE_HEADER.length; i++){
            content += "<th>";
            content +=  Finals.UNIT_TABLE_HEADER[i];
            content += "</th>\n";
        }
        content += "</tr>\n";

        content += "<tr>\n";
        for (int i = 2; i < ttc.getUnitHeaderTs()[unitIndex][0].length; i++){
            content += "<th>";
            content += ttc.getUnitHeaderTs()[unitIndex][0][i];
            content += "</th>\n";
        }

        content += "</tr>\n";

        content += "</table>\n";
    }

    public void printSubunit( int unitIndex, int subUnitIndex){
        printSubunitHeader(unitIndex, subUnitIndex);


        for (int row = 0 ; row < ttc.getSubUnitTs()[unitIndex][subUnitIndex].length; row++){
            content += "<tr>";
            putLeftSideCell();
            for (int field = 0; field < ttc.getSubUnitTs()[unitIndex][subUnitIndex][row].length; field++){
                content += "<td>";
                content += ttc.getSubUnitTs()[unitIndex][subUnitIndex][row][field];
                content += "</td>\n";
            }
            content += "</tr>\n";
        }

        content += "</table>";
    }

    public void printSubunitHeader(int unitIndex, int subUnitIndex){
        content += "<table>";

        content += "<tr>\n";
        putLeftSideCell();
        content += "<th colspan=\"4\">";
        content += Finals.SUB_UNIT_TITLES[subUnitIndex];
        content += "</th>\n";
        content += "</tr>\n";
        content += "<tr>\n";
        putLeftSideCell();
        switch(subUnitIndex)
        {
            case 0:
                for (String field : Finals.MATERIAL_TABLE_HEADER) {
                    content += "<th>";
                    content += field;
                    content += "</th>\n";

                }
                break;
            case 1:
                for (String field : Finals.MANOPERA_TABLE_HEADER) {
                    content += "<th>";
                    content += field;
                    content += "</th>\n";

                }
                break;
            case 2:
                for (String field : Finals.UTILAJ_TABLE_HEADER) {
                    content += "<th>";
                    content += field;
                    content += "</th>\n";

                }
                break;
            case 3:
                for (String field : Finals.TRANSPORT_TABLE_HEADER) {
                    content += "<th>";
                    content += field;
                    content += "</th>\n";

                }
        }

        content += "</tr>\n";

        printFormulas(subUnitIndex);
    }

    public void printFormulas(int subUnitIndex){


        content += "<tr>\n";
        putLeftSideCell();
        switch(subUnitIndex)
        {
            case 0:
                for (String field : Finals.MATERIAL_FORMULAS) {
                    content += "<th>";
                    content += field;
                    content += "</th>\n";

                }
                break;
            case 1:
                for (String field : Finals.MANOPERA_FORMULAS) {
                    content += "<th>";
                    content += field;
                    content += "</th>\n";

                }
                break;
            case 2:
                for (String field : Finals.UTILAJ_FORMULAS) {
                    content += "<th>";
                    content += field;
                    content += "</th>\n";

                }
                break;
            case 3:
                for (String field : Finals.TRANSPORT_FORMULAS) {
                    content += "<th>";
                    content += field;
                    content += "</th>\n";

                }
        }

        content += "</tr>\n";
    }

    public void putLeftSideCell()
    {
        content += "<th class=\"leftSideLine\"></th>\n";
    }
}
