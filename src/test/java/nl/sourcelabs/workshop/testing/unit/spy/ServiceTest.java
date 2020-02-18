package nl.sourcelabs.workshop.testing.unit.spy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {

    @Spy
    private MathUtil mathUtil = new MathUtil();

    @InjectMocks
    private Service sut;

    @Test
    public void given_aAndBAdded_thenExpect_correctAdditionResult() {
        final int a = 666;
        final int b = 69;
        final int expected = a + b;

        final int result = sut.add(a, b);

        assertThat(result).isEqualTo(expected);

        verify(mathUtil).add(a, b);
    }


}