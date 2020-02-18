package nl.sourcelabs.workshop.testing.unit.mock;

public class AgeLookupService {

    public Integer lookupAge(final String firstName, final String lastName) {

        /*
        Some really complicated stuff, calling a dozen other web services that may know how old this person is.
        If the age can't be found then a special AI network is used to analyse the first and last name
        to come up with an educated guess as to how old the person is etc. etc.
         */


        return (int) (Math.random() * 100);
    }


}
