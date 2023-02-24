package com.example.online_nursery_store.UserPojo;

import com.example.online_nursery_store.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPojo {

    private Integer id;


    private String email;
    private  String mobile_no;
    private  String password;


    public UserPojo(User user) {
        this.id=user.getId();
        this.email=user.getEmail();
        this.mobile_no=user.getMobileno();
        this.password=user.getPassword();

    }
}
