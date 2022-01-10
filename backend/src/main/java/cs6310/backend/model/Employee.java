package cs6310.backend.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Employee extends User {
    protected String taxId;
}
