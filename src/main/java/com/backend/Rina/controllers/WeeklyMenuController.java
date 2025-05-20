package com.backend.Rina.controllers;

import com.backend.Rina.models.WeeklyMenu;
import com.backend.Rina.security.jwt.JwtUtils;
import com.backend.Rina.services.WeeklyMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/weekly-menu")
public class WeeklyMenuController {
    @Autowired
    JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(WeeklyMenuController.class);

    private final WeeklyMenuService service;

    public WeeklyMenuController(WeeklyMenuService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdateMenu(
            @RequestBody Map<String, String> payload,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        String userId = jwtUtils.extractUserId(token);
        String dateStr = payload.get("date");
        String menuId = payload.get("menuId");

        if (dateStr == null || menuId == null) {
            return ResponseEntity.badRequest().body("Missing date or menuId");
        }

        Date inputDate;
        try {
            inputDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid date format. Use yyyy-MM-dd");
        }

        WeeklyMenu savedMenu = service.createOrUpdateMenu(userId, inputDate, menuId);
        return ResponseEntity.ok(savedMenu);
    }

    @GetMapping
    public ResponseEntity<?> getMenuByDate(
            @RequestParam("date") String dateStr,
            @RequestHeader("Authorization") String authHeader
    ) {
        Date inputDate;
        try {
            inputDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid date format. Use yyyy-MM-dd");
        }

        String token = authHeader.substring(7);
        String userId = jwtUtils.extractUserId(token);

        return service.getMenuForUserByDate(userId, inputDate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}