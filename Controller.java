import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    private boolean isSaved;

    public Controller() {

        activeView = Finals.NO_VIEW_ACTIVE;

        taskModel = new TaskModel();//eloszor letrehozzuk a modelleket, aztan atadjuk a viewknak a megfelelo modellt
        initializeFrame();
        isSaved = true;
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

        //System.out.println("amp");
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
        isSaved = false;
        return true;
    }

    public void detaliiProiectChanged(String data){
        taskModel.setDetaliiProiect(data);
        //System.out.println("detaliiiii");
        isSaved = false;
    }

    private boolean unitEdited(int unitIndex, int columnIndex, String data){

        //System.out.println("odjefgthgrf");
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

        isSaved = false;
        return true;
    }

    private boolean rowEdited(int unitIndex, int subUnitIndex, int rowIndex, int columnIndex, String data){

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
                    if (subUnitIndex == 0 || subUnitIndex == 3) //Materil vagy transport
                        row.setFurnizor(data);
                    else {    //manopera vagy utilaj
                        try{

                            row.setNumarDeAlocati(Integer.parseInt(data));
                        }
                        catch (NumberFormatException e) {
                            return false;
                        }
                    }
                    break;
            case 9:
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
        isSaved = false;
        return true;

    }

    public void orarGridChanged(int row, int col, boolean value){
        System.out.println("orar gird changed. new values:"+ row + " " + col + " " + value);

        if (value){ // if selected
            taskModel.getTaskUnits().get(row).getSchedules().add(col);
        }
        else{   // if unselected
            for (int i = 0; i < taskModel.getTaskUnits().get(row).getSchedules().size(); i++)
            {
                if (taskModel.getTaskUnits().get(row).getSchedules().get(i) == col){

                    taskModel.getTaskUnits().get(row).getSchedules().remove(i);
                    break;
                }
            }
        }
        isSaved = false;

    }

    private void initializeViews(){

        taskView = new TaskView(taskModel,frame,this);
        taskShell = new JScrollPane(taskView);
        taskShell.getVerticalScrollBar().setUnitIncrement(25);

        //https://stackoverflow.com/questions/4298582/jscrollpane-scrolling-with-arrow-keys
        JScrollBar vertical = taskShell.getVerticalScrollBar();
        InputMap im = vertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
        im.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");

        scheduleView = new ScheduleView(taskModel,this);
        scheduleShell = new JScrollPane(scheduleView);


    }

    private void askUserConfirmOnExit(){
        String[] options = {"Da","Nu"};
        if(!isSaved){
            if (JOptionPane.showOptionDialog(frame,
                    "Inchideti aplicatia fara salvare?", "Confirmare",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,null,options,null) == JOptionPane.YES_OPTION){

                System.exit(0);
            }
        }
        else {
            System.exit(0);
        }
    }

    private void initializeFrame(){
        
        frame = new MenuFrame(this);




        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                askUserConfirmOnExit();

            }
        });


        frame.setBounds(0,0,600,450);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Test");




        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private void loadProject(String projectName){
        this.projectName = projectName.replaceAll(" ","_");

        try {
            loadTaskFromFile();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
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

    private void loadTaskFromFile() throws Exception{
        taskModel = new TaskModel();
        taskModel.loadProject(projectName);
    }

    private void displayNonExtistingProjectError(){
        JOptionPane.showMessageDialog(frame,
                "Proiectul selectat nu exista!",
                "Proiect inexistent",
                JOptionPane.ERROR_MESSAGE);
    }

    private void displayProjectAlreadyExistsError(){
        JOptionPane.showMessageDialog(frame,
                "Proiectul selectat exista deja!",
                "Proiect existent",
                JOptionPane.ERROR_MESSAGE);
    }

    private void saveProject() throws IOException{

        taskModel.saveTaskToFile(projectName);
    }

    private void saveUnits(){
        try {
            taskModel.saveUnits();
        } catch (Exception e) {
            System.out.println("error in saving units!");
            e.printStackTrace();
        }
    }

    public void saveButtonClicked(){


        if(projectName == null){
            return;
        }

        try{
            saveProject();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        saveUnits();
        isSaved = true;

    }

    public void loadButtonClicked(){
        askUserInputLoadProject();
    }

    public void listareButtonClicked(){
        if(projectName == null){
            return;
        }

        makeHtmlTaskFile();
        makeHtmlScheduleFile();

        File f = null;
        String path = "";
        boolean bool = false;

        try {

            f = new File(Finals.PRINT_PATH + projectName);


            path = f.getAbsolutePath();
            System.out.print("Absolute Pathname "+ path);

            Desktop.getDesktop().open(new File(path));

        } catch(Exception e) {

            // if any error occurs
            e.printStackTrace();
        }
    }

    public void newProjectClicked(){
        askUserInputNewProject();
    }

    private void askUserInputNewProject(){
        jd = new JDialog();

        textField = new JTextField("");
        textField.setColumns(50);


        textField.setVisible(true);

        textField.addActionListener((ActionEvent e) ->{

            if(textField.getText().contains(Finals.TOK_D)){
                JOptionPane.showMessageDialog(frame,
                        "Caracterul " + Finals.TOK_D + " nu este permis",
                        "Caracter invalid",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String pn = textField.getText().replaceAll(" ","_");
            //System.out.println(pn);

            if (Files.isRegularFile(Paths.get(Finals.PROJECTS_PATH + pn + "_task.txt"))){
                displayProjectAlreadyExistsError();

            }
            else
            {
                projectName = pn;
                taskModel = new TaskModel();
                initializeViews();

                frame.getTextArea().setText("");

                activeView = Finals.NO_VIEW_ACTIVE;
                switchViews();

                jd.setVisible(false);

            }

        });


        jd.setTitle("Numele proiectului?");
        jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jd.setSize(300, 75);
        jd.add(textField);
        jd.setLocationRelativeTo(null);
        jd.requestFocus();
        jd.setModal(true);
        jd.setVisible(true);
    }

    private void askUserInputLoadProject(){
        jd = new JDialog();

        textField = new JTextField("");
        textField.setColumns(50);

        textField.setVisible(true);
        textField.addActionListener((ActionEvent e) ->{

            String text = textField.getText();

            System.out.println(text);

            loadProject(textField.getText());

            jd.setVisible(false);

        });


        jd.setTitle("Numele proiectului?");
        jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jd.setSize(300, 75);
        jd.setLocationRelativeTo(null);
        jd.add(textField);
        jd.requestFocus();
        jd.setModal(true);
        jd.setVisible(true);
    }

    private void makeHtmlTaskFile(){
        try{
            TaskHtmlFileCreator htmlCreator = new TaskHtmlFileCreator(taskModel, projectName);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void makeHtmlScheduleFile(){
        try{
            ScheduleHtmlFileCreator htmlCreator = new ScheduleHtmlFileCreator(taskModel, projectName);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void switchViews(){


        if(projectName == null){
            return;
        }
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
        taskView.buildFromModel();

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

    public String getProjectName() {
        return projectName;
    }
}
