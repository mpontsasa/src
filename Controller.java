import javax.print.attribute.standard.OrientationRequested;
import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
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
    private MenuFrame frame;
    private JScrollPane shell;

    private String projectName;

    public Controller(String projectName) {

        this.projectName = projectName;


        scheduleModel = new ScheduleModel();
        taskModel = new TaskModel();//eloszor letrehozzuk a modelleket, aztan atadjuk a viewknak a megfelelo modellt
        scheduleView = new ScheduleView(scheduleModel, frame);

    }

    public void viewEdited(Integer unitIndex, Integer subUnitIndex, Integer rowIndex, Integer columnIndex, String data){
        System.out.println(unitIndex + " " +subUnitIndex +" " + rowIndex + " " + columnIndex + " " + data);
    }

    public void initializeViews(){
        //ahhoz, hogy lehessen scrollozni az ablakban, egy scrollpane-be teszem az egesz taskViewt, s azt a burkot a framebe
        initializeFrame();
        taskView = new TaskView(taskModel,frame,this);
        shell = new JScrollPane(taskView);
        shell.getVerticalScrollBar().setUnitIncrement(16);


        frame.add(shell,BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private void initializeFrame(){
        frame = new MenuFrame(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,600,450);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Test");
    }

    public void loadTaskFromFile() throws FileNotFoundException{
        try{
            taskModel.loadProject(projectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

//    public void loadScheduleFromFile() throws FileNotFoundException{
//
//        Scanner scan = new Scanner(new File(projectName + "_schedule.txt"));
//        while(scan.hasNextLine()){
//            String line = scan.nextLine();
//            scheduleModel.loadLine(line);
//        }
//        scan.close();
//    }

//    public void saveScheduleToFile() throws IOException{
//
//        FileWriter fw = new FileWriter(projectName + "_schedule.txt");
//
//        String currentLine = scheduleModel.saveLine();
//
//        while(!currentLine.equals(Finals.END_OF_PROJECT)) {
//            fw.write(currentLine + "\n");
//            currentLine = scheduleModel.saveLine();
//        }
//
//        fw.close();
//
//    }

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

    public void newUnitCodeInserted(String candidateCode){
        System.out.println("New code insterted:"+candidateCode);
        taskView.addUnit();
    }

    public void saveButtonClicked(){
        System.out.println("saved!");
//        try {
//            saveProject();
//        } catch (IOException e) {
//            e.printStackTrace();
//            //LE KELL KEZELNI
//        }
//        saveUnits();
    }

    public void printUnits() {
        for (JPanel unit : taskView.getUnitViews()){
            printComponenet(unit);
        }
        //JPanel unit = taskView.getUnitViews().get(0);
    }

    public void printComponenet(JComponent component){

        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName(" Print Component ");
        PageFormat pf = pj.defaultPage();
        pf.setOrientation(PageFormat.LANDSCAPE);
        //pj.setPrintable(new Printable(), pf);

        //printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);

        pj.setPrintable (new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum){
                if (pageNum > 0){
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                g2.scale(0.7,0.7);
                component.paint(g2);

                return Printable.PAGE_EXISTS;
            }
        },pf);

        if (pj.printDialog() == false)
            return;

        try {
            pj.print();
        } catch (PrinterException ex) {
            // handle exception
        }
    }

    public TaskView getTaskView() {
        return taskView;
    }
}
