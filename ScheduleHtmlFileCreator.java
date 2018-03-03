import java.io.FileWriter;

public class ScheduleHtmlFileCreator {
    TaskModel taskModel;
    String content = "";
    ScheduleTableCreator stc;
    int lengthOfGrid;

    ScheduleHtmlFileCreator(TaskModel taskModel, String projectName) throws  Exception{
        this.taskModel = taskModel;
        stc = new ScheduleTableCreator(taskModel);
        lengthOfGrid = stc.getMaxDays();
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
//                "}\n" +
                "#selected {\n" +
                "    background-color: green;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table>";

        printHeader();

        for (int i = 0; i < taskModel.getTaskUnits().size(); i++){
            printRow(i);
        }

        content += "</table>\n" +
                "</body>\n" +
                "</html>";
//..............print to file
        FileWriter fw = new FileWriter(projectName + "_grafic_de_executie.html");

        fw.write(content);

        fw.close();
    }

    public void printHeader(){

        content += "<tr>\n";

        content += "<th colspan=\"" + Finals.LENGTH_OF_TASKS_TABLE +"\"> Articole </th>\n";

        for (int i = 0; i < (stc.getMaxDays() + 1) / 7; i++){   // +1 mert 0-tol van inexelve
            content += "<th colspan=\"7\"> Spt. " + i + "</th>\n";
        }

        if ((stc.getMaxDays() + 1) % 7 != 0)   // utolso, nem teljes het // +1 mert 0-tol van inexelve
        {
            content += "<th colspan=\"" + ((stc.getMaxDays() + 1) % 7) + "\"> Spt. " + ((stc.getMaxDays() + 1) / 7 + 1) + "</th>\n";// +1 mert 0-tol van inexelve
        }

        content += "</tr>\n";
        content += "<tr>\n";

        for (String str : Finals.TASKS_TABLE_HEADER){
            content += "<th>" + str + "</th>\n";
        }

        for(int i = 0; i < (stc.getMaxDays() + 1) / 7; i++)
        {
            for (String str : Finals.WEEK_HEADER){
                content += "<th>" + str + "</th>\n";
            }
        }

        for (int  i = 0; i < (stc.getMaxDays() + 1) % 7; i++){

            content += "<th>" + Finals.WEEK_HEADER[i] + "</th>\n";
        }

        content += "</tr>\n";
    }

    public void printRow(int i){

        TaskUnit tu = taskModel.getTaskUnits().get(i);

        content += "<tr>\n";

        content += "<th>";
        content += "" + (i + 1);    // index
        content += "</th>\n";

        content += "<th>";
        content += tu.getUnitTitle();
        content += "</th>\n";

        content += "<th>";
        content += tu.getUnitateMetric();
        content += "</th>\n";

        content += "<th>";
        content += tu.getCantitate();
        content += "</th>\n";

        content += "<th>";
        content += tu.getOre();
        content += "</th>\n";
    //--------------------------------------------------------tasks resz


        for (int j = 0; j < stc.getMaxDays(); j++){
            if (stc.getWeeksTable()[i][j]){ // ha zold
                content += "<th id=\"selected\"></th>\n";
            }
            else    // not seleted
            {
                content += "<th></th>\n";
            }
        }
        content += "</tr>\n";
    }
}
