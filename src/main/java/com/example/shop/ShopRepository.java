package com.example.shop;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    // 基本CRUDは JpaRepository が自動で提供
}
