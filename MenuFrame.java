import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.io.IOException;

public class MenuFrame extends JFrame implements MenuListener {

    private JMenuBar menuBar;
    private JMenu save;
    private Controller myController;


    public MenuFrame(Controller myController) throws HeadlessException {
        this.myController = myController;
        menuBar = new JMenuBar();
        save = new JMenu("Salvare!");
        save.addMenuListener(this);
        menuBar.add(save);
        this.setJMenuBar(menuBar);
    }

    @Override
    public void menuSelected(MenuEvent e) {
        myController.saveButtonClicked();
        
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
