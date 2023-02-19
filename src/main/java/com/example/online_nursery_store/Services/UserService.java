package com.example.online_nursery_store.Services;


        import com.example.online_nursery_store.UserPojo.BookingPojo;
        import com.example.online_nursery_store.UserPojo.ContactPojo;
        import com.example.online_nursery_store.UserPojo.UserPojo;
        import com.example.online_nursery_store.entity.Booking;
        import com.example.online_nursery_store.entity.Contact;

        import java.util.List;

public interface UserService {


    String save(UserPojo userPojo);

    UserPojo findByEmail(String email);



    void processPasswordResetRequest(String email);

    void sendEmail();

    String updateResetPassword(String email);




    List<Booking> fetchAll();




    void deleteById(Integer id);




    String save(BookingPojo bookingPojo);

    abstract Booking fetchById(Integer id);




//    UserPojo findByPassword(String password);
}
