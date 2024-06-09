package me.balokir.grpc_simple_streaming.server.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SpringConfig {
    @JsonProperty(value = "application")
    private ApplicationConfig application;
}
