package com.springboot.carsharing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "driver")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {//implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "password", length = 255)
    private String password;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "driver")
    private Image avatar;
    public void addImageToDriver(Image image){
        image.setDriver(this);
        avatar=image;
    }

    @Column(name = "active")
    private boolean isActive;

    @Column(name = "role")
    private String role;

    private LocalDateTime creationDate;
    @PrePersist
    private void setTime(){
        creationDate = LocalDateTime.now();
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "driver")
    private List<Rent> rents = new ArrayList<>();

    public Driver(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}



