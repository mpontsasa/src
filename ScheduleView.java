import javax.swing.*;

public class ScheduleView extends JPanel implements SuperView  {

    private ScheduleModel myModel;
    private JFrame myFrame;


    public ScheduleView(ScheduleModel myModel, JFrame myFrame) {
        this.myModel = myModel;
        this.myFrame = myFrame;
        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));

        this.add(new ResumeTableView());
        this.add(new OrarGridView());
    }

    @Override
    public void buildFromModel() {

    }

    public void test(){
        myFrame.setTitle("csaaa");
    }


}
