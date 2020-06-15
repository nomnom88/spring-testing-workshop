package nl.sourcelabs.workshop.testing.unit.mock;

public class A2BMapper {

    private final AgeLookupService ageLookupService;

    public A2BMapper(final AgeLookupService ageLookupService) {
        this.ageLookupService = ageLookupService;
    }

    public B map(final A a) {
        final B b = new B();

        final String firstName = a.getFirstName();
        final String lastName = a.getLastName();

        final String fullName = getFullName(firstName, lastName);
        b.setFullName(fullName);

        final Integer age = ageLookupService.lookupAge(firstName, lastName);

        b.setAge(age);

        return b;
    }

    private String getFullName(final String firstName, final String lastName) {
        return firstName + " " + lastName;
    }

}
