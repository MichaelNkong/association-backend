package com.culture.association_backend.service;

import com.culture.association_backend.model.CachedImage;
import com.culture.association_backend.model.KrakenResponse;
import com.culture.association_backend.repository.CachedImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.time.Instant;
import java.util.Optional;

@Service
public class ImageOptimizationService {

    private final CachedImageRepository cachedImageRepository;
    private final RestTemplate restTemplate;

    private final String KRAKEN_API_URL = "https://api.kraken.io/v1/url";
    private final String API_KEY = "your_kraken_api_key";
    private final String API_SECRET = "your_kraken_api_secret";

    public ImageOptimizationService(CachedImageRepository cachedImageRepository, RestTemplate restTemplate) {
        this.cachedImageRepository = cachedImageRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public ResponseEntity<ByteArrayResource> getOptimizedImage(String imageUrl, int width, int height) {

        // Look for the image in the database
        Optional<CachedImage> cachedImageOpt = cachedImageRepository.findByOriginalUrlAndWidthAndHeight(imageUrl, width, height);

        if (cachedImageOpt.isPresent()) {
            CachedImage cachedImage = cachedImageOpt.get();
            Instant expirationTime = cachedImage.getCachedAt().plusSeconds(86400); // 24 hours

            // Check if the cached image is still valid
            if (Instant.now().isBefore(expirationTime)) {
                return ResponseEntity.ok(new ByteArrayResource(cachedImage.getImageData()));
            }

            // Try to refresh the image
            byte[] newImageData = fetchOptimizedImageFromKraken(imageUrl, width, height);
            if (newImageData != null) {
                cachedImage.setImageData(newImageData);
                cachedImage.setCachedAt(Instant.now());
                cachedImageRepository.save(cachedImage);
                return ResponseEntity.ok(new ByteArrayResource(newImageData));
            }

            // If refresh fails, return stale image
            return ResponseEntity.ok(new ByteArrayResource(cachedImage.getImageData()));
        }

        // If image is not in cache, fetch from Kraken and save it
        byte[] optimizedImageData = fetchOptimizedImageFromKraken(imageUrl, width, height);
        if (optimizedImageData == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CachedImage newImage = new CachedImage();
        newImage.setOriginalUrl(imageUrl);
        newImage.setWidth(width);
        newImage.setHeight(height);
        newImage.setImageData(optimizedImageData);
        newImage.setCachedAt(Instant.now());

        cachedImageRepository.save(newImage);
        return ResponseEntity.ok(new ByteArrayResource(optimizedImageData));
    }

    // Fetch optimized image from Kraken API
    private byte[] fetchOptimizedImageFromKraken(String imageUrl, int width, int height) {
        String requestBody = String.format("""
            {
                "auth": {
                    "api_key": "%s",
                    "api_secret": "%s"
                },
                "url": "%s",
                "wait": true,
                "resize": {
                    "width": %d,
                    "height": %d,
                    "strategy": "exact"
                }
            }
        """, API_KEY, API_SECRET, imageUrl, width, height);

        ResponseEntity<KrakenResponse> response = restTemplate.postForEntity(KRAKEN_API_URL, requestBody, KrakenResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().isSuccess()) {
            return downloadImage(response.getBody().getKrakedUrl());
        }

        return null;
    }

    // Download the image from the provided Kraken optimized URL
    private byte[] downloadImage(String imageUrl) {
        return restTemplate.getForObject(imageUrl, byte[].class);
    }
}