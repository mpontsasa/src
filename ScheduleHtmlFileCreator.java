import java.io.FileWriter;

public class ScheduleHtmlFileCreator {
    TaskModel taskModel;
    String content = "";
    ScheduleTableCreator stc;

    ScheduleHtmlFileCreator(TaskModel taskModel, String projectName) throws  Exception{
        this.taskModel = taskModel;
        stc = new ScheduleTableCreator(taskModel);
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
            //printUnit(i);
            content += "<br><br>";
        }

        content += "</body>\n" +
                "</html>";
//..............print to file
        FileWriter fw = new FileWriter(projectName + ".html");

        fw.write(content);

        fw.close();
    }
}
