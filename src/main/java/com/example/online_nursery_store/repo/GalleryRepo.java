package com.example.online_nursery_store.repo;



import com.example.online_nursery_store.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface GalleryRepo extends JpaRepository<Gallery, Integer> {
    Optional<Gallery> findGalleryByTitle(String answer);
}
