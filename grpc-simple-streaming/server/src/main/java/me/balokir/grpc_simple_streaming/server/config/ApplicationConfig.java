package me.balokir.grpc_simple_streaming.server.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApplicationConfig {
    @JsonProperty(value = "name")
    private String name;
}
