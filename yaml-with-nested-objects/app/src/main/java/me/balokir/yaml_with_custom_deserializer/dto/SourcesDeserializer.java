package me.balokir.yaml_with_custom_deserializer.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import me.balokir.yaml.DtoYaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SourcesDeserializer extends StdDeserializer<SourcesContainer> {

    public SourcesDeserializer() {
        super(SourcesContainer.class);
    }

    @Override
    public SourcesContainer deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        ObjectReader dataReader = DtoYaml.getObjectMapper().readerFor(Data.class);

        SourcesContainer sources = new SourcesContainer();
        sources.setSources(new ArrayList<>());

        for (JsonNode jsonNode : (ArrayNode)node.get("Sources")) {
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            if(fields.hasNext()){
                Map.Entry<String, JsonNode> next = fields.next();

                Source source = new Source();
                sources.getSources().add(source);
                source.setName(next.getKey());
                source.setData(new ArrayList<>());

                for (JsonNode jsonDataNode : (ArrayNode) next.getValue()) {
                    source.getData().add(dataReader.readValue(jsonDataNode));
                }
            }
        }
        return sources;
    }
}
