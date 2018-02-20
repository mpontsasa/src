// az orarend tablazathoz hasznaljuk
public class ScheduleModel implements  SuperModel{

    private int n = 0;

    @Override
    public void loadLine(String line){
        System.out.println("schedule got:" + line);
    }


    public String saveLine(){
        if(n < 3){
            n++;
            return "scheduleeeetestsaveline";
        }

        else
            return Finals.END_OF_PROJECT;
    }
}
