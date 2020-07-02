package nl.sourcelabs.workshop.testing.unit.mock.example;

public class RealLifeClassMock extends RealLifeClass {

    private boolean isCalled = false;
    private Integer paramA;
    private Integer paramB;
    private Integer returnValue;

    @Override
    public int addTwoNumbers(final int a, final int b) {
        isCalled = true;
        paramA = a;
        paramB = b;
        return returnValue;
    }

    public boolean isCalled() {
        return isCalled;
    }

    public void setReturnValue(final Integer returnValue) {
        this.returnValue = returnValue;
    }

    public Integer getParamA() {
        return paramA;
    }

    public Integer getParamB() {
        return paramB;
    }
}
