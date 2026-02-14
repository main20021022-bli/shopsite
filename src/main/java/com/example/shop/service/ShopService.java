package com.example.shop.service;

import com.example.shop.dto.ShopDTO;
import com.example.shop.entity.ShopEntity;
import com.example.shop.repository.ShopDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ShopService {

    @Autowired
    private ShopDAO shopDAO;

    // ★ お店を作成して DB に保存
    public ShopEntity createShop(String name, String address, String mapUrl, MultipartFile image) throws IOException {

        String imagePath = null;

        // 画像がある場合のみ保存
        if (!image.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/uploads/" + fileName);

            Files.createDirectories(uploadPath.getParent());
            Files.write(uploadPath, image.getBytes());

            imagePath = "/uploads/" + fileName;
        }

        // Entity 作成
        ShopEntity shop = new ShopEntity();
        shop.setName(name);
        shop.setAddress(address);
        shop.setMapUrl(mapUrl);
        shop.setImagePath(imagePath);

        // ★ DB に保存して返す
        return shopDAO.save(shop);
    }

    // ★ Entity → DTO 変換
    public ShopDTO toDTO(ShopEntity entity) {
        ShopDTO dto = new ShopDTO();
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setMapUrl(entity.getMapUrl()); // ← 重要！
        dto.setImagePath(entity.getImagePath());
        dto.setHasImage(entity.getImagePath() != null && !entity.getImagePath().isEmpty());
        return dto;
    }

    // ★ 全件取得
    public List<ShopEntity> findAll() {
        return shopDAO.findAll();
    }
}