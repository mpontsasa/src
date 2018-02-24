import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UnitView extends JPanel implements SuperView {



    private ArrayList<JPanel> subUnitViews;
    private  TaskView parent;
    private Integer myIndex;

    public UnitView(TaskView parent, int myIndex){
        //NEM URES UNIT KONSTRUKTOR
        this.parent = parent;
        this.myIndex = myIndex;
        subUnitViews = new ArrayList<>();


        JPanel paddingPanel = new JPanel();
        paddingPanel.setPreferredSize(new Dimension(this.getWidth(), 60));
        paddingPanel.setBackground(Finals.UNIT_PADDING_COLOR);

        TaskTableCreator taskTableCreator = new TaskTableCreator(parent.getMyModel());
        String[][] uhvData = taskTableCreator.getUnitHeaderTs()[myIndex];//a unitokat egytol indexeljuk
        UnitHeaderView uhv = new UnitHeaderView(this, uhvData);

        SubUnitView suv = new SubUnitView("MATERIAL", this, myIndex, 0,Finals.SUB_UNIT_TYPE);
        SubUnitView suv2 = new SubUnitView("MANOPERA",this,myIndex,1,Finals.EXTENDED_SUB_UNIT_TYPE);
        SubUnitView suv3 = new SubUnitView("UTILAJ",this, myIndex, 2,Finals.EXTENDED_SUB_UNIT_TYPE);
        SubUnitView suv4 = new SubUnitView("TRANSPORT",this, myIndex, 3,Finals.SUB_UNIT_TYPE);
        subUnitViews.add(suv);subUnitViews.add(suv2);subUnitViews.add(suv3);subUnitViews.add(suv4);


        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));//https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html

        this.add(uhv);
        for(JPanel subUnitView:subUnitViews){
            this.add(subUnitView);
        }




        paddingPanel.setPreferredSize(new Dimension(paddingPanel.getWidth(),10));

        this.add(paddingPanel);

        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }


    public UnitView(TaskView parent) {
        //URES UNIT KONSTRUKTOR

        this.parent = parent;
        subUnitViews = new ArrayList<>();


        JPanel paddingPanel = new JPanel();
        paddingPanel.setPreferredSize(new Dimension(this.getWidth(), 60));
        paddingPanel.setBackground(Finals.UNIT_PADDING_COLOR);

        UnitHeaderView uhv = new UnitHeaderView(this);

        SubUnitView suv = new SubUnitView("MATERIAL",Finals.SUB_UNIT_TYPE);
        SubUnitView suv2 = new SubUnitView("MANOPERA",Finals.EXTENDED_SUB_UNIT_TYPE);
        SubUnitView suv3 = new SubUnitView("UTILAJ", Finals.EXTENDED_SUB_UNIT_TYPE);
        SubUnitView suv4 = new SubUnitView("TRANSPORT",Finals.SUB_UNIT_TYPE);
        subUnitViews.add(suv);subUnitViews.add(suv2);subUnitViews.add(suv3);subUnitViews.add(suv4);


        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));//https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html

        this.add(uhv);
        for(JPanel subUnitView:subUnitViews){
            this.add(subUnitView);
        }




        paddingPanel.setPreferredSize(new Dimension(paddingPanel.getWidth(),10));

        this.add(paddingPanel);

        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public void buildFromModel() {

    }


    public void notifyController(String candidateCode){
        parent.notifyController(candidateCode);
    }

    @Override
    public TaskView getParent() {
        return parent;
    }

    public Integer getMyIndex() {
        return myIndex;
    }
}
