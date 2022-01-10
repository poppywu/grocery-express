package edu.gatech.cs6310;

public class Pilot  extends Employee {
    private final String licenseId;
    private Integer experience;
    private Integer droneId;

    public Pilot(String username, String firstName, String lastName, String phone,
                 String taxId, String licenseID, Integer experience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phone;
        this.username = username;
        this.taxId = taxId;
        this.licenseId = licenseID;
        this.experience = experience;
    }

    public void setDroneId(Integer droneId) { this.droneId = droneId; }

    public Integer getDroneId() { return this.droneId; }

    public String fullName() { return firstName + "_" + lastName; }

    public void incrementExperience() { experience++; }

    public void displayPilot() {
        System.out.println("name:" + fullName() + ",phone:" + phoneNum +
                ",taxID:" + taxId + ",licenseID:" + licenseId +",experience:"+ experience);
    }
}
