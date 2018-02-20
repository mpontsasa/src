public class MissingUnitHeaderException extends Exception {

    private String line;

    public MissingUnitHeaderException(String line) {
        this.line = line;
    }
}

