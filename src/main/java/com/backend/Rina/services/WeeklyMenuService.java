package com.backend.Rina.services;

import com.backend.Rina.models.WeeklyMenu;
import com.backend.Rina.repository.WeeklyMenuRepository;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeeklyMenuService {

    private final WeeklyMenuRepository repository;

    public WeeklyMenuService(WeeklyMenuRepository repository) {
        this.repository = repository;
    }

    private LocalDate getMondayOfWeek(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        return date.with(DayOfWeek.MONDAY);
    }

    public WeeklyMenu createMenu(String userId, LocalDate inputDate, String menuId) {
        LocalDate monday = getMondayOfWeek(inputDate);
        WeeklyMenu weeklyMenu = new WeeklyMenu(userId, monday, menuId);
        return repository.save(weeklyMenu);
    }

    public Optional<WeeklyMenu> getMenuForUserByDate(String userId, LocalDate date) {
        LocalDate monday = getMondayOfWeek(date);
        return repository.findByUserIdAndMondayOfWeek(userId, monday);
    }
}

