public class MissingUnitException extends Exception {

    String line;

    public MissingUnitException(String line) {
        this.line = line;
    }


}
