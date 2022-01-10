package cs6310.backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "pilot")
@Entity
public class Pilot extends Employee {
    private String licenseId;
    private Integer experience;

    @JsonIgnore
    @OneToOne(mappedBy = "pilot")
    private Drone drone;

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

    public Pilot() {}

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public String fullName() {
        return firstName + "_" + lastName;
    }

    public void incrementExperience() {
        experience++;
    }

    public String displayPilot() {
        return "name:" + fullName() + ",phone:" + phoneNum +
                ",taxID:" + taxId + ",licenseID:" + licenseId + ",experience:" + experience;
    }

    public String getLicenseId() { return licenseId; }

    public String getTaxId() { return taxId; }

    public void setTaxId(String taxId) { this.taxId = taxId; }

    public String getUsername() { return this.username; }

}
