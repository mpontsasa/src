import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args){
        Controller controller = new Controller("uj_tesztike");
        try {
            controller.loadTaskFromFile();
            //controller.insertUnit(0);
            controller.saveUnits();
            controller.saveProject();


            ///<INSERT TEST>
//            controller.insertUnit(2);
//            controller.insertSubUnit(2,0);
//            controller.insertRow(2,0,0);
//
//            controller.insertSubUnit(1,0);
//            controller.insertRow(1,0,0);
//
//            controller.insertRow(0,0,0);
//
//            controller.insertUnit(4);


            //</INSERT TEST>


            //<DELETE TEST>

//            controller.deleteUnit(1);
//
//            //controller.deleteSubUnit(0,1);
//
//            controller.deleteRow(0,0,1);

            //</DELETE TEST>

            //controller.setProjectName("tesztike_mentes");
            //controller.saveTaskToFile();

        } catch (IOException e) {
            e.printStackTrace();
        }


//        ArrayList<String> t = new ArrayList<>();
//
//        t.add("elso");t.add("masodik");t.add("harrmadik");
//        t.add(1,"szerussz");
//        t.add(0,"nulla");
//        //t.add(-1,"s");
//        //System.out.println(t.get(20));
//
//       // t.remove(-1);
//        t.remove(2)

    }
}
