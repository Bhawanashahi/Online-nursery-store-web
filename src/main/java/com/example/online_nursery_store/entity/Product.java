package com.example.online_nursery_store.entity;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @SequenceGenerator(name = "GG_products_seq_gen", sequenceName = "GG_products_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "GG_products_seq_gen", strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String photo;
    @Column(name = "productName", nullable = false)
    private String name;
    @Column(name = "productQuantity", nullable = false)
    private String quantity;
    @Column(name = "productPrice", nullable = false)
    private String price;
    @Transient
    private String imageBase64;
}
