package me.balokir.grpc_simple_streaming.client.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ServerConfig {
    @JsonProperty(value = "port")
    private Integer port = 50000;

    @JsonProperty(value = "host")
    private String host = "127.0.0.1";
}
