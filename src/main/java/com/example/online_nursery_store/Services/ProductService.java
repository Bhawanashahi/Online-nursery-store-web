package com.example.online_nursery_store.Services;


import com.example.online_nursery_store.UserPojo.ProductPojo;
import com.example.online_nursery_store.entity.Product;

import java.io.IOException;
import java.util.List;


public interface ProductService {
    List<Product> fetchAll();

    String save(ProductPojo productPojo) throws IOException;
}
