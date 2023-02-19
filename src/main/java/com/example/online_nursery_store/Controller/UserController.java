package com.example.online_nursery_store.Controller;

import com.example.online_nursery_store.Services.ContactServices;
import com.example.online_nursery_store.Services.GalleryService;
import com.example.online_nursery_store.Services.UserService;
import com.example.online_nursery_store.UserPojo.BookingPojo;
import com.example.online_nursery_store.UserPojo.ContactPojo;
import com.example.online_nursery_store.UserPojo.UserPojo;

import com.example.online_nursery_store.entity.Gallery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final ContactServices contactServices;
    private final GalleryService galleryServices;




    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("user", new UserPojo());
        return "signup";
    }

    @PostMapping("/save")
    public String saveUser(@Valid UserPojo userpojo) {
        userService.save(userpojo);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String getUserProfile(Integer id, Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        model.addAttribute("update", new UserPojo());
        if (principal != null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        return "accountpage";
    }

    //    @PostMapping("/updateUser")
//    public String updateUser(@Valid UserPojo userpojo) {
//        userService.update(userpojo);
//        return "redirect:/user/profile";
//    }
//
    @GetMapping("/contact")
    public String getPage(Model model) {
        model.addAttribute("contact", new ContactPojo());
        return "contact_page";
    }

    @PostMapping("/send-message")
    public String submitMessage(@Valid ContactPojo contactPojo) {
        contactServices.submitMsg(contactPojo);
        return "redirect:contact";
    }

    @GetMapping("/about")
    public String getAboutUsPage(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        return "/aboutus";
    }


    @GetMapping("/forgotpassword")
    public String forgotpassword(Model model) {
        model.addAttribute("user", new UserPojo());
        return ("forgotpassword");
    }

    @PostMapping("/changepassword")
    public String changepassword(@RequestParam("email") String email, Model model, @Valid UserPojo userPojo) {
        userService.processPasswordResetRequest(userPojo.getEmail());
        model.addAttribute("message", "Your new password has been sent to your email Please " +
                "check your inbox");
        return "redirect:/user/login";
    }
    @GetMapping("/viewcontact")
    public String viewContactService( Model model){
        model.addAttribute("contact", new ContactPojo());
        return "contact_page";
    }
//    @GetMapping("/edit/{id}")
//    public String editUser(@PathVariable("id") Integer id, Model model) {
//        Booking user = userService.fetchById(id);
//        model.addAttribute("user", new UserPojo(user));
//        return "/user/create";
//    }


//    @GetMapping("/delete/{id}")
//    public String deleteuser(@PathVariable("id") Integer id) {
//        System.out.println("delete");
//        userService.deleteById(id);
//        return "redirect:/user/list";
//
//    }
@GetMapping("/viewGallery")
public String getViewGallery(Model model, Principal principal) {
    if (principal != null) {
        model.addAttribute("prof", userService.findByEmail(principal.getName()));
    }
    List<Gallery> gallerys = galleryServices.fetchAll();
    model.addAttribute("gallerylist", gallerys.stream().map(gallery ->
            Gallery.builder()
                    .id(gallery.getId())
                    .title(gallery.getTitle())
                    .imageBase64(getImageBase64(gallery.getImage()))
                    .build()

    ));
    return "gallery";
}



    public String getImageBase64(String fileName) {
        String filePath = System.getProperty("user.dir") + "/NurseryStore/";
        File file = new File(filePath + fileName);
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return Base64.getEncoder().encodeToString(bytes);
    }
    @GetMapping("/booking")
    public String BookHotel(Model model) {
        model.addAttribute("booking", new BookingPojo());
        return "booking";
    }


    @PostMapping("/savebook")
    public String saveBooking(@Valid BookingPojo bookingPojo) {
        userService.save(bookingPojo);
        return "landing";
    }
}




