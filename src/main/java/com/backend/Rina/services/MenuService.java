package com.backend.Rina.services;

import com.backend.Rina.models.Menu;
import com.backend.Rina.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Optional<Menu> getMenuById(String id) {
        return menuRepository.findById(id);
    }

    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

}
