package com.springboot.carsharing.controller;


import com.springboot.carsharing.entity.Image;
import com.springboot.carsharing.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
public class ImageController {
    @Autowired
    private final ImageRepository imageRepository;

    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable int id) {
        Image image = imageRepository.findById(id).orElse(null);
        assert image != null;
        return ResponseEntity.ok()
                .header("file", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }
}
