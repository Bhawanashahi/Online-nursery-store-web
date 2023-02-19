 package com.example.online_nursery_store.Controller;

        import com.example.online_nursery_store.Services.ContactServices;
        import com.example.online_nursery_store.Services.GalleryService;
        import com.example.online_nursery_store.Services.ProductService;
        import com.example.online_nursery_store.Services.UserService;
        import com.example.online_nursery_store.UserPojo.BookingPojo;
        import com.example.online_nursery_store.UserPojo.ContactPojo;
        import com.example.online_nursery_store.UserPojo.GalleryPojo;
        import com.example.online_nursery_store.UserPojo.ProductPojo;
        import com.example.online_nursery_store.entity.Booking;
        import com.example.online_nursery_store.entity.Contact;
        import com.example.online_nursery_store.entity.Gallery;
        import com.example.online_nursery_store.entity.Product;
        import jakarta.validation.Valid;
        import lombok.RequiredArgsConstructor;
        import org.springframework.security.authentication.AnonymousAuthenticationToken;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.validation.BindingResult;
        import org.springframework.validation.FieldError;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;

        import java.io.File;
        import java.io.IOException;
        import java.nio.file.Files;
        import java.security.Principal;
        import java.util.Base64;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

 @Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;


     private final GalleryService galleryServices;
    private final UserService userService;
     private final ContactServices contactServices;
    @GetMapping("/dashboard")
    public String getAdminPage() {
        return "admin_dashboard";
    }

     @GetMapping("/addGallery")
     public String createGallery(Model model) {
         model.addAttribute("gallery", new GalleryPojo());
         return "adminGallery";
     }
     @PostMapping("/save")
     public String saveGallery(@Valid GalleryPojo galleryPojo,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

         Map<String, String> requestError = validateRequest(bindingResult);
         if (requestError != null) {
             redirectAttributes.addFlashAttribute("requestError", requestError);
             return "redirect:admin/gallerytable";
         }

         galleryServices.saveGallery(galleryPojo);
         redirectAttributes.addFlashAttribute("successMsg", "Gallery saved successfully");

         return "redirect:/gallery/list";
     }
     @GetMapping("/gallerylist")
     public String displayGallery( Model model) {
         List<Gallery> galleries = galleryServices.fetchAll();
         model.addAttribute("gallerylist", galleries.stream().map(gallery ->
                 Gallery.builder()
                         .id(gallery.getId())
                         .title(gallery.getTitle())
                         .imageBase64(getImageBase64(gallery.getImage()))
                         .build()

         ));
         return "gallerytable";
     }
     @GetMapping("/editGallery/{id}")
     public String editGallery(@PathVariable("id") Integer id, Model model) {
         Gallery gallery = galleryServices.fetchById(id);
         model.addAttribute("gallerylist", new GalleryPojo(gallery));
         return "adminGallery";
     }


     @GetMapping("/deleteGallery/{id}")
     public String deleteGallery(@PathVariable("id") Integer id) {
         galleryServices.deleteById(id);
         return "redirect:/gallery/list";
     }

     public Map<String, String> validateRequest(BindingResult bindingResult) {
         if (!bindingResult.hasErrors()) {
             return null;
         }
         Map<String, String> errors = new HashMap<>();
         bindingResult.getAllErrors().forEach(error -> {
             String fieldName = ((FieldError) error).getField();
             String message = error.getDefaultMessage();
             errors.put(fieldName, message);
         });
         return errors;

     }

     public String getImageBase64(String fileName) {
         String filePath = System.getProperty("user.dir") + "/NurseryStore/";
         File file = new File(filePath + fileName);
         byte[] bytes ;
         try {
             bytes = Files.readAllBytes(file.toPath());
         } catch (IOException e) {
             e.printStackTrace();
             return null;
         }
         return Base64.getEncoder().encodeToString(bytes);
     }

    @GetMapping("/add-product")
    public String getAddProductPage(Model model) {
        model.addAttribute("product", new ProductPojo());
        return "add_products";
    }
    @PostMapping("/save/product")
    public String saveProduct(@Valid ProductPojo productPojo) throws IOException {
        productService.save(productPojo);
        return "redirect:/landing";
    }
     @GetMapping("/order-list")
     public String getOrderListPage(Model model, Principal principal) {
         if (principal!=null) {
             model.addAttribute("info", userService.findByEmail(principal.getName()));
         }
         return "order_list";
     }
     @GetMapping("/product-list")
     public String getProductList(Model model, Principal principal) {
         if (principal!=null) {
             model.addAttribute("info", userService.findByEmail(principal.getName()));
         }
         List<Product> products = productService.fetchAll();
         model.addAttribute("product", products.stream().map(product ->
                 Product.builder()
                         .id(product.getId())
                         .imageBase64(getImageBase64(product.getPhoto()))
                         .name(product.getName())
                         .quantity(product.getQuantity())
                         .price(product.getPrice())
                         .build()
         ));
         return "productlist";
     }
     @GetMapping("/editProduct/{id}")
     public String editProducts(@PathVariable("id") Integer id, Model model, Principal principal) {
         if (principal!=null) {
             model.addAttribute("info", userService.findByEmail(principal.getName()));
         }
         Product products = productService.fetchById(id);
         model.addAttribute("product", new ProductPojo(products));
         return "add_products";
     }
     @GetMapping("/deleteProduct/{id}")
     public String deleteProducts(@PathVariable("id") Integer id, Model model, Principal principal) {
         if (principal!=null) {
             model.addAttribute("info", userService.findByEmail(principal.getName()));
         }
         productService.deleteById(id);
         return "redirect:/admin/product-list";
     }


//    @GetMapping("/contactlist")
//    public String getContactList(Model model){
//        List<Contact> contacts = userService.fetchAll();
//        model.addAttribute("contact", contacts);
//        return "AdminContact";
//    }

    @PostMapping("/savecontact")
    public String saveContact(@Valid ContactPojo contactPojo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        contactServices.save(contactPojo);
        return "redirect:/landing";
    }

    @GetMapping("/deletecontact/{id}")
    public String deleteContact(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return "redirect:/admin/contactlist";
    }


    @GetMapping("/settings")
    public String getAdminSettingsPage() {
        return "admin_settings";
    }





    @GetMapping("/list")
    public String getUserList(Model model) {
        List<Booking> bookings = userService.fetchAll();
        model.addAttribute("bookinglist", bookings);
        return "AllBooking";
    }


    @GetMapping("/alllist")
    public String getAllBooking(Model model) {
        List<Booking> bookings = userService.fetchAll();
        model.addAttribute("bookinglist", bookings);
        return "AllBooking";
    }





    @GetMapping("/newbooking")
    public String BookHotel(Model model) {
        model.addAttribute("newBooking", new BookingPojo());
        return "newbookings";
    }



    @PostMapping("/savenewbook")
    public String saveBooking(@Valid BookingPojo bookingPojo) {
        userService.save(bookingPojo);
        return "redirect:/admin/list";
    }

     @GetMapping("/edit/{id}")
     public String editUser(@PathVariable("id") Integer id, Model model) {
         Booking booking = userService.fetchById(id);
         model.addAttribute("newBooking", new BookingPojo(booking));
         return "newbookings";
     }


     @GetMapping("/delete/{id}")
     public String deleteUser(@PathVariable("id") Integer id) {
         userService.deleteById(id);
         return "redirect:/admin/list";
     }
     @GetMapping("/contactlist")
     public String getContactList(Model model) {
         List<Contact> contact = contactServices.fetchAll();
         model.addAttribute("contact", contact);
         return "contacttable";
     }


     @GetMapping("/allcontactlist")
     public String getAllContact(Model model) {
         List<Contact> contact = contactServices.fetchAll();
         model.addAttribute("contact", contact);
         return "contacttable";
     }



//     @GetMapping("/delete/{id}")
//     public String deleteContactUser(@PathVariable("id") Integer id) {
//         contactServices.deleteById(id);
//         return "redirect:/admin/allcontactlist";
//     }

}

