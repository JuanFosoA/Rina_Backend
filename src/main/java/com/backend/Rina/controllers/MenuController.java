package com.backend.Rina.controllers;

import com.backend.Rina.models.Menu;
import com.backend.Rina.security.jwt.JwtUtils;
import com.backend.Rina.services.ExpoPushNotificationService;
import com.backend.Rina.services.MenuService;
import com.backend.Rina.services.WeeklyMenuList;
import lombok.ToString;
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
    JwtUtils jwtUtils;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ExpoPushNotificationService expoService;

    @Autowired
    private WeeklyMenuList weeklyMenuList;

    @GetMapping
    public List<Menu> getAllMenus(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtUtils.extractUserId(token);
        return menuService.getMenusByUser(userId);
    }

    @GetMapping("/{id}")
    public Optional<Menu> getMenuById(@PathVariable String id) {
        return menuService.getMenuById(id);
    }

    @PostMapping
    public Menu createMenu(@RequestBody Menu menu,
                           @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtUtils.extractUserId(token);
        menu.setUser(userId);

        Menu createdMenu = menuService.createMenu(menu);

        expoService.sendPushNotification(
                userId,
                "Nuevo Menú Creado",
                "Se ha creado el menú: " + createdMenu.getName()
        );

        return createdMenu;
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

