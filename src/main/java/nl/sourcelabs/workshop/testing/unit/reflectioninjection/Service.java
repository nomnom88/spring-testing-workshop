package nl.sourcelabs.workshop.testing.unit.reflectioninjection;

public class Service {

    private final MathUtil mathUtil;

    public Service() {
        mathUtil = new MathUtil();
    }

    public int add(final int a, final int b) {
        return mathUtil.add(a, b);
    }

}
