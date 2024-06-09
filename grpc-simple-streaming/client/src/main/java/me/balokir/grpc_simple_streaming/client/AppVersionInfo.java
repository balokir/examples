package me.balokir.grpc_simple_streaming.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

public class AppVersionInfo {
    private final String version;

    public AppVersionInfo(ApplicationContext context) {
        version = context.getBeansWithAnnotation(SpringBootApplication.class).entrySet().stream()//
                         .findFirst()//
                         .flatMap(es -> Optional.ofNullable(
                                 es.getValue().getClass().getPackage().getImplementationVersion()))//
                         .orElse("unknown");
    }

    public String getVersion() {
        return version;
    }
}
