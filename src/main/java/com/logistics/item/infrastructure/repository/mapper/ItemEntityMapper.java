package com.logistics.item.infrastructure.repository.mapper;

import com.logistics.item.domain.Item;
import com.logistics.item.infrastructure.repository.entity.ItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemEntityMapper {
    ItemEntity fromDomain(Item in);

    Item toDomain(ItemEntity in);
}
