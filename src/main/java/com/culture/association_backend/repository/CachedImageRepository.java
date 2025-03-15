package com.culture.association_backend.repository;

import com.culture.association_backend.model.CachedImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CachedImageRepository extends JpaRepository<CachedImage, Long> {

    Optional<CachedImage> findByOriginalUrlAndWidthAndHeight(String originalUrl, int width, int height);
}