package com.springboot.carsharing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Table(name = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "orig_name")
    private String originalFileName;

    @Column(name = "size")
    private Long size;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "preview")
    private boolean isPreview;

    @Lob
    @Column(name = "bytes", columnDefinition = "LONGBLOB")
    private byte[] bytes;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public static Image toImageEntity(MultipartFile multipartFile) throws IOException {
        Image image = new Image();
        image.setName(multipartFile.getName());
        image.setOriginalFileName(multipartFile.getOriginalFilename());
        image.setContentType(multipartFile.getContentType());
        image.setSize(multipartFile.getSize());
        image.setBytes(multipartFile.getBytes());
        return image;
    }
}
