package com.example.online_nursery_store.Services.Impl;


import com.example.online_nursery_store.Services.UserService;
import com.example.online_nursery_store.UserPojo.BookingPojo;
import com.example.online_nursery_store.UserPojo.ContactPojo;
import com.example.online_nursery_store.UserPojo.UserPojo;
import com.example.online_nursery_store.config.PasswordEncoderUtil;
import com.example.online_nursery_store.entity.Booking;
import com.example.online_nursery_store.entity.Contact;
import com.example.online_nursery_store.entity.User;
import com.example.online_nursery_store.exception.AppException;
import com.example.online_nursery_store.repo.BookingRepo;
import com.example.online_nursery_store.repo.ContactRepo;
import com.example.online_nursery_store.repo.UserRepo;
import freemarker.template.Template;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


//    private final EmailCredRepo emailCredRepo;

    public final UserRepo userRepo;

    private final ContactRepo contactRepo;
    private final BookingRepo bookingRepo;
    private final JavaMailSender mailSender;
    private final ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    @Qualifier("emailConfigBean")
    private Configuration emailConfig;



    @Override
    public String save(UserPojo userPojo) {
        User user = new User();
        user.setEmail(userPojo.getEmail());
        user.setMobileno(userPojo.getMobile_no());
        user.setPassword(PasswordEncoderUtil.getInstance().encode(userPojo.getPassword()));
        userRepo.save(user);
        return "created";
    }
    private void sendPassword(String email, String password ){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your new password is:");
        message.setText(password);
        mailSender.send(message);
    }
    @Override
    public void processPasswordResetRequest(String email){
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            String password = generatePassword();
            sendPassword(email, password);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodePassword = passwordEncoder.encode(password);
            user.setPassword(encodePassword);
            userRepo.save(user);
        }
    }
    @Override
    public void sendEmail() {
        try {
            Map<String, String> model = new HashMap<>();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,  StandardCharsets.UTF_8.name());

            Template template = emailConfig.getTemplate("emailTemp.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            mimeMessageHelper.setTo("sendfrom@yopmail.com");
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setSubject("Registration");
            mimeMessageHelper.setFrom("sendTo@yopmail.com");

            taskExecutor.execute(new Thread() {
                public void run() {
                    mailSender.send(message);
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public String updateResetPassword(String email) {
        User user = (User) userRepo.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Invalid User email"));
        String updated_password = generatePassword();
        try {
            userRepo.updatePassword(updated_password, email);
            return "CHANGED";
        } catch (Exception e){
            e.printStackTrace();
        }
        return "ds";
    }

    public String generatePassword() {
        int length = 8;
        String password = "";
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            int randomChar = (int)(r.nextInt(94) + 33);
            char c = (char)randomChar;
            password += c;
        }
        return password;

    }

    @Override
    public UserPojo findByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AppException("Invalid User email", HttpStatus.BAD_REQUEST));
        return new UserPojo(user);
    }

    @Override
    public String submitMsg(ContactPojo contactPojo) {
        Contact contact = new Contact();
        contact.setFullname(contactPojo.getFullname());
        contact.setEmail(contactPojo.getEmail());
        contact.setSubject(contactPojo.getSubject());
        contact.setMessage(contactPojo.getMessage());
        contactRepo.save(contact);
        return "sent";

    }


    @Override
    public List<Contact> fetchAllContact() {
        return null;
    }


    @Override
    public void save(ContactPojo contactPojo) {
             Contact contact=new Contact();
            if(contactPojo.getId()!=null){
                contact.setId(contactPojo.getId());
            }
            contact.setFullname(contactPojo.getFullname());
            contact.setEmail(contactPojo.getEmail());
            contact.setSubject(contactPojo.getSubject());
            contact.setMessage(contactPojo.getMessage());
            contactRepo.save(contact);
        }
    @Override
    public String save(BookingPojo bookingPojo) {
        Booking booking=new Booking();
        if(bookingPojo.getId()!=null){
            booking.setId(bookingPojo.getId());
        }
        booking.setFullname(bookingPojo.getFullname());
        booking.setNumber_of_plants(bookingPojo.getNumber_of_plants());
        booking.setMobileNo(bookingPojo.getMobile_no());
        booking.setCheckin(bookingPojo.getCheckin());
        booking.setDate(bookingPojo.getDate());
        booking.setTotal(bookingPojo.getAmount());
        bookingRepo.save(booking);
        return null;
    }

//    @Override
//    public Booking fetchById(Integer id) {
//        return bookingRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
//    }


//    @Override
//    public Booking deleteById(Integer id) {
//        return bookingRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
//    }

    public List<Booking> fetchAll(){
        return this.bookingRepo.findAll();
    }


    @Override
    public void deleteById(Integer id) {
        bookingRepo.deleteById(id);

    }










//    @Override
//    public void deleteById(Integer id) {
//
//    }

    @Override
    public Booking fetchById(Integer id) {
        return null;
    }
}