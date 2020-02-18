package nl.sourcelabs.workshop.testing.unit.spy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Service {

    private final MathUtil mathUtil;

    public int add(final int a, final int b) {
        return mathUtil.add(a, b);
    }

}
