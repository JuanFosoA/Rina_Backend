package com.backend.Rina.repository;

import com.backend.Rina.models.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MenuRepository extends MongoRepository<Menu, String> {
}