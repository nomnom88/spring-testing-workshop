package nl.sourcelabs.workshop.testing.unit.spy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Disabled
public class ServiceTest {

    private MathUtil mathUtil;

    private Service sut;

    //BEFORE-EACH

    @Test
    public void given_aAndBAdded_thenExpect_correctAdditionResult() {
        final int a = 666;
        final int b = 69;
        final int expected = a + b;

        final int result = sut.add(a, b);

        assertThat(result).isEqualTo(expected);

        verify(mathUtil).doVeryVeryComplicatedCalculation(a, b);
    }

}