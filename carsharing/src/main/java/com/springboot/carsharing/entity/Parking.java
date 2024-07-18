package com.springboot.carsharing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "parking")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "address")
    private String address;

    @Column(name = "length")
    private float length;

    @Column(name = "width")
    private float width;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parkingOfCar")
    private List<Car> carsOnParking;

}
