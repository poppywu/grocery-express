package cs6310.backend;

import cs6310.backend.model.AppUser;
import cs6310.backend.model.Role;
import cs6310.backend.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication

public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(AppUserService appUserService) {
        return args -> {
            //create role types and save to db when the application starts
            appUserService.saveRole(new Role(null, "ROLE_USER"));
            appUserService.saveRole(new Role(null, "ROLE_MANAGER"));
            appUserService.saveRole(new Role(null, "ROLE_ADMIN"));
            appUserService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
            //create app user and save to db when the application starts
            appUserService.saveAppUser(new AppUser(null, "john", "1234", new ArrayList<>()));
            appUserService.saveAppUser(new AppUser(null,"will", "1234", new ArrayList<>()));
            appUserService.saveAppUser(new AppUser(null, "taylor", "1234", new ArrayList<>()));
            appUserService.saveAppUser(new AppUser(null, "kayne", "1234", new ArrayList<>()));
            //assign roles to each app user on the fly
            appUserService.addRoleToAppUser("john", "ROLE_USER");
            appUserService.addRoleToAppUser("will", "ROLE_MANAGER");
            appUserService.addRoleToAppUser("taylor", "ROLE_ADMIN");
            appUserService.addRoleToAppUser("kayne", "ROLE_SUPER_ADMIN");
            appUserService.addRoleToAppUser("kayne", "ROLE_ADMIN");
            appUserService.addRoleToAppUser("kayne", "ROLE_USER");
        };
    }
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
