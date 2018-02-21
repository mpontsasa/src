import javax.swing.*;
import java.util.ArrayList;

public class TaskView extends JPanel {

    ArrayList<UnitView> unitViews;
    SuperModel myModel;
    JFrame myFrame;

    public TaskView(SuperModel myModel, JFrame myFrame) {

        this.myFrame = myFrame;
        this.myModel = myModel;
        unitViews = new ArrayList<>();

        //test
        UnitView uv1 = new UnitView();
        UnitView uv2 = new UnitView();
        UnitView uv3 = new UnitView();

        unitViews.add(uv1); unitViews.add(uv2); unitViews.add(uv3);

        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        for(UnitView unitView : unitViews){
            this.add(unitView);
        }

        //test vege



    }


    public void refreshUnits(){
        //REMOVES ALL UNITS FROM THE PANEL AND READDS ALL UNITS IN THE LIST

        this.removeAll();
        this.revalidate();
        for(UnitView unitView : unitViews){
            this.add(unitView);
        }
        this.revalidate();
        this.repaint();
    }

    public void addUnit(UnitView unitView){
        unitViews.add(unitView);
        refreshUnits();
    }

    public void addEmptyUnit(){
        //to-do
    }
}
