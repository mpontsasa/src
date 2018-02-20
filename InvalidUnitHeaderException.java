public class InvalidUnitHeaderException extends Exception {

    private String line;

    public InvalidUnitHeaderException(String line) {
        this.line = line;
    }
}
