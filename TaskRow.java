public class TaskRow {

    String test;

    TaskRow (String line){
        test = line;
    }

    public TaskRow() {
        test = "new row";
    }

    public String saveLine(){
        return "R" + test;
    }

}
