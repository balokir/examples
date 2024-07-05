package me.balokir.yaml_with_custom_deserializer.dto;

import me.balokir.yaml.DtoYaml;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@DisplayName("Test yaml with custom deserializer read support for DTO")
public class DtoYamlCustomReadTest {

    @DisplayName("Test read full")
    @Test
    void test_full() throws IOException {
        String testCase = """
            Sources:
              - Source1:
                  - Name1: Name 1 1 1
                    Name2: Name 1 1 2
                  - Name1: Name 1 2 1
                    Name2: Name 1 2 2
              - Source2:
                  - Name1: Name 2 1 1
                    Name2: Name 2 1 2
                  - Name1: Name 2 2 1
                    Name2: Name 2 2 2
                        """;
        SourcesContainer sources = DtoYaml.read(testCase, SourcesContainer.class);
        assertThat(sources, notNullValue());
        List<Source> sourcesList = sources.getSources();
        assertThat(sourcesList, notNullValue());
        assertThat(sourcesList, hasSize(2));
        Source source;
        List<Data> dataList;
        Data data;

        source = sourcesList.get(0);
        assertThat(source, notNullValue());

        dataList = source.getData();
        assertThat(dataList, notNullValue());
        assertThat(dataList, hasSize(2));

        data = dataList.get(0);
        assertThat(data, notNullValue());
        assertThat(data.getName1(), is("Name 1 1 1"));
        assertThat(data.getName2(), is("Name 1 1 2"));

        data = dataList.get(1);
        assertThat(data, notNullValue());
        assertThat(data.getName1(), is("Name 1 2 1"));
        assertThat(data.getName2(), is("Name 1 2 2"));

        source = sourcesList.get(1);
        assertThat(source, notNullValue());

        dataList = source.getData();
        assertThat(dataList, notNullValue());
        assertThat(dataList, hasSize(2));

        data = dataList.get(0);
        assertThat(data, notNullValue());
        assertThat(data.getName1(), is("Name 2 1 1"));
        assertThat(data.getName2(), is("Name 2 1 2"));

        data = dataList.get(1);
        assertThat(data, notNullValue());
        assertThat(data.getName1(), is("Name 2 2 1"));
        assertThat(data.getName2(), is("Name 2 2 2"));

    }

}
