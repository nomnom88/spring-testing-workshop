package nl.sourcelabs.workshop.testing.database;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String firstName;

    private String lastName;

}
