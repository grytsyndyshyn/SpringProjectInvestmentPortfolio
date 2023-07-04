package com.example.springProgect.MyProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;


@Entity
@Table(name = "Securities")
public class Securities {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "not blanc")
    @Size(min = 2, max = 100, message = "2-100 symbols")
    @Column(name = "kind")
    private String kind;

    @NotEmpty(message = "noy blanc")
    @Size(min = 2, max = 100, message = "2-100 symbols")
    @Column(name = "issuer")
    private String issuer;

    @Min(value = 1, message = "number> 0")
    @Column(name = "number")
    private int number;


    @Column(name = "total_value")
    private int total_value;
    @ManyToOne
    @JoinColumn(name = "investor_id", referencedColumnName = "id")
    private Investor owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean expired;

    public Securities() {
    }

    public Securities(int id, String kind, String issuer, int number, int total_value) {
        this.id = id;
        this.kind = kind;
        this.issuer = issuer;
        this.number = number;
        this.total_value = total_value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotal_value() {
        return total_value;
    }

    public void setTotal_value(int total_value) {
        this.total_value = total_value;
    }

    public Investor getOwner() {
        return owner;
    }

    public void setOwner(Investor owner) {
        this.owner = owner;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
