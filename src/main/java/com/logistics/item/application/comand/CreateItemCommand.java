package com.logistics.item.application.comand;

import com.logistics.item.application.port.ItemRepositoryPort;
import com.logistics.item.domain.DuplicatedItemException;
import com.logistics.item.domain.InvalidItemException;
import com.logistics.item.domain.Item;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Builder
public class CreateItemCommand {
    @NonNull
    final ItemRepositoryPort itemRepository;
    final String name;

    public String handle() throws InvalidItemException, DuplicatedItemException {
        if (!StringUtils.hasText(name)) throw new InvalidItemException();
        if (itemRepository.getItemByName(name).isPresent())
            throw new DuplicatedItemException();
        Item item = Item.builder().id(UUID.randomUUID().toString()).name(name).build();
        itemRepository.createItem(item);
        return item.getId();
    }
}
