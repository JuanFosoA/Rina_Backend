package com.backend.Rina.controllers;

import com.backend.Rina.models.Menu;
import com.backend.Rina.services.MenuService;
import com.backend.Rina.services.WeeklyMenuList;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/menus")
public class MenuController {


    @Autowired
    private MenuService menuService;

    @Autowired
    private WeeklyMenuList weeklyMenuList;

    @GetMapping
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/{id}")
    public Optional<Menu> getMenuById(@PathVariable String id) {
        return menuService.getMenuById(id);
    }

    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        System.out.println(menu);
        return menuService.createMenu(menu);
    }

    @PostMapping("/listaDeCompra")
    public List<Document> calcularIngredientesTotales(@RequestBody Map<String, Map<String, String>> menuSemanalJson) {
        Document menuDocument = new Document();

        for (Map.Entry<String, Map<String, String>> dia : menuSemanalJson.entrySet()) {
            Document comidasDelDia = new Document();
            for (Map.Entry<String, String> comida : dia.getValue().entrySet()) {
                comidasDelDia.append(comida.getKey(), comida.getValue());
            }
            menuDocument.append(dia.getKey(), comidasDelDia);
        }
        System.out.println(menuDocument);
        return weeklyMenuList.obtenerIngredientesTotales(menuDocument);
    }


}

