package nl.sourcelabs.workshop.testing.application.exception;

public class NoEmployeeFoundException extends RuntimeException {

    public NoEmployeeFoundException() {
        super("No Employee could be found");
    }
}
