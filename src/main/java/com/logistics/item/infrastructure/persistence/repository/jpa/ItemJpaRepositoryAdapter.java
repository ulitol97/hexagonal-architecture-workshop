package com.logistics.item.infrastructure.persistence.repository.jpa;

import com.logistics.item.application.port.ItemRepositoryPort;
import com.logistics.item.domain.Item;
import com.logistics.item.infrastructure.persistence.entity.jpa.ItemJpaEntity;
import com.logistics.item.infrastructure.persistence.mapper.jpa.ItemJpaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("h2")
@RequiredArgsConstructor
public class ItemJpaRepositoryAdapter implements ItemRepositoryPort {
    final ItemJpaEntityMapper itemJpaEntityMapper;
    final ItemJpaRepository itemJpaRepository;

    @Override
    public List<Item> getItems() {
        return itemJpaRepository.findAll().stream().map(itemJpaEntityMapper::toDomain).toList();
    }

    @Override
    public Optional<Item> getItemById(String id) {
        return itemJpaRepository.findById(id).map(itemJpaEntityMapper::toDomain);
    }

    @Override
    public void deleteItem(String id) {
        itemJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        itemJpaRepository.deleteAll();
    }

    @Override
    public void updateItem(Item item) {
        itemJpaRepository.save(itemJpaEntityMapper.fromDomain(item));
    }

    @Override
    public String createItem(Item item) {
        itemJpaRepository.save(itemJpaEntityMapper.fromDomain(item));
        return item.getId();
    }

    @Override
    public List<String> saveAll(List<Item> item) {
      List<ItemJpaEntity> savedItems = itemJpaRepository.saveAll(item.stream().map(itemJpaEntityMapper::fromDomain).toList());
      return savedItems.stream().map(ItemJpaEntity::getId).toList();
    }

    @Override
    public Optional<Item> getItemByName(String name) {
        List<ItemJpaEntity> items = itemJpaRepository.findByName(name);
        return items.isEmpty() ? Optional.empty() : items.stream().findFirst().map(itemJpaEntityMapper::toDomain);
    }
}
