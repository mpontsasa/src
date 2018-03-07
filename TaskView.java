import javax.swing.*;
import java.util.ArrayList;

public class TaskView extends JPanel {

    ArrayList<UnitView> unitViews;
    SuperModel myModel;
    JFrame myFrame;
    SumTableView sumTableView;
    Controller myController;

    public TaskView(SuperModel myModel, JFrame myFrame, Controller myController) {

        this.myFrame = myFrame;
        this.myModel = myModel;
        this.myController= myController;
        unitViews = new ArrayList<>();

        //  ez egy ures teszt model

        //test
//        UnitView uv1 = new UnitView(this);
//        UnitView uv2 = new UnitView(this);
//        UnitView uv3 = new UnitView(this);
//
//        unitViews.add(uv1); //unitViews.add(uv2); unitViews.add(uv3);

        //idaig tartott

        sumTableView = new SumTableView(this);

        buildFromModel();


        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
//        for(UnitView unitView : unitViews){
//            this.add(unitView);
//        }

        //this.add(sumTableView);
        //test vege



    }

    public void refreshUnits(){
        //REMOVES ALL UNITS FROM THE PANEL AND READDS ALL UNITS IN THE LIST

        this.removeAll();
        this.revalidate();
        for(UnitView unitView : unitViews){
            this.add(unitView);
        }

        this.add(sumTableView);
        this.revalidate();
        this.repaint();
    }

//    public void addUnit(){
//        addEmptyUnit();
//        //KELL MODOSITANI
//
//        refreshUnits();
//    }

    public void addEmptyUnit(){
        //to-do
        unitViews.add(new UnitView(this));
        refreshUnits();
    }

    public void notifyController(String candidateCode){
        System.out.println("New code insterted:"+candidateCode);
        Integer newIndex = unitViews.size() - 1;// azert kell ide a - 1 mert az utolso unit az szellem unit es en azelotti vagyok, marmint a most beillesztette uj unit az az utolso elotti mivel az utolso az a szellem unit
        System.out.println("new index:" + newIndex);
        if(!myController.taskViewEdited(newIndex,-1,-1,2,candidateCode)){
            //ez boolt terit vissza de egyelore az if minden agan ugyanaz lenne
            JOptionPane.showMessageDialog(myFrame,
                    "Format incorect.",
                    "Caracter invalid",
                    JOptionPane.ERROR_MESSAGE);
        }
        buildFromModel();
        //addEmptyUnit();
    }

    public void buildFromModel() {
        TaskTableCreator taskTableCreator = new TaskTableCreator((TaskModel) myModel);
        unitViews.clear();
        for(int i = 0; i < taskTableCreator.getUnitHeaderTs().length; i++){
            //int unitIndex = Integer.parseInt(taskTableCreator.getUnitHeaderTs()[i][0][0]) - 1;//1tol indexelunk a headerekben
            unitViews.add(new UnitView(this,i));
        }
        addEmptyUnit();
        sumTableView = new SumTableView(this);
        refreshUnits();
    }


    public void cellChanged(Integer unitIndex, Integer subUnitIndex, Integer rowIndex, Integer columnIndex, String data){
        if(!myController.taskViewEdited(unitIndex,subUnitIndex,rowIndex,columnIndex,data)){
            JOptionPane.showMessageDialog(myFrame,
                    "Format incorect.",
                    "Caracter invalid",
                    JOptionPane.ERROR_MESSAGE);
        }
        buildFromModel();
    }

    public void amplifiersEdited(int amplifierIndex, String data){
        if(!myController.amplifiersEdited(amplifierIndex,data)){
            JOptionPane.showMessageDialog(myFrame,
                  "Format incorect.",
                  "Caracter invalid",
                  JOptionPane.ERROR_MESSAGE);
        }
        buildFromModel();
    }

    public void deleteUnitClicked(int unitIndex){
        myController.deleteUnit(unitIndex);
    }

    public TaskModel getMyModel() {
        return (TaskModel)myModel;
    }

    public ArrayList<UnitView> getUnitViews() {
        return unitViews;
    }
}
