package com.backend.Rina.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "menus")
public class Menu {

    @Id
    private String id;
    private String name;

    private Map<String, Map<String, String>> dias;

    public Menu() {}

    public Menu(Map<String, Map<String, String>> dias) {
        this.dias = dias;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Map<String, String>> getDias() {
        return dias;
    }

    public void setDias(Map<String, Map<String, String>> dias) {
        this.dias = dias;
    }
}
