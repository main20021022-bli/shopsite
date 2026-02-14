package com.example.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class HomeController {

    private final ShopDAO shopRepository;

    public HomeController(ShopDAO shopRepository) {
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
    public String registerSubmit(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String mapUrl,
            @RequestParam("image") MultipartFile image,   // ★ 追加
            Model model) throws IOException {

        String imagePath = null;

        // ★ 画像が送られてきた場合のみ保存
        if (!image.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/uploads/" + fileName);

            Files.createDirectories(uploadPath.getParent());
            Files.write(uploadPath, image.getBytes());

            imagePath = "/uploads/" + fileName;
        }

        // ★ Shop に画像パスをセット
        ShopEntity shop = new ShopEntity();
        shop.setName(name);
        shop.setAddress(address);
        shop.setMapUrl(mapUrl);
        shop.setImagePath(imagePath);  // ← 追加

        shopRepository.save(shop);

        model.addAttribute("name", name);
        model.addAttribute("address", address);
        model.addAttribute("mapUrl", mapUrl);
        model.addAttribute("imagePath", imagePath);

        return "registerResult";
    }
}