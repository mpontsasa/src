public class InvalidSubUnitHeaderException extends Exception {

    private String line;

    public InvalidSubUnitHeaderException(String line) {
        this.line = line;
    }
}
