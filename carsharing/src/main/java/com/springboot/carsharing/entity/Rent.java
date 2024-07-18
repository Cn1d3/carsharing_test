package com.springboot.carsharing.entity;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_point")
    private String startPoint;

    @Column(name = "end_point")
    private String endPoint;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Transient
    private String totalTime;

    public String getTotalTime() {
        if (startTime == null || endTime == null) {
        this.totalTime = null;
        return null;
        }
        Duration duration = Duration.between(startTime, endTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds()%60;
        this.totalTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return totalTime;
    }

    @Column(name = "total_cost")
    private int totalCost;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ordered_car_id")
    private Car orderedCar;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public Rent(){}

    public Rent(String startPoint, String endPoint, LocalDateTime startTime, LocalDateTime endTime, int totalCost) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Car getOrderedCar() {
        return orderedCar;
    }

    public void setOrderedCar(Car orderedCar) {
        this.orderedCar = orderedCar;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalCost=" + totalCost +
                '}';
    }
}
