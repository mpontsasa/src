import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UnitView extends JPanel implements SuperView {


    //private SuperModel myModel;
    //private JFrame myFrame;
    private JTable jt;

    private ArrayList<JPanel> subUnitViews;
    private  TaskView parent;

    public UnitView(TaskView parent) {
        //this.myModel = myModel;
        //this.myFrame = myFrame;
        this.parent = parent;
        subUnitViews = new ArrayList<>();

//        String[] columns= {"name", "age"};
//        String[][] data = {{"sasa","16"},{"matyi", "2"},{"matyi", "2"},{"matyi", "2"},{"matyi", "2"},{"matyi", "2"},{"matyi", "2"}};
//        jt = new JTable(data,columns);
//        jt.getTableHeader().setReorderingAllowed(false);
//        JScrollPane scrollPane = new JScrollPane(jt);
//        jt.setFillsViewportHeight(true);
//        jt.setPreferredScrollableViewportSize(new Dimension(450,450));
        JPanel paddingPanel = new JPanel();
        paddingPanel.setPreferredSize(new Dimension(this.getWidth(), 60));
        //paddingPanel.setBackground(Color.BLUE);
        UnitHeaderView uhv = new UnitHeaderView(this);

        SubUnitView suv = new SubUnitView("MATERIAL");
        ExtendedSubUnitView suv2 = new ExtendedSubUnitView("MANOPERA");
        ExtendedSubUnitView suv3 = new ExtendedSubUnitView("UTILAJ");
        SubUnitView suv4 = new SubUnitView("TRANSPORT");
        subUnitViews.add(suv);subUnitViews.add(suv2);subUnitViews.add(suv3);subUnitViews.add(suv4);


        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));//https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html

        this.add(uhv);
        for(JPanel subUnitView:subUnitViews){
            this.add(subUnitView);
        }
//        this.add(suv);
//        this.add(suv2);
//        this.add(suv3);
//        this.add(suv4);
        this.add(paddingPanel);



//        JTextField tf = new JTextField(20);
//
//
//        tf.addActionListener(e->{
//            System.out.println("oioi");
//        });
//
//        tf.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusLost(FocusEvent e) {
//                super.focusLost(e);
//                System.out.println(":(");
//            }
//        });
//        this.add(tf);
//        this.add(new JTextField(10));

    }

    @Override
    public void buildFromModel() {

    }


    public void notifyController(String candidateCode){
        parent.notifyController(candidateCode);
    }
}
