public class InvalidProjectHeaderException extends Exception {

    private String line;

    public InvalidProjectHeaderException(String line) {
        this.line = line;
    }
}
