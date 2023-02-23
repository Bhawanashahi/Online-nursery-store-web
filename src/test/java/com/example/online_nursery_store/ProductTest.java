package com.example.online_nursery_store;

import com.example.online_nursery_store.entity.Product;
import com.example.online_nursery_store.repo.ProductRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTest {


    @Autowired
    private ProductRepo productRepo;


    @Test
    @Order(1)
    public void saveContactTest() {


        Product product = Product.builder()
                .photo("bhawana@gmail.com")
                .name("bhawana")
                .quantity("2")
                .price("505")
                .build();

        productRepo.save(product);

        Assertions.assertThat(product.getId()).isGreaterThan(1);
    }

    @Test
    @Order(2)
    public void getProductTest() {

        Product product = Product.builder()
                .photo("bhawana@gmail.com")
                .name("bhawana")
                .quantity("2")
                .price("505")
                .build();


        productRepo.save(product);


        Product product1Created = productRepo.findById(product.getId()).get();
        Assertions.assertThat(product1Created.getId()).isEqualTo(product.getId());

    }
}
