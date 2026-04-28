package com.logistics.item.infrastructure.persistence.entity.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ItemMongoEntity {
  @Id
  private String id;
  private String name;
}