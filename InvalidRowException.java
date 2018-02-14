public class InvalidRowException extends Exception {

    private String line;

    public InvalidRowException(String line) {
        this.line = line;
    }
}
