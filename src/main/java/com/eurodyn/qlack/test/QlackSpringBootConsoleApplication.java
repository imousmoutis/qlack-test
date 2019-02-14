package com.eurodyn.qlack.test;

import com.eurodyn.qlack.test.services.aaa.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories("com.eurodyn.qlack.fuse.aaa.repository")
@EntityScan("com.eurodyn.qlack.fuse.aaa.model")
@ComponentScan(basePackages = {
        "com.eurodyn.qlack.test.services",
        "com.eurodyn.qlack.fuse.aaa"
})
public class QlackSpringBootConsoleApplication implements CommandLineRunner {

    @Autowired
    private UserTestService userTestService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(QlackSpringBootConsoleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    public void run(String... args) {

        if (args.length == 0){
            System.out.println("Please provide the service name you want to test as the first argument.");
        } else{

            switch (args[0]){
                case "UserService":
                    userTestService.createUser();
                    userTestService.updateUser();
                    userTestService.getUserByName();
                    userTestService.deleteUser();
                    break;
                default: System.out.println("Service " +args[0]+ " is not found :(");
            }
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
