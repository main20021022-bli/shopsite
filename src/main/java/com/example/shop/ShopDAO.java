package com.example.shop;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopDAO extends JpaRepository<ShopEntity, Long> {
    // 基本CRUDは JpaRepository が自動で提供
}
