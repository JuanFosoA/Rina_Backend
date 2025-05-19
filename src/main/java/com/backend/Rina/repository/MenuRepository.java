package com.backend.Rina.repository;

import com.backend.Rina.models.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MenuRepository extends MongoRepository<Menu, String> {
    List<Menu> findByUser(String userId);
}