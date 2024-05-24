package org.example.worlddbspringmvc;

import org.example.worlddbspringmvc.model.entities.User;
import org.example.worlddbspringmvc.model.respositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;


@SpringBootApplication
public class WorldDbSpringMvcApplication {

    private static final Logger logger = Logger.getLogger("Spring Logger");

    public static void main(String[] args) {
        SpringApplication.run(WorldDbSpringMvcApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUserName("user").isEmpty()) {
                userRepository.save(new User("user", passwordEncoder.encode("password"), "ROLE_USER"));
            }
            if (userRepository.findByUserName("admin").isEmpty()) {
                userRepository.save(new User("admin", passwordEncoder.encode("password"), "ROLE_USER,ROLE_ADMIN"));
            }
        };
    }
}
