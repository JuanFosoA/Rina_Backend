package com.backend.Rina.repository;

import com.backend.Rina.models.UserExpoToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserExpoTokenRepository extends MongoRepository<UserExpoToken, String> {
    Optional<UserExpoToken> findByUserId(String userId);

    void deleteByUserId(String userId);
}
