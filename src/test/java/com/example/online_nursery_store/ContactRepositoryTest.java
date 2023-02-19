//package com.example.online_nursery_store;
//
//
//
//import com.example.online_nursery_store.entity.Contact;
//import com.example.online_nursery_store.repo.ContactRepo;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//
//
//@DataJpaTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class ContactRepositoryTest {
//
//        @Autowired
//        private ContactRepo contactRepo;
//
//
//        @Test
//        @Order(1)
//        public void saveContactTest() {
//
//
//            Contact contact = Contact.builder()
//                    .email("shahivawana61@gmail.com")
//                    .message("234")
//                    .build();
//
//            contactRepo.save(contact);
//
//            Assertions.assertThat(contact.getId()).isGreaterThan(0);
//        }
//
//        @Test
//        @Order(2)
//        public void getContactTest() {
//
//            Contact contact = Contact.builder()
//                    .email("prabisha@gmail.com")
//                    .message("prabisha")
//                    .build();
//
//            contactRepo.save(contact);
//
//
//            Contact contactCreated = contactRepo.findById(contact.getId()).get();
//            Assertions.assertThat(contactCreated.getId()).isEqualTo(contact.getId());
//
//        }
//
//
//
//    }
//
//
