package com.example.online_nursery_store.UserPojo;


import com.example.online_nursery_store.entity.Product;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPojo {
    private Integer id;
    private MultipartFile photo;
    @NotEmpty(message = "Product Name can't be empty")
    private String name;
    @NotEmpty(message = "Product Quantity can't be empty")
    private String quantity;
    @NotEmpty(message = "Product Price can't be empty")
    private String price;

    public ProductPojo(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
    }
}
