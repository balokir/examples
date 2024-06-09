package me.balokir.yaml_with_nested_objects.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Test yaml read support for DTO")
public class DtoYamlReadTest {

    @DisplayName("Test read file with empty commands list")
    @Test
    void test_empty_commands() throws IOException {
        CommandsContainer commandsContainer = DtoYaml.read(getReader("commands0.yaml"), CommandsContainer.class);
        assertThat(commandsContainer.getCommands(), is(notNullValue()));
        assertThat(commandsContainer.getCommands(), is(empty()));
    }


    @DisplayName("Test read file with one command in list")
    @Test
    void test_one_command() throws IOException {
        CommandsContainer commandsContainer = DtoYaml.read(getReader("commands1.yaml"), CommandsContainer.class);
        assertThat(commandsContainer.getCommands(), is(notNullValue()));
        assertThat(commandsContainer.getCommands().size(), is(1));
    }

    @DisplayName("Test read file with two commands in list")
    @Test
    void test_two_commands() throws IOException {
        CommandsContainer commandsContainer = DtoYaml.read(getReader("commands2.yaml"), CommandsContainer.class);
        List<Command> commands = commandsContainer.getCommands();
        assertThat(commands, is(notNullValue()));
        assertThat(commands.size(), is(2));
        assertThat(commands.get(0).getId(), is("id1"));
        assertThat(commands.get(1).getId(), is("id2"));
    }

    @DisplayName("Test read file with all types of argument")
    @Test
    void test_all_arg_types_command() throws IOException {
        CommandsContainer commandsContainer = DtoYaml.read(getReader("commands_all_types.yaml"),
                                                           CommandsContainer.class);
        List<Command> commands = commandsContainer.getCommands();
        assertThat(commands, is(notNullValue()));
        assertThat(commands.size(), is(1));
        List<Argument> arguments = commands.get(0).getArguments();
        assertThat(arguments, is(notNullValue()));
        assertThat(arguments.size(), is(5));
        assertThat(arguments.get(0).getType(), is(ArgumentType.STRING));
        assertThat(arguments.get(1).getType(), is(ArgumentType.INT));
        assertThat(arguments.get(2).getType(), is(ArgumentType.DOUBLE));
        assertThat(arguments.get(3).getType(), is(ArgumentType.BOOL));
        assertThat(arguments.get(4).getType(), is(ArgumentType.CHOICE));
    }

    @DisplayName("Test read with file fully filled  command")
    @Test
    void test_fully_filled_command() throws IOException {
        CommandsContainer commandsContainer = DtoYaml.read(getReader("commands_full.yaml"),
                                                           CommandsContainer.class);
        List<Command> commands = commandsContainer.getCommands();
        assertThat(commands, is(notNullValue()));
        assertThat(commands.size(), is(1));
        Command command = commands.get(0);
        assertThat(command.getId(), is("command_id"));
        assertThat(command.getName(), is("command name"));
        List<Argument> arguments = command.getArguments();
        assertThat(arguments, is(notNullValue()));
        assertThat(arguments.size(), is(1));
        Argument argument = arguments.get(0);
        assertThat(argument.getId(), is("argument_id"));
        assertThat(argument.getName(), is("argument name"));
        assertThat(argument.isRequired(), is(true));
        assertThat(argument.getType(), is(ArgumentType.CHOICE));

        Map<String, String> variants = argument.getVariants();
        assertThat(variants, is(notNullValue()));
        assertThat(variants.size(), is(3));
        assertThat(variants.get("one"), is("One"));
        assertThat(variants.get("two"), is("Two"));
        assertThat(variants.get("three"), is("Three"));
    }

    private static Reader getReader(String file) throws IOException {
        InputStream inputStream = DtoYamlReadTest.class.getClassLoader().getResourceAsStream(file);
        if (inputStream == null) {
            throw new IOException("Cannot load file " + file);
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
