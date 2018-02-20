public class InvalidRowHeaderException extends Exception {

    private String line;

    public InvalidRowHeaderException(String line) {
        this.line = line;
    }
}
