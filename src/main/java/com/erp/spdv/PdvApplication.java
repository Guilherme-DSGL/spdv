package com.erp.spdv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.erp.spdv.config.security.core.SecurityConstants;

@SpringBootApplication
@RestController
public class PdvApplication {

    public static void main(String[] args) {

        SpringApplication.run(PdvApplication.class, args);

    }
}
