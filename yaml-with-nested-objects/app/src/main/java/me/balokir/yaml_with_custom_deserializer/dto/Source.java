package me.balokir.yaml_with_custom_deserializer.dto;

import java.util.List;

@lombok.Data
public class Source {
    private String name;
    private List<Data> data;
}
