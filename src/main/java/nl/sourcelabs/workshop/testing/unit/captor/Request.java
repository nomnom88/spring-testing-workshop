package nl.sourcelabs.workshop.testing.unit.captor;

public class Request {

    private final String header;
    private final String message;

    public Request(final String header, final String message) {
        this.header = header;
        this.message = message;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }
}
