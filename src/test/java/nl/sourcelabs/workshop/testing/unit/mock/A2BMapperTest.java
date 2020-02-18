package nl.sourcelabs.workshop.testing.unit.mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import nl.sourcelabs.workshop.testing.unit.mock.A;
import nl.sourcelabs.workshop.testing.unit.mock.A2BMapper;
import nl.sourcelabs.workshop.testing.unit.mock.AgeLookupService;
import nl.sourcelabs.workshop.testing.unit.mock.B;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
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