package com.logistics.item.domain;

import lombok.*;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Builder.Default
    final String id = UUID.randomUUID().toString();
    @With
    String name;
}
