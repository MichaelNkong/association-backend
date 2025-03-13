package com.culture.association_backend.controller;

import com.culture.association_backend.service.ImageOptimizationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageOptimizationService imageOptimizationService;

    public ImageController(ImageOptimizationService imageOptimizationService) {
        this.imageOptimizationService = imageOptimizationService;
    }

    @GetMapping("/optimize")
    public ResponseEntity<ByteArrayResource> optimizeImage(
            @RequestParam String imageUrl,
            @RequestParam int width,
            @RequestParam int height) {
        return imageOptimizationService.getOptimizedImage(imageUrl, width, height);
    }
}