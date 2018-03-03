public class MissingDetaliiException extends Exception {

    private String line;

    public MissingDetaliiException(String line) {
        this.line = line;
    }
}
