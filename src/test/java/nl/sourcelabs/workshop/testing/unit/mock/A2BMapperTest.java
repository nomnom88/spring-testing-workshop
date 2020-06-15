package nl.sourcelabs.workshop.testing.unit.mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class A2BMapperTest {

    @Mock
    private AgeLookupService ageLookupService;

    @InjectMocks
    private A2BMapper sut;

    @Test
    public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
        final String firstName = "FirstName";
        final String lastName = "LastName";
        final String expectedFullName = firstName + " " + lastName;
        final Integer expectedAge = 123;

        final A input = new A();
        input.setLastName(lastName);
        input.setFirstName(firstName);

        when(ageLookupService.lookupAge(firstName, lastName)).thenReturn(expectedAge);

        final B output = sut.map(input);

        assertThat(output.getFullName()).isEqualTo(expectedFullName);
    }

}