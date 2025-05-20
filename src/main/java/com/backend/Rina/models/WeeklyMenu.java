package com.backend.Rina.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "Weekly_menus")
public class WeeklyMenu {
    @Id
    private String Id;
    @JsonIgnore
    private String userId;
    private Date mondayOfWeek;
    private String menuId;

    public WeeklyMenu(String id, String userId, Date mondayOfWeek, String menuId) {
        Id = id;
        this.userId = userId;
        this.mondayOfWeek = mondayOfWeek;
        this.menuId = menuId;
    }

    public WeeklyMenu(String userId, Date mondayOfWeek, String menuId) {
        this.userId = userId;
        this.mondayOfWeek = mondayOfWeek;
        this.menuId = menuId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getMondayOfWeek() {
        return mondayOfWeek;
    }

    public void setMondayOfWeek(Date mondayOfWeek) {
        this.mondayOfWeek = mondayOfWeek;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
