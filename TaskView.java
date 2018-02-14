import javax.swing.*;

public class TaskView extends JPanel implements SuperView {


    private SuperModel myModel;
    private JFrame myFrame;

    public TaskView(SuperModel myModel, JFrame myFrame) {
        this.myModel = myModel;
        this.myFrame = myFrame;
    }

    @Override
    public void buildFromModel() {

    }


}
