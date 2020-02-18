package nl.sourcelabs.workshop.testing.unit.captor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Service {

    public static final String APP_ID = "DuncansApp";

    private final Client client;

    public Service(final Client client) {
        this.client = client;
    }

    public void sendRequest(final String message) {

        final String header = makeHeader();

        final Request input = new Request(header, message);

        client.sendRequest(input);

    }

    private String makeHeader() {
        return APP_ID + " " + LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }

}
