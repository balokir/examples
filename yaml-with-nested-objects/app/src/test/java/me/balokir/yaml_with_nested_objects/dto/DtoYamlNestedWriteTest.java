/*
 * This source file was generated by the Gradle 'init' task
 */
package me.balokir.yaml_with_nested_objects.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.balokir.yaml.DtoYaml;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Test yaml write support for DTO")
class DtoYamlNestedWriteTest {

    @DisplayName("Test empty CommandsContainer")
    @Test
    void test_empty_CommandsContainer() throws JsonProcessingException {
        CommandsContainer commands = new CommandsContainer();
        String string = DtoYaml.write(commands);
        assertThat(string, is("""
                                  {}
                                  """));
    }

    @DisplayName("Test empty Command")
    @Test
    void test_empty_Command() throws JsonProcessingException {
        Command command = new Command();
        String string = DtoYaml.write(command);
        assertThat(string, is("""
                                  {}
                                  """));
    }

    @DisplayName("Test Command 'id' and 'name'")
    @Test
    void test_Command() throws JsonProcessingException {
        Command command = new Command();
        command.setId("command_id");
        command.setName("command name");
        String string = DtoYaml.write(command);
        assertThat(string, is("""
                                  id: command_id
                                  name: command name
                                  """));
    }

    @DisplayName("Test non empty CommandsContainer")
    @Test
    void test_non_empty_CommandsContainer() throws JsonProcessingException {
        List<Command> commands = new ArrayList<>();
        Command command1 = new Command();
        command1.setId("command_id1");
        command1.setName("command name1");
        commands.add(command1);

        Command command2 = new Command();
        command2.setId("command_id2");
        command2.setName("command name2");
        commands.add(command2);

        CommandsContainer commandsContainer = new CommandsContainer();
        commandsContainer.setCommands(commands);

        String string = DtoYaml.write(commandsContainer);
        assertThat(string, is("""
                                  commands:
                                  - id: command_id1
                                    name: command name1
                                  - id: command_id2
                                    name: command name2
                                  """));
    }

    @DisplayName("Test Argument 'id', 'name', 'required=true'")
    @Test
    void test_Argument_required() throws JsonProcessingException {
        Argument argument = new Argument();
        argument.setId("argument_id");
        argument.setName("argument name");
        argument.setRequired(true);
        String string = DtoYaml.write(argument);
        assertThat(string, is("""
                                  id: argument_id
                                  name: argument name
                                  required: true
                                  """));
    }

    @DisplayName("Test Argument 'id', 'name', 'type', and 'required=false'")
    @Test
    void test_Argument_not_required() throws JsonProcessingException {
        Argument argument = new Argument();
        argument.setId("argument_id");
        argument.setName("argument name");
        argument.setType(ArgumentType.STRING);
        String string = DtoYaml.write(argument);
        assertThat(string, is("""
                                  id: argument_id
                                  name: argument name
                                  type: string
                                  """));
    }

    @DisplayName("Test Argument 'type = string'")
    @Test
    void test_Argument_argument_type_string() throws JsonProcessingException {
        Argument argument = new Argument();
        argument.setId("argument_id");
        argument.setType(ArgumentType.STRING);
        String string = DtoYaml.write(argument);
        assertThat(string, is("""
                                  id: argument_id
                                  type: string
                                  """));
    }

    @DisplayName("Test Argument 'type = bool'")
    @Test
    void test_Argument_argument_type_bool() throws JsonProcessingException {
        Argument argument = new Argument();
        argument.setId("argument_id");
        argument.setType(ArgumentType.BOOL);
        String string = DtoYaml.write(argument);
        assertThat(string, is("""
                                  id: argument_id
                                  type: bool
                                  """));
    }

    @DisplayName("Test Argument 'type = int'")
    @Test
    void test_Argument_argument_type_int() throws JsonProcessingException {
        Argument argument = new Argument();
        argument.setId("argument_id");
        argument.setType(ArgumentType.INT);
        String string = DtoYaml.write(argument);
        assertThat(string, is("""
                                  id: argument_id
                                  type: int
                                  """));
    }

    @DisplayName("Test Argument 'type = double'")
    @Test
    void test_Argument_argument_type_double() throws JsonProcessingException {
        Argument argument = new Argument();
        argument.setId("argument_id");
        argument.setType(ArgumentType.DOUBLE);
        String string = DtoYaml.write(argument);
        assertThat(string, is("""
                                  id: argument_id
                                  type: double
                                  """));
    }

    @DisplayName("Test Argument 'type = choice'")
    @Test
    void test_Argument_argument_type_choice() throws JsonProcessingException {
        Argument argument = new Argument();
        argument.setId("argument_id");
        argument.setType(ArgumentType.CHOICE);
        String string = DtoYaml.write(argument);
        assertThat(string, is("""
                                  id: argument_id
                                  type: choice
                                  """));
    }

    @DisplayName("Test Argument with variants")
    @Test
    void test_Argument_argument_with_variants() throws JsonProcessingException {
        Argument argument = new Argument();
        argument.setId("argument_id");
        argument.setType(ArgumentType.CHOICE);

        Map<String, String> variants = new HashMap<>();
        variants.put("one", "One");
        variants.put("two", "Two");
        variants.put("three", "Three");
        argument.setVariants(variants);
        String string = DtoYaml.write(argument);

        assertThat(string, is("""
                                  id: argument_id
                                  type: choice
                                  variants:
                                    one: One
                                    two: Two
                                    three: Three
                                  """));
    }

    @DisplayName("Test Command with arguments")
    @Test
    void test_Command_with_arguments() throws JsonProcessingException {
        List<Argument> arguments = new ArrayList<>();

        Argument argument1 = new Argument();
        argument1.setId("argument_id1");
        argument1.setType(ArgumentType.STRING);
        arguments.add(argument1);

        Argument argument2 = new Argument();
        argument2.setId("argument_id2");
        argument2.setType(ArgumentType.INT);
        arguments.add(argument2);

        Command command = new Command();
        command.setId("command_id");
        command.setName("command name");
        command.setArguments(arguments);

        String string = DtoYaml.write(command);
        assertThat(string, is("""
                                  id: command_id
                                  name: command name
                                  arguments:
                                  - id: argument_id1
                                    type: string
                                  - id: argument_id2
                                    type: int
                                  """));
    }

}