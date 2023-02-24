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

    String submitMsg(ContactPojo contactPojo);

    void processPasswordResetRequest(String email);

    void sendEmail();

    String updateResetPassword(String email);



    List<Contact> fetchAllContact();

    List<Booking> fetchAll();

    void save(ContactPojo contactPojo);

    void deleteById(Integer id);




    abstract Booking fetchById(Integer id);

    String save(BookingPojo bookingPojo);


//    UserPojo findByPassword(String password);
}
