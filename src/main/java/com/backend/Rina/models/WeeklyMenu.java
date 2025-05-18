package com.backend.Rina.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "Weekly_menus")
public class WeeklyMenu {
    @Id
    private String userId;
    private LocalDate mondayOfWeek;
    private String menuId;

    public WeeklyMenu(String userId, LocalDate mondayOfWeek, String menuId) {
        this.userId = userId;
        this.mondayOfWeek = mondayOfWeek;
        this.menuId = menuId;
    }

    public String getId() {
        return userId;
    }

    public void setId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getMondayOfWeek() {
        return mondayOfWeek;
    }

    public void setMondayOfWeek(LocalDate mondayOfWeek) {
        this.mondayOfWeek = mondayOfWeek;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
