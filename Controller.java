import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Controller {

    private ScheduleModel scheduleModel;
    private TaskModel taskModel;


    private ScheduleView scheduleView;
    private TaskView taskView;
    private JFrame frame;

    private String projectName;

    public Controller(String projectName) {

        this.projectName = projectName;
        initializeFrame();

        scheduleModel = new ScheduleModel();
        taskModel = new TaskModel();//eloszor letrehozzuk a modelleket, aztan atadjuk a viewknak a megfelelo modellt
        scheduleView = new ScheduleView(scheduleModel, frame);
        taskView = new TaskView(taskModel, frame);


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scheduleView.test();
        frame.add(taskView);
        frame.revalidate();
        frame.repaint();
    }

    private void initializeFrame(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,600,450);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Test");

        frame.setVisible(true);
    }


    public void loadTaskFromFile() throws FileNotFoundException{

        Scanner scan = new Scanner(new File(Finals.PROJECTS_PATH + projectName + "_task.txt"));
        while(scan.hasNextLine()){
            //ez a line egy unicode lesz
            String line = scan.nextLine();

            try {
                taskModel.loadLine(line);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        scan.close();
    }

    public void saveProject() throws IOException{

        taskModel.saveTaskToFile(projectName);
        //save Schedule ...
    }

    public void saveUnits(){
        try {
            taskModel.saveUnits();
        } catch (Exception e) {
            System.out.println("error in saving units!");
            e.printStackTrace();
        }
    }

    public void loadScheduleFromFile() throws FileNotFoundException{

        Scanner scan = new Scanner(new File(projectName + "_schedule.txt"));
        while(scan.hasNextLine()){
            String line = scan.nextLine();
            scheduleModel.loadLine(line);
        }
        scan.close();
    }

    public void saveScheduleToFile() throws IOException{

        FileWriter fw = new FileWriter(projectName + "_schedule.txt");

        String currentLine = scheduleModel.saveLine();

        while(!currentLine.equals(Finals.END_OF_PROJECT)) {
            fw.write(currentLine + "\n");
            currentLine = scheduleModel.saveLine();
        }

        fw.close();

    }

    public void insertUnit(int unitIndex){

        try{
            taskModel.insertUnit(unitIndex);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("index error!");
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("netuu");
            e.printStackTrace();
        }

    }

    public void deleteUnit(int unitIndex){

        try{
            taskModel.deleteUnit(unitIndex);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("index error!");
            e.printStackTrace();
        }

    }

    public void insertSubUnit(int unitIndex, int subUnitIndex){

        try{
            taskModel.insertSubUnit(unitIndex,subUnitIndex);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("index error!");
            e.printStackTrace();
        }
    }

    public void deleteSubUnit(int unitIndex, int subUnitIndex){

        try{
            taskModel.deleteSubUnit(unitIndex,subUnitIndex);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("index error!");
            e.printStackTrace();
        }
    }

    public void insertRow(int unitIndex, int subUnitIndex, int rowIndex){

        try{
            taskModel.insertRow(unitIndex,subUnitIndex,rowIndex);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("index error!");
            e.printStackTrace();
        }
    }

    public void deleteRow(int unitIndex, int subUnitIndex, int rowIndex){

        try{
           taskModel.deleteRow(unitIndex,subUnitIndex,rowIndex);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("index error!");
            e.printStackTrace();
        }
    }





    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
