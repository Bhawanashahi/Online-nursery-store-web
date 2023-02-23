package com.example.online_nursery_store.Services.Impl;


import com.example.online_nursery_store.Services.GalleryService;
import com.example.online_nursery_store.UserPojo.GalleryPojo;
import com.example.online_nursery_store.entity.Gallery;
import com.example.online_nursery_store.repo.GalleryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
@RequiredArgsConstructor

public class GalleryServiceImpl implements GalleryService {
    private final GalleryRepo galleryRepo;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/NurseryStore/";


    @Override
    public GalleryPojo saveGallery(GalleryPojo galleryPojo) throws IOException {
        Gallery gallery = new Gallery();
        if (galleryPojo.getId() != null) {
            gallery.setId(galleryPojo.getId());
        }
        gallery.setTitle(galleryPojo.getTitle());



        if(galleryPojo.getImage()!=null){
//            StringBuilder fileNames = new StringBuilder();
//            System.out.println(UPLOAD_DIRECTORY);
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, galleryPojo.getImage().getOriginalFilename());
            Files.write(fileNameAndPath, galleryPojo.getImage().getBytes());

            gallery.setImage(galleryPojo.getImage().getOriginalFilename());
        }
        galleryRepo.save(gallery);
        return new GalleryPojo(gallery);
    }


    @Override
    public List<Gallery> fetchAll() {
        return galleryRepo.findAll();
    }

    @Override
    public Gallery fetchById(Integer id) {
        return galleryRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));

    }

    @Override
    public void deleteById(Integer id) {
        galleryRepo.deleteById(id);
    }
}