import javax.swing.*;
import java.awt.*;

public class ScheduleView extends JPanel implements SuperView  {

    private ScheduleModel myModel;
    private JFrame myFrame;
    JSplitPane splitPane;


    public ScheduleView(ScheduleModel myModel, JFrame myFrame) {
        this.myModel = myModel;
        this.myFrame = myFrame;
        //this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        //this.setLayout(new GridLayout(0,2));
        this.setLayout(new FlowLayout());

        int numOfRows = 3;
        int numOfColumns = 36;



        /*na sozval itt annyirol van szo, hogy a ket viewban levo ket scrollpanet kiszedtem, s ezeket rakom egy splitpane-be
        s beallitom hogy az elvalasztas a screen felenel legyen. Kicsit meg lehetne szebben is irni, de most faradt vagyok
         */
        ResumeTableView resumeTableView = new ResumeTableView();
        OrarGridView orarGridView = new OrarGridView(numOfRows,numOfColumns);






        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                resumeTableView.getScrollPane(), orarGridView.getGridScrollPane());
        splitPane.setOneTouchExpandable(true);
        long id = Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2);
        int idd = (int)id + 10;
        splitPane.setDividerLocation(

             idd
        );


        Dimension minimumSize = new Dimension(300, 50);
        resumeTableView.getScrollPane().setMinimumSize(minimumSize);
        orarGridView.getGridScrollPane().setMinimumSize(minimumSize);

        splitPane.setPreferredSize(new Dimension(1500,400));

        this.add(splitPane);

//        this.add(resumeTableView.getScrollPane());
//        this.add(orarGridView.getGridScrollPane());
    }

    @Override
    public void buildFromModel() {

    }

    public void test(){
        myFrame.setTitle("csaaa");
    }


}
