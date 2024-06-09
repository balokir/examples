package me.balokir.grpc_simple_streaming.client.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import me.balokir.grpc_simple_streaming.client.ClientRunner;

@Data
public class SpringConfig {
    @JsonProperty(value = "application")
    private ApplicationConfig application;
}
