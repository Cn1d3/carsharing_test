package com.springboot.carsharing.repository;

import com.springboot.carsharing.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
