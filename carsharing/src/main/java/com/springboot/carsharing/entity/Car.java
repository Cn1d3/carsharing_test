package com.springboot.carsharing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private int year;

    @Column(name = "registration_number")
    private String regNum;

    @Column(name = "class")
    private String clas;

    @Column(name = "cost_for_minute")
    private int costForMinute;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "image")
    private String image;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "car")
    private List<Image> imageList = new ArrayList<>();
    private int previewImageId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Rent rent;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "parking_id")
    private Parking parkingOfCar;

    private LocalDateTime addingDate;
    public void addImageToCar(Image image){
        image.setCar(this);
        imageList.add(image);
    }
    @PrePersist
    private void setTime(){
        addingDate = LocalDateTime.now();
    }
}
