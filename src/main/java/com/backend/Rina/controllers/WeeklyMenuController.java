package com.backend.Rina.controllers;

import com.backend.Rina.models.WeeklyMenu;
import com.backend.Rina.services.WeeklyMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/weekly-menu")
public class WeeklyMenuController {

    private final WeeklyMenuService service;

    public WeeklyMenuController(WeeklyMenuService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createMenu(
            @RequestBody Map<String, String> payload,
            Principal principal
    ) {
        String dateStr = payload.get("date");
        String menuId = payload.get("menuId");

        if (dateStr == null || menuId == null) {
            return ResponseEntity.badRequest().body("Missing date or menuId");
        }

        LocalDate date = LocalDate.parse(dateStr);
        String userId = principal.getName(); // JWT debe inyectar username como userId

        WeeklyMenu savedMenu = service.createMenu(userId, date, menuId);
        return ResponseEntity.ok(savedMenu);
    }

    @GetMapping
    public ResponseEntity<?> getMenuByDate(
            @RequestParam("date") String dateStr,
            Principal principal
    ) {
        LocalDate date = LocalDate.parse(dateStr);
        String userId = principal.getName();

        return service.getMenuForUserByDate(userId, date)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}