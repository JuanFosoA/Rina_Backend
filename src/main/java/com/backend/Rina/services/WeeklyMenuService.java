package com.backend.Rina.services;

import com.backend.Rina.models.WeeklyMenu;
import com.backend.Rina.repository.WeeklyMenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class WeeklyMenuService {

    private final WeeklyMenuRepository repository;

    public WeeklyMenuService(WeeklyMenuRepository repository) {
        this.repository = repository;
    }

    private Date getMondayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DATE, 1);
        }

        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    public WeeklyMenu createOrUpdateMenu(String userId, Date inputDate, String menuId) {
        Date monday = getMondayOfWeek(inputDate);

        Optional<WeeklyMenu> existingMenuOpt = repository.findByUserIdAndMondayOfWeek(userId, monday);

        if (existingMenuOpt.isPresent()) {
            WeeklyMenu existingMenu = existingMenuOpt.get();
            existingMenu.setMenuId(menuId);
            return repository.save(existingMenu);
        }

        WeeklyMenu newMenu = new WeeklyMenu(userId, monday, menuId);
        return repository.save(newMenu);
    }

    public WeeklyMenu createMenu(String userId, Date inputDate, String menuId) {
        Date monday = getMondayOfWeek(inputDate);
        WeeklyMenu weeklyMenu = new WeeklyMenu(userId, monday, menuId);
        return repository.save(weeklyMenu);
    }

    public Optional<WeeklyMenu> getMenuForUserByDate(String userId, Date date) {
        Date monday = getMondayOfWeek(date);
        return repository.findByUserIdAndMondayOfWeek(userId, monday);
    }
}
