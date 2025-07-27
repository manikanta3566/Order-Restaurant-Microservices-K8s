package com.project.service;

import com.project.entity.MenuItem;
import com.project.entity.Order;
import com.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Order placeOrder(Order order) {
        String url = "http://RESTAURANT-SERVICE/restaurants/" + order.getRestaurantId() + "/menu";
        ResponseEntity<MenuItem[]> response = restTemplate.getForEntity(url, MenuItem[].class);

        Set<String> validItems = Arrays.stream(response.getBody()).map(MenuItem::getName).collect(Collectors.toSet());
        List<String> orderedItems = Arrays.asList(order.getItems().split(","));

        if (!validItems.containsAll(orderedItems)) {
            throw new RuntimeException("Invalid menu items!");
        }
        return orderRepository.save(order);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}
