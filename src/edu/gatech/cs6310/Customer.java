package edu.gatech.cs6310;

public class Customer extends User {
    private Integer rating;
    private Integer credit;

    public Customer(String username, String firstName, String lastName, String phone, Integer rating, Integer credit) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phone;
        this.username = username;
        this.rating = rating;
        this.credit = credit;
    }

    public Integer getCredit() { return credit; }

    public void setCredit(Integer credit) { this.credit = credit; }

    public String fullName() { return firstName + "_" + lastName; }

    public void displayCustomer() {
        System.out.println("name:" + fullName() + ",phone:" + phoneNum + ",rating:" + rating + ",credit:" + credit);
    }
}
