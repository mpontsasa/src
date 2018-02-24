import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UnitView extends JPanel implements SuperView {



    private ArrayList<JPanel> subUnitViews;
    private  TaskView parent;

    public UnitView(TaskView parent, int myIndex){
        //NEM URES UNIT KONSTRUKTOR
        this.parent = parent;
        subUnitViews = new ArrayList<>();


        JPanel paddingPanel = new JPanel();
        paddingPanel.setPreferredSize(new Dimension(this.getWidth(), 60));
        paddingPanel.setBackground(Finals.UNIT_PADDING_COLOR);

        TaskTableCreator taskTableCreator = new TaskTableCreator(parent.getMyModel());
        String[][] uhvData = taskTableCreator.getUnitHeaderTs()[myIndex];//a unitokat egytol indexeljuk
        UnitHeaderView uhv = new UnitHeaderView(this, uhvData);

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


        this.add(paddingPanel);

    }


    public UnitView(TaskView parent) {
        //URES UNIT KONSTRUKTOR

        this.parent = parent;
        subUnitViews = new ArrayList<>();


        JPanel paddingPanel = new JPanel();
        paddingPanel.setPreferredSize(new Dimension(this.getWidth(), 60));
        paddingPanel.setBackground(Finals.UNIT_PADDING_COLOR);

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

        this.add(paddingPanel);


    }

    @Override
    public void buildFromModel() {

    }


    public void notifyController(String candidateCode){
        parent.notifyController(candidateCode);
    }
}
