package me.balokir.yaml_with_nested_objects.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class DtoYaml {
    public static <T> T read(Reader reader, Class<T> _class) throws IOException {
        return getObjectMapper().readValue(reader, _class);
    }

    public static <T> T read(String string, Class<T> _class) throws JsonProcessingException {
        return getObjectMapper().readValue(string, _class);
    }

    public static <T> String write(T t) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(t);
    }

    @SuppressWarnings("unused")
    public static <T> void write(Writer writer, T t) throws IOException {
        getObjectMapper().writeValue(writer, t);
    }

    private static ObjectMapper getObjectMapper() {
        YAMLFactory factory =//
            new YAMLFactory()//
                             .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)//
                             .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);
        ObjectMapper mapper = //
            new ObjectMapper(factory)//
                                     .findAndRegisterModules()//
                                     .setSerializationInclusion(JsonInclude.Include.NON_NULL)//
                                     .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
