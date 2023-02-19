package com.example.online_nursery_store.Services;

import com.example.online_nursery_store.UserPojo.ContactPojo;
import com.example.online_nursery_store.entity.Contact;

import java.util.List;

public interface ContactServices {
    List<Contact> fetchAllContact();

    void save(ContactPojo contactPojo);

    String submitMsg(ContactPojo contactPojo);

    void deleteById(Integer id);

    Contact fetchById(Integer id);

    List<Contact> fetchAll();
}
