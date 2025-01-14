package com.grocery.booking.config;

import com.grocery.booking.entity.User;
import com.grocery.booking.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeeder {

    @Bean
    CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        Set adminRole = new HashSet<>();
        adminRole.add("ADMIN");
        Set userRole = new HashSet<>();
        userRole.add("USER");
        return args -> {
            if (!userRepository.findByUsername("admin").isPresent()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles(adminRole);
                userRepository.save(admin);
            }

            if (!userRepository.findByUsername("user").isPresent()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRoles(userRole);
                userRepository.save(user);
            }
        };
    }
}
