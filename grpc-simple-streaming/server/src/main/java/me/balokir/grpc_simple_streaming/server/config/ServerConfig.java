package me.balokir.grpc_simple_streaming.server.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ServerConfig {
    @JsonProperty(value = "port")
    private Integer port = 50000;
}
