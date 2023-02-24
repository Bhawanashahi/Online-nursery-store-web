package com.example.online_nursery_store.Services.Impl;

import com.example.online_nursery_store.Services.ProductService;
import com.example.online_nursery_store.UserPojo.ProductPojo;
import com.example.online_nursery_store.entity.Product;
import com.example.online_nursery_store.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    public final ProductRepo productRepo;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/NurseryStore";
    @Override
    public List<Product> fetchAll() {
        return this.productRepo.findAll();
    }

    @Override
    public String save(ProductPojo productPojo) throws IOException {
        Product product;
        if (productPojo.getId()!=null) {
            product = productRepo.findById(productPojo.getId()).orElseThrow(() -> new RuntimeException("Not found"));
        } else {
            product = new Product();
        }
        if(productPojo.getId()!=null){
            product.setId(productPojo.getId());
        }
        product.setName(productPojo.getName());
        product.setQuantity(productPojo.getQuantity());
        product.setPrice(productPojo.getPrice());
        if(productPojo.getPhoto()!=null){
            StringBuilder fileNames = new StringBuilder();
            System.out.println(UPLOAD_DIRECTORY);
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, productPojo.getPhoto().getOriginalFilename());
            fileNames.append(productPojo.getPhoto().getOriginalFilename());
            Files.write(fileNameAndPath, productPojo.getPhoto().getBytes());

            product.setPhoto(productPojo.getPhoto().getOriginalFilename());
        }

        productRepo.save(product);
        return "created";
    }
}
