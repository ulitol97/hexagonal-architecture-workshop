package com.logistics.item.infrastructure.persistence.repository.jpa;

import com.logistics.item.infrastructure.persistence.entity.jpa.ItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemJpaRepository extends JpaRepository<ItemJpaEntity, String> {
    List<ItemJpaEntity> findByName(String name);
}
