package me.balokir.yaml_with_custom_deserializer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.balokir.yaml_with_nested_objects.dto.ArgumentType;

import java.util.Map;

@lombok.Data
public class Data {
    @JsonProperty("Name1")
    private String name1;
    @JsonProperty("Name2")
    private String name2;
}
