import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class MenuFrame extends JFrame {

    private JMenuBar menuBar;
    private JMenu file;

    private JMenu options;

    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem newProject;

    private JMenuItem details;
    private JMenuItem print;
    private JMenuItem switchViews;

    private  JDialog jd;
    private JTextArea textArea;

    private Controller myController;


    public MenuFrame(Controller myController) throws HeadlessException {
        this.myController = myController;
        menuBar = new JMenuBar();
        setupTextArea();



        save = new JMenuItem("Salvare proiect");
        save.addActionListener(e->this.myController.saveButtonClicked());

        load = new JMenuItem("Incarcare proiect");
        load.addActionListener(e->this.myController.loadButtonClicked());

        newProject = new JMenuItem("Proiect nou");
        newProject.addActionListener(e->this.myController.newProjectClicked());


        details = new JMenuItem("Detalii proiect");
        details.addActionListener(e->jd.setVisible(true));

        print = new JMenuItem("Listare");
        print.addActionListener(e->this.myController.listareButtonClicked());

        switchViews = new JMenuItem("Schimbare");
        switchViews.addActionListener(e->this.myController.switchViews());

        file = new JMenu("File");
        options = new JMenu("Options");
        //file.addMenuListener(this);

        file.add(newProject);file.add(load);file.add(save);

        options.add(details);options.add(print);options.add(switchViews);


        menuBar.add(file);
        menuBar.add(options);
        this.setJMenuBar(menuBar);
    }


    private void setupTextArea(){

        jd = new JDialog();
        textArea = new JTextArea();
        textArea.setVisible(true);


        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                myController.detaliiProiectChanged(textArea.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                myController.detaliiProiectChanged(textArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });




        jd.setTitle("Detalii proiect");
        jd.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        jd.setSize(400, 300);
        jd.add(textArea);
        jd.requestFocus();
        jd.setModal(true);

    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
