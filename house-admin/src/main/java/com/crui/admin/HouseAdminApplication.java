package com.crui.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class HouseAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseAdminApplication.class, args);
    }

}
