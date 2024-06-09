package me.balokir.yaml_with_nested_objects.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ArgumentType {
    @JsonProperty("string")
    STRING,
    @JsonProperty("int")
    INT,
    @JsonProperty("double")
    DOUBLE,
    @JsonProperty("bool")
    BOOL,
    @JsonProperty("choice")
    CHOICE
}
