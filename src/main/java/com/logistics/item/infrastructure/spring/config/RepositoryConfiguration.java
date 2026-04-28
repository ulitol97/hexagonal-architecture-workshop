package com.logistics.item.infrastructure.spring.config;

import com.logistics.item.infrastructure.persistence.repository.jpa.ItemJpaRepository;
import com.logistics.item.infrastructure.persistence.repository.mongo.ItemMongoRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
public class RepositoryConfiguration {

    @Configuration
    @Profile("h2")
    @EnableJpaRepositories(basePackageClasses = ItemJpaRepository.class)
    static class JpaRepositoryProfileConfiguration {
    }

    @Configuration
    @Profile("mongo")
    @EnableMongoRepositories(basePackageClasses = ItemMongoRepository.class)
    static class MongoRepositoryProfileConfiguration {
    }
}
