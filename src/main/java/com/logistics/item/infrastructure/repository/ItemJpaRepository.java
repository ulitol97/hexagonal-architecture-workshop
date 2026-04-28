package com.logistics.item.infrastructure.repository;

import com.logistics.item.infrastructure.repository.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemJpaRepository extends JpaRepository<ItemEntity, String> {
    List<ItemEntity> findByName(String name);
}
