package me.balokir.grpc_simple_streaming.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.balokir.grpc_simple_streaming.server.config.RootConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
@EnableScheduling // required for @Scheduled
@Slf4j
@RequiredArgsConstructor // required for components injection
public class ServerRunner {

    // injected
    private final GrpcServer server;
    private final RootConfig config;

    public static void main(String[] args) {
        log.info("Run server");
        SpringApplication.run(ServerRunner.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            log.info("Start {}", config.getSpring().getApplication().getName());
            server.start();
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        };
    }

    // it does not work if we `kill -9` or stop from IDE
    private void shutdown() {
        log.info("Stopping app {}", config.getSpring().getApplication().getName());
        try {
            server.stop();
            log.info("Stopped app  {}", config.getSpring().getApplication().getName());
        } catch (InterruptedException e) {
            log.error("Cannot stop app {}", config.getSpring().getApplication().getName(), e);
        }
    }

    @Scheduled(fixedRate = 5000)
    public void keepAlive() {
        // keep alive JVM
    }

//    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        server.sendMessage();
    }

}
