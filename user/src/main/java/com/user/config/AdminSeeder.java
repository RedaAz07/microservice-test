package com.lets_plat.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lets_plat.entity.User;
import com.lets_plat.repository.UserRepository;

@Configuration
public class AdminSeeder {
    @Bean
    public CommandLineRunner seedAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByName("Admin").isEmpty()) {

                User Admin = new User();
                Admin.setEmail("Admin@gmail.com");
                Admin.setName("Admin");
                Admin.setPassword(passwordEncoder.encode("Admin@123"));
                Admin.setRole("ROLE_ADMIN");
                userRepository.save(Admin);
            }
        };

    }

}
