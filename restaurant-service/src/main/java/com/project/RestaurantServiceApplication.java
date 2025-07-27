package com.project;

import com.project.entity.MenuItem;
import com.project.entity.Restaurant;
import com.project.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class RestaurantServiceApplication implements CommandLineRunner {

	@Autowired
	private RestaurantRepository restaurantRepository;

	public static void main(String[] args) {
		SpringApplication.run(RestaurantServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if(restaurantRepository.count()==0) {
			Restaurant r1 = new Restaurant("Pizza Palace");
			r1.setMenu(List.of(new MenuItem("Margherita", 200.0, r1), new MenuItem("Farmhouse", 250.0, r1)));
			restaurantRepository.save(r1);
		}
	}

}
