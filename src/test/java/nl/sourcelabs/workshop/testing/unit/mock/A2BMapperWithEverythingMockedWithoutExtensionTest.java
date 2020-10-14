package nl.sourcelabs.workshop.testing.unit.mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class A2BMapperWithEverythingMockedWithoutExtensionTest {

    private AgeLookupService ageLookupService;

    private A2BMapper sut;

    @BeforeEach
    public void doWhatTheMockitoExtensionDoesForUs() {
        ageLookupService = Mockito.mock(AgeLookupService.class);
        sut = new A2BMapper(ageLookupService);
    }

    @Test
    public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
        final String firstName = "FirstName";
        final String lastName = "LastName";
        final String expectedFullName = firstName + " " + lastName;
        final Integer expectedAge = 123;

        final A input = new A();
        input.setFirstName(firstName);
        input.setLastName(lastName);

        when(ageLookupService.lookupAge(firstName, lastName)).thenReturn(expectedAge);

        final B output = sut.map(input);

        assertThat(output.getFullName()).isEqualTo(expectedFullName);
        assertThat(output.getAge()).isEqualTo(expectedAge);

        verify(ageLookupService).lookupAge(firstName, lastName);


    }

}