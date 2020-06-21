package nl.sourcelabs.workshop.testing.exception;

public class NoEmployeeFoundException extends RuntimeException {

    public NoEmployeeFoundException() {
        super("No Employee could be found");
    }
}
