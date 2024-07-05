package me.balokir.yaml_with_custom_deserializer.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

@Data
@JsonDeserialize(using = SourcesDeserializer.class)
public class SourcesContainer {
    private List<Source> sources;
}
