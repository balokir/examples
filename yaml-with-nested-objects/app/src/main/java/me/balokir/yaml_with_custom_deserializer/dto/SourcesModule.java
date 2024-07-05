package me.balokir.yaml_with_custom_deserializer.dto;

import com.fasterxml.jackson.databind.module.SimpleModule;

@SuppressWarnings("unused")
public class SourcesModule extends SimpleModule {
    public SourcesModule() {
        super("SourcesModule");
        addDeserializer(SourcesContainer.class, new SourcesDeserializer());
    }
}
