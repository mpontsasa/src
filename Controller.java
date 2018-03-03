import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {

    private ScheduleModel scheduleModel;
    private TaskModel taskModel;

    private ScheduleView scheduleView;
    private TaskView taskView;
    private MenuFrame frame;
    private JScrollPane shell;

    private JScrollPane taskShell;
    private JScrollPane scheduleShell;
    private int activeView;

    private JDialog jd;
    private JTextField textField;

    private String projectName;

    public Controller(String projectName) {

        this.projectName = projectName;

        activeView = Finals.NO_VIEW_ACTIVE;
        scheduleModel = new ScheduleModel();
        taskModel = new TaskModel();//eloszor letrehozzuk a modelleket, aztan atadjuk a viewknak a megfelelo modellt
        initializeFrame();
       // scheduleView = new ScheduleView(scheduleModel, frame);

    }

    public void menuTest(){
        System.out.println("azigennn");
    }

    public boolean taskViewEdited(Integer unitIndex, Integer subUnitIndex, Integer rowIndex, Integer columnIndex, String data){
        System.out.println(unitIndex + " " +subUnitIndex +" " + rowIndex + " " + columnIndex + " " + data);

        if (unitIndex == -1) {  //sum table edited
            return amplifiersEdited(columnIndex, data);
        }
        else if (subUnitIndex == -1)  //unit edited
        {
            if (unitIndex < taskModel.getTaskUnits().size()){   // edited, not added
                return unitEdited(unitIndex, columnIndex, data);
            }
            else if(unitIndex == taskModel.getTaskUnits().size()) // new unit added
            {
                taskModel.getTaskUnits().add(new TaskUnit(taskModel, data));
                if (unitEdited(unitIndex, columnIndex, data)){
                    return true;
                }
                else{
                    taskModel.getTaskUnits().remove(taskModel.getTaskUnits().size() - 1);  // remooving last unit
                    return false;
                }
            }
            else    // hibas parameterek
            {
                System.out.println("hibas unitszam taskViewEditedben: " + unitIndex);
                return false;
            }
        }
        else    //row edited
        {
            if (rowIndex < taskModel.getTaskUnits().get(unitIndex).getSubUnits().get(subUnitIndex).getTaskRows().size()){   // edited, not added
                return rowEdited(unitIndex, subUnitIndex, rowIndex, columnIndex, data);
            }
            else if(rowIndex == taskModel.getTaskUnits().get(unitIndex).getSubUnits().get(subUnitIndex).getTaskRows().size()) // new row added
            {
                taskModel.getTaskUnits().get(unitIndex).getSubUnits().get(subUnitIndex).getTaskRows().add(new TaskRow(taskModel.getTaskUnits().get(unitIndex).getSubUnits().get(subUnitIndex)));
                if (rowEdited(unitIndex, subUnitIndex, rowIndex, columnIndex, data)){
                    return true;
                }
                else{
                    taskModel.getTaskUnits().get(unitIndex).getSubUnits().get(subUnitIndex).getTaskRows().remove(taskModel.getTaskUnits().get(unitIndex).getSubUnits().get(subUnitIndex).getTaskRows().size() - 1);//remoove last//
                    return false;
                }

            }
            else    // hibas parameterek
            {
                System.out.println("hibas unitszam taskViewEditedben");
                return false;
            }
        }

    }

    public boolean amplifiersEdited(int amplifierIndex, String data){

        switch (amplifierIndex)
        {
            case 1:
                try{
                    taskModel.setCheltuileIndirecteAmplifier(Float.parseFloat(data));
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
                break;
            case 2:
                try{
                    taskModel.setProfitAmplifier(Float.parseFloat(data));
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
                break;
            case 4:
                try{
                    taskModel.setTVAAmplifier(Float.parseFloat(data));
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
                break;
            default:
                System.out.println("Hibas index amplifierEdited-nel");

        }
        return true;
    }

    public void detaliiProiectChanged(String data){
        taskModel.setDetaliiProiect(data);
    }

    public boolean unitEdited(int unitIndex, int columnIndex, String data){

        switch(columnIndex){
            case 1:
                taskModel.getTaskUnits().get(unitIndex).setUnitTitle(data);
                break;
            case 2:
                taskModel.getTaskUnits().get(unitIndex).setUnitCode(data);
                break;
            case 4:
                taskModel.getTaskUnits().get(unitIndex).setUnitateMetric(data);
                break;
            case 5:
                try{
                    taskModel.getTaskUnits().get(unitIndex).setCantitate(Float.parseFloat(data));
                }
                catch(NumberFormatException e)
                {
                    return false;
                }
                break;
            case 6:
                try{
                    taskModel.getTaskUnits().get(unitIndex).setOre(Float.parseFloat(data));
                }
                catch(NumberFormatException e)
                {
                    return false;
                }
                break;
            default:
                System.out.println("hibas columnIndex unit header editalasanal");
        }

        return true;
    }

    public boolean rowEdited(int unitIndex, int subUnitIndex, int rowIndex, int columnIndex, String data){

        TaskRow row = taskModel.getTaskUnits().get(unitIndex).getSubUnits().get(subUnitIndex).getTaskRows().get(rowIndex);

        switch(columnIndex){
            case 1:
                row.setRowTitle(data);
                break;
            case 2:
                row.setUnitateDeMasura(data);
                break;
            case 3:
                try{

                    row.setCantitateUnitara(Float.parseFloat(data));
                }
                catch (NumberFormatException e){
                    return false;
                }
                break;
            case 4:
                try{

                    row.setPretUnitara(Float.parseFloat(data));
                }
                catch (NumberFormatException e){
                    return false;
                }
                break;
            case 8:
                    row.setFurnizor(data);
                break;
            case 9:
                try{

                    row.setNumarDeAlocati(Integer.parseInt(data));
                }
                catch (NumberFormatException e){
                    return false;
                }
                break;
            case 10:
                try{

                    row.setNumarDeOreNecesare(Float.parseFloat(data));
                }
                catch (NumberFormatException e){
                    return false;
                }
                break;
            default:
                System.out.println("hibas columnIndex unit header editalasanal");
        }

        System.out.println("Cell edit: " + unitIndex + " " +subUnitIndex +" " + rowIndex + " " + columnIndex + " " + data);
        return true;

    }

    public void initializeViews(){
        //ahhoz, hogy lehessen scrollozni az ablakban, egy scrollpane-be teszem az egesz taskViewt, s azt a burkot a framebe

        //taskView = new TaskView(taskModel,frame,this);
//        taskShell = new JScrollPane(taskView);
//        taskShell.getVerticalScrollBar().setUnitIncrement(16);


//        frame.add(shell,BorderLayout.CENTER);
        //frame.setContentPane(shell);
        //activeView = Finals.TASK_VIEW_ACTIVE;

//        scheduleView = new ScheduleView(scheduleModel,frame);
//        shell = new JScrollPane(scheduleView);
//        frame.add(scheduleView, BorderLayout.CENTER);

        taskView = new TaskView(taskModel,frame,this);
        taskShell = new JScrollPane(taskView);
        taskShell.getVerticalScrollBar().setUnitIncrement(16);

        scheduleView = new ScheduleView(taskModel,this);
        scheduleShell = new JScrollPane(scheduleView);


    }

    private void initializeFrame(){
        frame = new MenuFrame(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,600,450);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Test");


        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    public void loadProject(String projectName){
        this.projectName = projectName;


        try {
            loadTaskFromFile();
        }
        catch (FileNotFoundException e)
        {
            displayNonExtistingProjectError();
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        frame.getTextArea().setText(taskModel.getDetaliiProiect());

        initializeViews();

        activeView = Finals.NO_VIEW_ACTIVE;
        switchViews();
    }

    public void loadTaskFromFile() throws Exception{
        taskModel = new TaskModel();
        taskModel.loadProject(projectName);
    }

    public void displayNonExtistingProjectError(){
        System.out.println("not existing proiect");
    }

    public void saveProject() throws IOException{

        taskModel.saveTaskToFile(projectName);
    }

    public void saveUnits(){
        try {
            taskModel.saveUnits();
        } catch (Exception e) {
            System.out.println("error in saving units!");
            e.printStackTrace();
        }
    }

    public void saveButtonClicked(){
        try{
            saveProject();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        saveUnits();

        System.out.println("saved!");
    }

    public void loadButtonClicked(){



        askUserInput();
        loadProject(textField.getText());

//        if(activeView == Finals.TASK_VIEW_ACTIVE){
//            taskView.buildFromModel();
//        }
    }

    public void newProjectClicked(){
        askUserInput();
        projectName = textField.getText();
        taskModel = new TaskModel();
        initializeViews();

        frame.getTextArea().setText("");

        initializeViews();

        activeView = Finals.NO_VIEW_ACTIVE;
        switchViews();
    }

    private void askUserInput(){
        jd = new JDialog();

        textField = new JTextField("");
        textField.setColumns(50);

        textField.setVisible(true);
        textField.addActionListener((ActionEvent e) ->{

            String text = textField.getText();

            System.out.println(text);



            jd.setVisible(false);

        });


        jd.setTitle("Numele proiectului?");
        jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jd.setSize(300, 75);
        jd.add(textField);
        jd.requestFocus();
        jd.setModal(true);
        jd.setVisible(true);
    }

    public void makeHtmlTaskFile(){
        try{
            TaskHtmlFileCreator htmlCreator = new TaskHtmlFileCreator(taskModel, projectName);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void makeHtmlScheduleFile(){
        try{
            ScheduleHtmlFileCreator htmlCreator = new ScheduleHtmlFileCreator(taskModel, projectName);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void switchViews(){

        switch (activeView){

            case Finals.TASK_VIEW_ACTIVE:



                scheduleView = new ScheduleView(taskModel,this);
                scheduleShell = new JScrollPane(scheduleView);

                frame.setContentPane(scheduleShell);

                activeView = Finals.SCHEDULE_VIEW_ACTIVE;
                frame.revalidate();
                frame.repaint();
                break;
            case Finals.NO_VIEW_ACTIVE:
            case Finals.SCHEDULE_VIEW_ACTIVE:



                frame.setContentPane(taskShell);

                activeView = Finals.TASK_VIEW_ACTIVE;
                frame.revalidate();
                frame.repaint();
                break;

            default:
                System.out.println("error on switching views!");
        }

    }

    public void orarGridChanged(int row, int col, boolean value){
        System.out.println("orar gird changed. new values:"+ row + " " + col + " " + value);
    }
    //        scan.close();
    //        }
    //            scheduleModel.loadLine(line);
    //            String line = scan.nextLine();
    //        while(scan.hasNextLine()){
    //        Scanner scan = new Scanner(new File(projectName + "_schedule.txt"));
    //
//    public void loadScheduleFromFile() throws FileNotFoundException{

//    }
    //
    //        fw.close();
    //
    //        }
    //            currentLine = scheduleModel.saveLine();
    //            fw.write(currentLine + "\n");
    //        while(!currentLine.equals(Finals.END_OF_PROJECT)) {
    //
    //        String currentLine = scheduleModel.saveLine();
    //
    //        FileWriter fw = new FileWriter(projectName + "_schedule.txt");
    //
//    public void saveScheduleToFile() throws IOException{

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

//    public void insertRow(int unitIndex, int subUnitIndex, int rowIndex){
//
//        try{
//            taskModel.insertRow(unitIndex,subUnitIndex,rowIndex);
//        }
//        catch (IndexOutOfBoundsException e){
//            System.out.println("index error!");
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteRow(int unitIndex, int subUnitIndex, int rowIndex){
//
//        try{
//           taskModel.deleteRow(unitIndex,subUnitIndex,rowIndex);
//        }
//        catch (IndexOutOfBoundsException e){
//            System.out.println("index error!");
//            e.printStackTrace();
//        }
//    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void newUnitCodeInserted(String candidateCode){
        System.out.println("New code insterted:"+candidateCode);
        //taskView.addUnit();
    }


//    public void printComponenet(JComponent component){

//        PrintUtil pu = new PrintUtil(component);
//        pu.print();
//
//
////        PrinterJob pj = PrinterJob.getPrinterJob();
////        pj.setJobName(" Print Component ");
////        PageFormat pf = pj.defaultPage();
////        pf.setOrientation(PageFormat.LANDSCAPE);
////        //pj.setPrintable(new Printable(), pf);
////
////        //printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);
////
////
////
////        pj.setPrintable (new Printable() {
////            public int print(Graphics pg, PageFormat pf, int pageNum){
////                if (pageNum > 0){
////                    return Printable.NO_SUCH_PAGE;
////                }
////
////                Graphics2D g2 = (Graphics2D) pg;
////                g2.translate(pf.getImageableX(), pf.getImageableY());
////                g2.scale(0.7,0.7);
////                component.paint(g2);
////
////                return Printable.PAGE_EXISTS;
////            }
////        },pf);
////
////        if (pj.printDialog() == false)
////            return;
////
////        try {
////            pj.print();
////        } catch (PrinterException ex) {
////            // handle exception
////        }
//    }

    public TaskView getTaskView() {
        return taskView;
    }
}
