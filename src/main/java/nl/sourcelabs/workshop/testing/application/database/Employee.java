package nl.sourcelabs.workshop.testing.application.database;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee {

    private String firstName;

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String lastName;

    public Employee() {
    }

    @Builder
    public Employee(final UUID id, final String firstName, final String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
