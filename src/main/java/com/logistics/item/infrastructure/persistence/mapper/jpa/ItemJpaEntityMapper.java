package com.logistics.item.infrastructure.persistence.mapper.jpa;

import com.logistics.item.domain.Item;
import com.logistics.item.infrastructure.persistence.entity.jpa.ItemJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemJpaEntityMapper {
    ItemJpaEntity fromDomain(Item in);

    Item toDomain(ItemJpaEntity in);
}
