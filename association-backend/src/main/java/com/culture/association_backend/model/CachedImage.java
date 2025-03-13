package com.culture.association_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "cached_images")
public class CachedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String originalUrl; // Original image URL

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private Instant cachedAt; // Timestamp of when the image was cached

    @Lob
    @Column(nullable = false)
    private byte[] imageData; // Optimized image data

}