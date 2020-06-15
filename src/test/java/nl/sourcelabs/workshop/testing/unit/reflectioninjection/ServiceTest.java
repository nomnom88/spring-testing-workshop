package nl.sourcelabs.workshop.testing.unit.reflectioninjection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class ServiceTest {

    private static final String MATH_UTIL_FIELD_NAME = "mathUtil";

    private MathUtil mathUtil;

    private Service sut;

    @Before
    public void injectSpy() {
        sut = new Service();
        mathUtil = (MathUtil) ReflectionTestUtils.getField(sut, MATH_UTIL_FIELD_NAME);
        mathUtil = Mockito.spy(mathUtil);
        ReflectionTestUtils.setField(sut, MATH_UTIL_FIELD_NAME, mathUtil);
    }

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