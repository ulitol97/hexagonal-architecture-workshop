package com.logistics.item.infrastructure.repository;

import com.logistics.item.application.port.ItemRepositoryPort;
import com.logistics.item.domain.Item;
import com.logistics.item.infrastructure.repository.entity.ItemEntity;
import com.logistics.item.infrastructure.repository.mapper.ItemEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryAdapter implements ItemRepositoryPort {
    final ItemEntityMapper itemEntityMapper;
    final ItemJpaRepository itemJpaRepository;

    @Override
    public List<Item> getItems() {
        return itemJpaRepository.findAll().stream().map(itemEntityMapper::toDomain).toList();
    }

    @Override
    public Optional<Item> getItemById(String id) {
        return itemJpaRepository.findById(id).map(itemEntityMapper::toDomain);
    }

    @Override
    public void deleteItem(String id) {
        itemJpaRepository.deleteById(id);
    }

    @Override
    public void updateItem(Item item) {
        itemJpaRepository.save(itemEntityMapper.fromDomain(item));
    }

    @Override
    public String createItem(Item item) {
        itemJpaRepository.save(itemEntityMapper.fromDomain(item));
        return item.getId();
    }

    @Override
    public Optional<Item> getItemByName(String name) {
        List<ItemEntity> items = itemJpaRepository.findByName(name);
        return items.isEmpty() ? Optional.empty() : items.stream().findFirst().map(itemEntityMapper::toDomain);
    }
}
