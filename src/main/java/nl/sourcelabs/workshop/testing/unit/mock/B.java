package nl.sourcelabs.workshop.testing.unit.mock;

public class B {

    private String fullName;

    private Integer age;

    public void setAge(final Integer age) {
        this.age = age;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getAge() {
        return age;
    }
}
