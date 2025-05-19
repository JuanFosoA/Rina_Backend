package com.backend.Rina.controllers;

import com.backend.Rina.models.WeeklyMenu;
import com.backend.Rina.security.jwt.JwtUtils;
import com.backend.Rina.services.WeeklyMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/weekly-menu")
public class WeeklyMenuController {
    @Autowired
    JwtUtils jwtUtils;


    private final WeeklyMenuService service;

    public WeeklyMenuController(WeeklyMenuService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createMenu(
            @RequestBody Map<String, String> payload
            , @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        String userId = jwtUtils.extractUserId(token);
        String dateStr = payload.get("date");
        String menuId = payload.get("menuId");

        if (dateStr == null || menuId == null) {
            return ResponseEntity.badRequest().body("Missing date or menuId");
        }

        LocalDate date = LocalDate.parse(dateStr);

        WeeklyMenu savedMenu = service.createMenu(userId, date, menuId);
        return ResponseEntity.ok(savedMenu);
    }

    @GetMapping
    public ResponseEntity<?> getMenuByDate(
            @RequestParam("date") String dateStr, @RequestHeader("Authorization") String authHeader
    ) {
        LocalDate date = LocalDate.parse(dateStr);
        String token = authHeader.substring(7);
        String userId = jwtUtils.extractUserId(token);

        return service.getMenuForUserByDate(userId, date)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}