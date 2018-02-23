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

        //test
        UnitView uv1 = new UnitView(this);
        UnitView uv2 = new UnitView(this);
        UnitView uv3 = new UnitView(this);

        unitViews.add(uv1); unitViews.add(uv2); unitViews.add(uv3);

        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        for(UnitView unitView : unitViews){
            this.add(unitView);
        }
        sumTableView = new SumTableView();
        this.add(sumTableView);
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

    public void addUnit(){
        unitViews.add(new UnitView(this));
        refreshUnits();
    }

    public void addEmptyUnit(){
        //to-do
    }

    public void notifyController(String candidateCode){
        myController.newUnitCodeInserted(candidateCode);
    }
}
