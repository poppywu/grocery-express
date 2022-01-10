package cs6310.backend.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class User {
    @Id
    protected String username;
    protected String firstName;
    protected String lastName;
    protected String phoneNum;
}
