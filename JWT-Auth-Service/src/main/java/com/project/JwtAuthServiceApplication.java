package com.project;

import com.project.entity.User;
import com.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class JwtAuthServiceApplication {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			if(userRepository.findByEmail("admin@gmail.com")==null){
				User user = new User();
				user.setEmail("admin@gmail.com");
				user.setPassword(passwordEncoder.encode("password"));
				user.setName("admin");
				user.setRoles("ROLE_ADMIN");
				userRepository.save(user);
			}
		};
	}
}
