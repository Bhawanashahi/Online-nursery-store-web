package com.example.online_nursery_store.UserPojo;

import com.example.online_nursery_store.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingPojo {
    private Integer id;
    private  String fullname;
    private String price;
    private  String date;
    private  String mobile_no;
    private  String noofplant;
    private  String checkin;
    private  String address;

    public BookingPojo(Booking booking) {
        this.id=booking.getId();
        this.noofplant=booking.getNoofplant();
        this.mobile_no=booking.getMobileNo();
        this.fullname=booking.getFullname();
        this.checkin=booking.getCheckin();
        this.price=booking.getPrice();
        this.address=booking.getAddress();
        this.date=booking.getDate();

    }
}
