package com.example.online_nursery_store.UserPojo;


import com.example.online_nursery_store.entity.Gallery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GalleryPojo {
    private Integer id;
     private  String title;
     private MultipartFile image;

    public GalleryPojo(Gallery gallery) {
        this.id=gallery.getId();
        this.title=gallery.getTitle();

    }
}
