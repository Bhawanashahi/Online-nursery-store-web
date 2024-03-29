package com.example.online_nursery_store.repo;


import com.example.online_nursery_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {


    Optional<Product> findProductByName(String name);
}
