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
                "}\n" +
                "#selected {\n" +
                "    background-color: green;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>";


        for (int i = 0; i < taskModel.getTaskUnits().size(); i++){
            printRow(i);
        }

        content += "</body>\n" +
                "</html>";
//..............print to file
        FileWriter fw = new FileWriter(projectName + ".html");

        fw.write(content);

        fw.close();
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

        int k = 0;
        for (int j = 0; j < tu.getSchedules().size(); j ++) //minden schedul ele beirja az elotte levo ures negyzeteket es magat a kitoltott negyzetet
        {

            for ( ;k < tu.getSchedules().get(j); k++){
                content += "<th></th>\n";
            }

            content += "<th id = \"selected\"></th>\n";
        }

        for (; k < stc.getMaxDays(); k++)
            content += "<th></th>\n";

        content += "</tr>\n";
    }
}
