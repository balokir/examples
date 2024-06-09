package me.balokir.grpc_simple_streaming.client.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties()
public class RootConfig {

    @JsonProperty(value = "spring")
    private SpringConfig spring;

    @JsonProperty(value = "server")
    private ServerConfig server;

}
