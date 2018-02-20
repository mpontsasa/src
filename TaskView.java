import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TaskView extends JPanel implements SuperView {


    private SuperModel myModel;
    private JFrame myFrame;
    private JTable jt;

    private SubUnitView[] subUnitViews;

    public TaskView(SuperModel myModel, JFrame myFrame) {
        this.myModel = myModel;
        this.myFrame = myFrame;
//        String[] columns= {"name", "age"};
//        String[][] data = {{"sasa","16"},{"matyi", "2"},{"matyi", "2"},{"matyi", "2"},{"matyi", "2"},{"matyi", "2"},{"matyi", "2"}};
//        jt = new JTable(data,columns);
//        jt.getTableHeader().setReorderingAllowed(false);
//        JScrollPane scrollPane = new JScrollPane(jt);
//        jt.setFillsViewportHeight(true);
//        jt.setPreferredScrollableViewportSize(new Dimension(450,450));
        SubUnitView suv = new SubUnitView("MANOPERA");
        SubUnitView suv2 = new SubUnitView("UTILAJE");
        SubUnitView suv3 = new SubUnitView("material");
        SubUnitView suv4 = new SubUnitView("transport");
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));//https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
        this.add(suv);
        this.add(suv2);
        this.add(suv3);
        this.add(suv4);

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


}
