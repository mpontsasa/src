public class InvalidDetaliiException extends Exception {

    private String line;

    public InvalidDetaliiException(String line) {
        this.line = line;
    }
}
