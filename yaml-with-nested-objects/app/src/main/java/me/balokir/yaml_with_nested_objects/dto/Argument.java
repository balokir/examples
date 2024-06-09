package me.balokir.yaml_with_nested_objects.dto;

import lombok.Data;

import java.util.Map;

@Data
public class Argument {
    private String id;
    private String name;
    private ArgumentType type;

    private Boolean required;

    public boolean isRequired() {
        return required != null && required == Boolean.TRUE;
    }

    private Map<String, String> variants;
}
