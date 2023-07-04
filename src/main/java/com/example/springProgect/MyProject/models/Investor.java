package com.example.springProgect.MyProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Investor")
public class Investor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(min = 2, max = 100, message = "Некоректне ім'я")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Поле не може бути порожнім")
    @Size(min = 2, max = 100, message = "Некоректне ім'я")
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "owner")
    private List<Securities> securities;

    public Investor() {
    }

    public Investor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Securities> getSecurities() {
        return securities;
    }

    public void setSecurities(List<Securities> securities) {
        this.securities = securities;
    }
}
