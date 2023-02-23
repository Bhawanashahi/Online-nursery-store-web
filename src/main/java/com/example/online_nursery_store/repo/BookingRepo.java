package com.example.online_nursery_store.repo;


import com.example.online_nursery_store.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {
    Optional<Booking> findBookingByFullname(String fullname);

}
