package nl.sourcelabs.workshop.testing.unit.spy;

public class Service {

    private final MathUtil mathUtil;

    public Service(final MathUtil mathUtil) {
        this.mathUtil = mathUtil;
    }

    public int add(final int a, final int b) {
        final int result = mathUtil.doVeryVeryComplicatedCalculation(a, b);

        validateResult(result);

        return result;
    }

    private void validateResult(final int result) {
        // Complicated validation logic
    }

}
