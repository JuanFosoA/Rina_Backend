package com.backend.Rina.repository;

import com.backend.Rina.models.WeeklyMenu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public interface WeeklyMenuRepository extends MongoRepository<WeeklyMenu, String> {
    Optional<WeeklyMenu> findByUserIdAndMondayOfWeek(String userId, Date mondayOfWeek);
}