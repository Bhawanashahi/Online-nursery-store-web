package com.example.online_nursery_store.Controller;


import com.example.online_nursery_store.Services.ProductService;
import com.example.online_nursery_store.Services.UserService;
import com.example.online_nursery_store.entity.Gallery;
import com.example.online_nursery_store.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class LandingController {

    private final UserService userService;
    private final ProductService productService;



    @GetMapping("/landing")
    public String getProductsPage(Model model, Authentication authentication) {
        if (authentication!=null){
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority.getAuthority().equals("Admin")) {
                    return "redirect:/admin/dashboard";
                }
            }
        }

        List<Product> products = productService.fetchAll();
        if (authentication!=null) {
            model.addAttribute("info", userService.findByEmail(authentication.getName()));
        }
        model.addAttribute("product", products.stream().map(product ->
                        Product.builder()
                                .id(product.getId())
                                .imageBase64(getImageBase64(product.getPhoto()))
                                .name(product.getName())
                                .quantity(product.getQuantity())
                                .price(product.getPrice())
                                .build()
                )

        );
        return ("landing");
    }
    public String getImageBase64(String fileName) {
        String filePath = System.getProperty("user.dir") + "/NurseryStore/";
        File file = new File(filePath + fileName);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return base64;
    }

    @GetMapping("/pay")
    public  String qrScan(){
        return "qrscanner";
    }


}





