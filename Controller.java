import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

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
            case 2:
                try{
                    taskModel.setProfitAmplifier(Float.parseFloat(data));
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
            case 4:
                try{
                    taskModel.setTVAAmplifier(Float.parseFloat(data));
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
        }
        return true;
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

        return false;
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
        initializeFrame();
        taskView = new TaskView(taskModel,frame,this);
        shell = new JScrollPane(taskView);
        shell.getVerticalScrollBar().setUnitIncrement(16);


        frame.add(shell,BorderLayout.CENTER);

//        scheduleView = new ScheduleView(scheduleModel,frame);
//        shell = new JScrollPane(scheduleView);
//        frame.add(scheduleView, BorderLayout.CENTER);

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

    public void makeHtmlTaskFile(){
        try{
            TaskHtmlFileCreator htmlCreator = new TaskHtmlFileCreator(taskModel, projectName);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

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
