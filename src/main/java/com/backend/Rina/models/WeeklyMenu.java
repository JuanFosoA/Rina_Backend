package com.backend.Rina.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "Weekly_menus")
public class WeeklyMenu {
    @Id
    private String id;
    private LocalDate mondayOfWeek;
    private String menuId;

    public WeeklyMenu(String id, LocalDate mondayOfWeek, String menuId) {
        this.id = id;
        this.mondayOfWeek = mondayOfWeek;
        this.menuId = menuId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
