package com.backend.Rina.repository;

import com.backend.Rina.models.ERole;
import com.backend.Rina.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
