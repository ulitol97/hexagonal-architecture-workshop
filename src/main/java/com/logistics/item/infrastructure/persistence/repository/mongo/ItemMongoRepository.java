package com.logistics.item.infrastructure.persistence.repository.mongo;

import com.logistics.item.infrastructure.persistence.entity.mongo.ItemMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemMongoRepository extends MongoRepository<ItemMongoEntity, String> {
    List<ItemMongoEntity> findByName(String name);
}
