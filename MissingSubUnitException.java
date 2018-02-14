public class MissingSubUnitException extends Exception {

    private String line;

    public MissingSubUnitException(String line) {
        this.line = line;
    }
}
