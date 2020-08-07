package nl.sourcelabs.workshop.testing.application.database;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class Employee {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String firstName;

    private String lastName;

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
}