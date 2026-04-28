package com.logistics.item.repository;

import com.logistics.item.repository.entity.ItemEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<ItemEntity, String> {
    List<ItemEntity> findByName(String name);
}
