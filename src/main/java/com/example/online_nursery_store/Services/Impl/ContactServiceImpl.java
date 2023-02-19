package com.example.online_nursery_store.Services.Impl;

import com.example.online_nursery_store.Services.ContactServices;
import com.example.online_nursery_store.UserPojo.ContactPojo;
import com.example.online_nursery_store.entity.Booking;
import com.example.online_nursery_store.entity.Contact;
import com.example.online_nursery_store.repo.ContactRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor

public class ContactServiceImpl implements ContactServices {

    private final ContactRepo contactRepo;


    @Override
    public List<Contact> fetchAllContact() {
        return null;
    }


    @Override
    public void save(ContactPojo contactPojo) {
        Contact contact = new Contact();
        if (contactPojo.getId() != null) {
            contact.setId(contactPojo.getId());
        }
        contact.setFullname(contactPojo.getFullname());
        contact.setEmail(contactPojo.getEmail());
        contact.setSubject(contactPojo.getSubject());
        contact.setMessage(contactPojo.getMessage());
        contactRepo.save(contact);
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
    public List<Contact> fetchAll() {
        return this.contactRepo.findAll();
    }


    @Override
    public void deleteById(Integer id) {
        contactRepo.deleteById(id);

    }


//    @Override
//    public void deleteById(Integer id) {
//
//    }

    @Override
    public Contact fetchById(Integer id) {
        return null;
    }


}



