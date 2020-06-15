package nl.sourcelabs.workshop.testing.unit.mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.sourcelabs.workshop.testing.unit.mock.A;
import nl.sourcelabs.workshop.testing.unit.mock.A2BMapper;
import nl.sourcelabs.workshop.testing.unit.mock.AgeLookupService;
import nl.sourcelabs.workshop.testing.unit.mock.B;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class A2BMapperWithEverythingMockedWithoutRunnerTest {

    private A input;

    private AgeLookupService ageLookupService;

    private A2BMapper sut;

    @Before
    public void doWhatTheMockitoRunnerDoesForUs() {
        input = Mockito.mock(A.class);
        ageLookupService = mock(AgeLookupService.class);
        sut = new A2BMapper(ageLookupService);
    }

    @Test
    public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
        final String firstName = "FirstName";
        final String lastName = "LastName";
        final String expectedFullName = firstName + " " + lastName;
        final Integer expectedAge = 123;

        when(input.getFirstName()).thenReturn(firstName);
        when(input.getLastName()).thenReturn(lastName);
        when(ageLookupService.lookupAge(firstName, lastName)).thenReturn(expectedAge);

        final B output = sut.map(input);

        assertThat(output).isNotNull();
        assertThat(output.getFullName()).isEqualTo(expectedFullName);
        assertThat(output.getAge()).isEqualTo(expectedAge);
    }

}