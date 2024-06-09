package me.balokir.grpc_simple_streaming.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.balokir.grpc_simple_streaming.client.config.RootConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Optional;


@Slf4j
@SpringBootApplication
@EnableScheduling // required for @Scheduled
@RequiredArgsConstructor // required for components injection
public class ClientRunner {

    private final RootConfig config;

    private final GrpcClient client;


    public static void main(String[] args) {
        log.info("Run client");
        SpringApplication.run(ClientRunner.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            String implementationVersion = ClientRunner.class.getPackage().getImplementationVersion();

            config.getSpring().getApplication()
                  .setVersion(implementationVersion == null ? "unknown" : implementationVersion);

            log.info("Start app {}:{}", config.getSpring().getApplication().getName(),
                     config.getSpring().getApplication().getVersion());
            client.start();
        };
    }

    @Scheduled(fixedRate = 1000)
    public void keepAlive() {
        // keep alive JVM
    }

    @Scheduled(fixedRate = 100)
    public void sendMessage() {
        client.sendMessage();
    }

}
