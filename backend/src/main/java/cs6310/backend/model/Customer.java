package cs6310.backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Customer")
public class Customer extends User {
    private Integer rating;
    private String credit;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    public Customer(String username, String firstName, String lastName, String phone, Integer rating, String credit) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phone;
        this.username = username;
        this.rating = rating;
        this.credit = credit;
    }

    public Customer() {}

    public String getCredit() { return credit; }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String fullName() {
        return firstName + "_" + lastName;
    }

    public String getUsername() { return this.username; }

    public String displayCustomer() {
        return "name:" + fullName() + ",phone:" + phoneNum + ",rating:" + rating + ",credit:" + credit;
    }
}
