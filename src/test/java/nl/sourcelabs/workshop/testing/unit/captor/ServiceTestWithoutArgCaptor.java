package nl.sourcelabs.workshop.testing.unit.captor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import lombok.Getter;

public class ServiceTestWithoutArgCaptor {

    private MyClient client;

    private Service sut;

    @Test
    public void given_message_thenExpect_clientCalledWithAppropriateRequest() {

        final String message = "My Message Text";
        final String expectedPrefix = Service.APP_ID + " ";

        sut.sendRequest(message);

        final Request sentRequest = client.getSentRequest();
        assertThat(sentRequest).isNotNull();
        assertThat(sentRequest.getMessage()).isEqualTo(message);
        assertThat(sentRequest.getHeader()).startsWith(expectedPrefix);
        assertThatCode(() -> {
            DateTimeFormatter.ISO_DATE.parse(sentRequest.getHeader()
                    .replace(expectedPrefix, ""));
        }).doesNotThrowAnyException();
    }

    @Before
    public void setup() {
        client = new MyClient();
        sut = new Service(client);
    }

    @Getter
    public class MyClient extends Client {

        private Request sentRequest;

        @Override
        public void sendRequest(final Request input) {
            this.sentRequest = input;
        }
    }
}
