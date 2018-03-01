import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

public class MenuFrame extends JFrame implements MenuListener {

    private JMenuBar menuBar;
    private JMenu file;

    private JMenu options;

    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem newProject;

    private JMenuItem print;
    private JMenuItem switchViews;

    private Controller myController;


    public MenuFrame(Controller myController) throws HeadlessException {
        this.myController = myController;
        menuBar = new JMenuBar();

        save = new JMenuItem("Salvare proiect");
        load = new JMenuItem("Incarcare proiect");
        newProject = new JMenuItem("Proiect nou");
        print = new JMenuItem("Listare");
        print.addActionListener(e->this.myController.menuTest());

        switchViews = new JMenuItem("Schimbare");

        file = new JMenu("File");
        options = new JMenu("Options");
        //file.addMenuListener(this);

        file.add(newProject);file.add(load);file.add(save);

        options.add(print);options.add(switchViews);

        //options.addMenuListener(this);

        menuBar.add(file);
        menuBar.add(options);
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
