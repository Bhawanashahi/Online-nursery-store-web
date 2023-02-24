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
    private String amount;
    private  String date;
    private  String mobile_no;
    private  String number_of_plants;
    private  String checkin;

    public BookingPojo(Booking booking) {
        this.id=booking.getId();
        this.number_of_plants=booking.getNumber_of_plants();
        this.mobile_no=booking.getMobileNo();
        this.fullname=booking.getFullname();
        this.checkin=booking.getCheckin();
        this.amount=booking.getTotal();
        this.date=booking.getDate();

    }
}
