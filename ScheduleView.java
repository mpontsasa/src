import javax.swing.*;

public class ScheduleView extends JPanel implements SuperView  {

    private ScheduleModel myModel;
    private JFrame myFrame;


    public ScheduleView(ScheduleModel myModel, JFrame myFrame) {
        this.myModel = myModel;
        this.myFrame = myFrame;
    }

    @Override
    public void buildFromModel() {

    }

    public void test(){
        myFrame.setTitle("csaaa");
    }


}
