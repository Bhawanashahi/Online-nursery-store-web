package com.example.online_nursery_store.Services;




import com.example.online_nursery_store.UserPojo.GalleryPojo;
import com.example.online_nursery_store.entity.Gallery;

import java.io.IOException;
import java.util.List;

public interface GalleryService {
    GalleryPojo saveGallery(GalleryPojo galleryPojo)throws IOException;



    List<Gallery> fetchAll();

    Gallery fetchById(Integer id);
    void deleteById(Integer id);
}
