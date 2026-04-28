package com.logistics.item.infrastructure.persistence.repository.mongo;

import com.logistics.item.application.port.ItemRepositoryPort;
import com.logistics.item.domain.Item;
import com.logistics.item.infrastructure.persistence.entity.mongo.ItemMongoEntity;
import com.logistics.item.infrastructure.persistence.mapper.mongo.ItemMongoEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
@RequiredArgsConstructor
public class ItemMongoRepositoryAdapter implements ItemRepositoryPort {
    final ItemMongoEntityMapper itemEntityMapper;
    final ItemMongoRepository itemMongoRepository;

    @Override
    public List<Item> getItems() {
        return itemMongoRepository.findAll().stream().map(itemEntityMapper::toDomain).toList();
    }

    @Override
    public Optional<Item> getItemById(String id) {
        return itemMongoRepository.findById(id).map(itemEntityMapper::toDomain);
    }

    @Override
    public void deleteItem(String id) {
        itemMongoRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        itemMongoRepository.deleteAll();
    }

    @Override
    public void updateItem(Item item) {
        itemMongoRepository.save(itemEntityMapper.fromDomain(item));
    }

    @Override
    public String createItem(Item item) {
        itemMongoRepository.save(itemEntityMapper.fromDomain(item));
        return item.getId();
    }

    @Override
    public List<String> saveAll(List<Item> item) {
        List<ItemMongoEntity> savedItems = itemMongoRepository.saveAll(item.stream().map(itemEntityMapper::fromDomain).toList());
        return savedItems.stream().map(ItemMongoEntity::getId).toList();
    }

    @Override
    public Optional<Item> getItemByName(String name) {
        List<ItemMongoEntity> items = itemMongoRepository.findByName(name);
        return items.isEmpty() ? Optional.empty() : items.stream().findFirst().map(itemEntityMapper::toDomain);
    }
}
