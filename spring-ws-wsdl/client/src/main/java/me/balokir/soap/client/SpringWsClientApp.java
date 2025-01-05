package me.balokir.soap.client;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class SpringWsClientApp {
    private final HelloWorldClient client;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Provide first name and last name as arguments");
            System.exit(1);
        }
        SpringApplication.run(SpringWsClientApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println(client.sayHello(args[0], args[1]));
            System.exit(0);
        };
    }
}