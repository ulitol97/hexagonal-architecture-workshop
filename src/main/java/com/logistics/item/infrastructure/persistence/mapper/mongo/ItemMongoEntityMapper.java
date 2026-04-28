package com.logistics.item.infrastructure.persistence.mapper.mongo;

import com.logistics.item.domain.Item;
import com.logistics.item.infrastructure.persistence.entity.mongo.ItemMongoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMongoEntityMapper {
    ItemMongoEntity fromDomain(Item in);

    Item toDomain(ItemMongoEntity in);
}
