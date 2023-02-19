package com.example.online_nursery_store.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="buyproduct")
public class Booking {
    @Id
    @SequenceGenerator(name = "iv_book_seq_gen", sequenceName = "iv_book_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "iv_book_seq_gen", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "date")
    private String date;

    @Column(name = "Checkin")
    private String checkin;

    @Column(name="amount")
    private String total;

    @Column(name = "noofplant")
    private String noofplant;

    @Column(name = "price")
    private String price;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile_no")
    private String mobileNo;

}
