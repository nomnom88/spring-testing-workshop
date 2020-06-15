package nl.sourcelabs.workshop.testing.unit.spy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Service {

    private final MathUtil mathUtil;

    public int add(final int a, final int b) {
        final int result = mathUtil.doVeryVeryComplicatedCalculation(a, b);

        validateResult(result);

        return result;
    }

    private void validateResult(final int result) {
        // Complicated validation logic
    }

}
