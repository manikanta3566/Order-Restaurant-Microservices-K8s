package com.project.controller;

import com.project.entity.MenuItem;
import com.project.entity.Restaurant;
import com.project.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}/menu")
    public List<MenuItem> getMenu(@PathVariable Integer id) {
        List<MenuItem> menuItems = restaurantRepository.findById(id).map(Restaurant::getMenu).orElse(List.of());
        return menuItems;
    }
}
