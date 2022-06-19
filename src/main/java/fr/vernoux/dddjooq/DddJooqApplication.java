package fr.vernoux.dddjooq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DddJooqApplication {

    public static void main(String[] args) {
        SpringApplication.run(DddJooqApplication.class, args);
    }

}
