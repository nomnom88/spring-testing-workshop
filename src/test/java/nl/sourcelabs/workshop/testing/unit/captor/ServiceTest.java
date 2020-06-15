package nl.sourcelabs.workshop.testing.unit.captor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;

import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {

    @Mock
    private Client client;

    @Captor
    private ArgumentCaptor<Request> requestArgumentCaptor;

    @InjectMocks
    private Service service;

    @Test
    public void given_message_thenExpect_clientCalledWithAppropriateRequest() {

        final String message = "My Message Text";
        final String expectedPrefix = Service.APP_ID + " ";

        service.sendRequest(message);

        // If not using annotation this is the programmatic equivalent
        //final ArgumentCaptor<Request> requestArgumentCaptor = ArgumentCaptor.forClass(Request.class);

        verify(client).sendRequest(requestArgumentCaptor.capture());

        final Request sentRequest = requestArgumentCaptor.getValue();

        assertThat(sentRequest).isNotNull();
        assertThat(sentRequest.getMessage()).isEqualTo(message);
        assertThat(sentRequest.getHeader()).startsWith(expectedPrefix);

        final String headerWithoutPrefix = sentRequest.getHeader()
                .replace(expectedPrefix, "");

        assertThatCode(() -> DateTimeFormatter.ISO_DATE.parse(headerWithoutPrefix)).doesNotThrowAnyException();

    }

}