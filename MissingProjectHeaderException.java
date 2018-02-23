public class MissingProjectHeaderException extends Exception {

    private String line;

    public MissingProjectHeaderException(String line) {
        this.line = line;
    }
}
