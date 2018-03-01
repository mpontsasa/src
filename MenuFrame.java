import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.io.IOException;

public class MenuFrame extends JFrame implements MenuListener {

    private JMenuBar menuBar;
    private JMenu save;
    private JMenu print;
    private Controller myController;


    public MenuFrame(Controller myController) throws HeadlessException {
        this.myController = myController;
        menuBar = new JMenuBar();
        save = new JMenu("Salvare!");
        save.addMenuListener(this);

        print = new JMenu("Listare!");
        print.addMenuListener(this);

        menuBar.add(save);
        menuBar.add(print);
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
