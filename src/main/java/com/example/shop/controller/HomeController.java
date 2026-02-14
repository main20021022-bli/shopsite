package com.example.shop.controller;

import com.example.shop.dto.ShopDTO;
import com.example.shop.entity.ShopEntity;
import com.example.shop.service.ShopService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ShopService shopService;

    // 一覧表示（DTO に統一）
    @GetMapping("/")
    public String index(Model model) {
        List<ShopEntity> entities = shopService.findAll();
        List<ShopDTO> dtoList = entities.stream()
                .map(shopService::toDTO)
                .toList();

        model.addAttribute("shops", dtoList);
        return "index";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    // 登録処理（Service に丸投げ）
    @PostMapping("/register")
    public String registerSubmit(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String mapUrl,
            @RequestParam("image") MultipartFile image,
            Model model) throws IOException {

        // Service が画像保存 + DB 保存を全部やる
        ShopEntity saved = shopService.createShop(name, address, mapUrl, image);

        // 保存された値をそのまま画面へ
        model.addAttribute("name", saved.getName());
        model.addAttribute("address", saved.getAddress());
        model.addAttribute("mapUrl", saved.getMapUrl());
        model.addAttribute("imagePath", saved.getImagePath());

        return "registerResult";
    }

    // /list も / と同じ動作に統一
    @GetMapping("/list")
    public String list(Model model) {
        List<ShopEntity> entities = shopService.findAll();
        List<ShopDTO> dtoList = entities.stream()
                .map(shopService::toDTO)
                .toList();

        model.addAttribute("shops", dtoList);
        return "index";
    }
}