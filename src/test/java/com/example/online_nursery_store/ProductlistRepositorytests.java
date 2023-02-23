package com.example.online_nursery_store;

import com.example.online_nursery_store.entity.Booking;
import com.example.online_nursery_store.entity.Product;
import com.example.online_nursery_store.repo.ProductRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;

import java.util.Optional;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ProductlistRepositorytests {
    @Autowired
    private ProductRepo productRepo;
    @Test
    @Order(4)
    public void updateProductTest(){

        Product product = Product.builder()
                .name("image")
                .build();

        productRepo.save(product);

        Product  gallery1  = productRepo.findById(product.getId()).get();

        gallery1.setName("b@g.com");

        Product  product1Updated  = productRepo.save(product);

        Assertions.assertThat(product1Updated.getName()).isEqualTo("b@g.com");

    }

    @Test
    @Order(5)
    public void deleteProductTest(){

        Product product = Product.builder()
                .name("image")
                .build();

        productRepo.save(product);

//        @Query(value = "SELECT * from")

        Product  product1 = productRepo.findById(product.getId()).get();
        productRepo.delete(product1);

        Product  product2 = null;
        Optional<Product> optionalProduct = productRepo.findProductByName("a@g.com");
        if(optionalProduct.isPresent()){
            product2 = optionalProduct.get();
        }

        Assertions.assertThat(product2).isNull();
//        Assertions.assertThat(User1.getId()).isNull();
    }
}
