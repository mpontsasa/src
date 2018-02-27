import java.io.FileWriter;

public class HtmlFileCreator {

    TaskModel taskModel;
    String content = "";
    TaskTableCreator ttc;

    HtmlFileCreator(TaskModel taskModel, String projectName) throws  Exception{
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
                "td, th {\n" +
                "    border: 1px solid #dddddd;\n" +
                "    text-align: left;\n" +
                "    padding: 8px;\n" +
                "}\n" +
                "\n" +
//                "tr:nth-child(even) {\n" +
//                "    background-color: #dddddd;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>";


        for (int i = 0; i < taskModel.getTaskUnits().size(); i++){
            printUnit(i);
            content += "<br><br>";
        }

        content += "</body>\n" +
                "</html>";
//..............print to file
        FileWriter fw = new FileWriter(projectName + ".html");

        fw.write(content);

        fw.close();
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
        for (String field : Finals.UNIT_TABLE_HEADER){
            content += "<th>";
            content += field;
            content += "</th>\n";

        }
        content += "</tr>\n";

        content += "<tr>\n";

        for (String field : ttc.getUnitHeaderTs()[unitIndex][0]){
            content += "<th>";
            content += field;
            content += "</th>\n";

        }

        content += "</tr>\n";

        content += "</table>\n";
    }

    public void printSubunit( int unitIndex, int subUnitIndex){
        printSubunitHeader(unitIndex, subUnitIndex);


        for (int row = 0 ; row < ttc.getSubUnitTs()[unitIndex][subUnitIndex].length; row++){
            content += "<tr>";
            for (int field = 0; field < ttc.getSubUnitTs()[unitIndex][subUnitIndex][row].length; field++){
                content += "<th>\n";
                content += ttc.getSubUnitTs()[unitIndex][subUnitIndex][row][field];
                content += "</th>\n";
            }
            content += "</tr>\n";
        }

        content += "</table>";
    }

    public void printSubunitHeader(int unitIndex, int subUnitIndex){
        content += "<table>";

        content += "<tr>\n";
        content += "<th>";
        content += Finals.SUB_UNIT_TITLES[subUnitIndex];
        content += "</th>\n";
        content += "</tr>\n";

        content += "<tr>\n";
        if (subUnitIndex == 0 || subUnitIndex == 3) { //hogyha sima subunit(nem extended)
            for (String field : Finals.SUB_UNIT_TABLE_HEADER) {
                content += "<th>";
                content += field;
                content += "</th>\n";

            }
        }
        else{   //extended subunit esete
            for (String field : Finals.EXTENDED_SUB_UNIT_TABLE_HEADER) {
                content += "<th>";
                content += field;
                content += "</th>\n";

            }
        }
        content += "</tr>\n";
    }
}
