package nl.sourcelabs.workshop.testing.unit.spy.example;

public class RealLifeClassSpy extends RealLifeClass {

    private boolean isCalled = false;
    private Integer result;

    @Override
    public int addTwoNumbers(final int a, final int b) {
        isCalled = true;
        result = super.addTwoNumbers(a,b);
        return result;
    }

    public boolean isCalled() {
        return isCalled;
    }

    public Integer getResult() {
        return result;
    }
}
