package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.entity.ShopEntity;

public interface ShopDAO extends JpaRepository<ShopEntity, Long> {
    // 基本CRUDは JpaRepository が自動で提供
}
