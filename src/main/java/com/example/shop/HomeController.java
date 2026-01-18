package com.example.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final ShopRepository shopRepository;

    public HomeController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("shops", shopRepository.findAll());
        return "index";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam String name,
                                 @RequestParam String address,
                                 @RequestParam String mapUrl,
                                 Model model) {
        // データベースに保存
        Shop shop = new Shop();
        shop.setName(name);
        shop.setAddress(address);
        shop.setMapUrl(mapUrl);
        shopRepository.save(shop);

        model.addAttribute("name", name);
        model.addAttribute("address", address);
        model.addAttribute("mapUrl", mapUrl);
        return "registerResult";
    }
}
