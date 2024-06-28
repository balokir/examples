package me.balokir.yaml_with_nested_objects.dto;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@DisplayName("Test yaml read support for DTO")
public class DtoYamlReadTest {

    @DisplayName("Test read CommandsContainer with empty commands list")
    @Test
    void test_empty_commands() throws IOException {
        String testCase = """
            commands: []
            """;
        CommandsContainer commandsContainer = //
            DtoCommandsYaml.read(testCase, CommandsContainer.class);
        assertThat(commandsContainer.getCommands(), notNullValue());
        assertThat(commandsContainer.getCommands(), empty());
    }

    @DisplayName("Test read CommandsContainer with one command in list")
    @Test
    void test_one_command() throws IOException {
        String testCase = """
            commands:
            - id: id1
            """;
        CommandsContainer commandsContainer = //
            DtoCommandsYaml.read(testCase, CommandsContainer.class);
        assertThat(commandsContainer.getCommands(), notNullValue());
        assertThat(commandsContainer.getCommands(), hasSize(1));
    }


    @DisplayName("Test read CommandsContainer with two commands in list")
    @Test
    void test_two_commands() throws IOException {
        String testCase = """
            commands:
            - id: id1

            - id: id2
            """;
        CommandsContainer commandsContainer = //
            DtoCommandsYaml.read(testCase, CommandsContainer.class);
        List<Command> commands = commandsContainer.getCommands();
        assertThat(commands, is(notNullValue()));
        assertThat(commands, hasSize(2));
        assertThat(commands.get(0).getId(), is("id1"));
        assertThat(commands.get(1).getId(), is("id2"));
    }

    @DisplayName("Test read CommandsContainer with all types of argument")
    @Test
    void test_all_arg_types_command() throws IOException {
        String testCase = """
            commands:
            - id: command_id
              arguments:
                - id: string_id
                  type: string
                - id: int_id
                  type: int
                - id: double_id
                  type: double
                - id: bool_id
                  type: bool
                - id: choice_id
                  type: choice
            """;
        CommandsContainer commandsContainer = //
            DtoCommandsYaml.read(testCase, CommandsContainer.class);

        List<Command> commands = commandsContainer.getCommands();
        assertThat(commands, is(notNullValue()));
        assertThat(commands, hasSize(1));
        List<Argument> arguments = commands.get(0).getArguments();
        assertThat(arguments, is(notNullValue()));
        assertThat(arguments, hasSize(5));
        assertThat(arguments.get(0).getType(), CoreMatchers.is(ArgumentType.STRING));
        assertThat(arguments.get(1).getType(), CoreMatchers.is(ArgumentType.INT));
        assertThat(arguments.get(2).getType(), CoreMatchers.is(ArgumentType.DOUBLE));
        assertThat(arguments.get(3).getType(), CoreMatchers.is(ArgumentType.BOOL));
        assertThat(arguments.get(4).getType(), CoreMatchers.is(ArgumentType.CHOICE));
    }

    @DisplayName("Test read CommandsContainer with fully filled  command")
    @Test
    void test_fully_filled_command() throws IOException {
        String testCase = """
            commands:
            - id: command_id
              name : command name
              arguments:
                - id: argument_id
                  name : argument name
                  required: yes
                  type: choice
                  variants:
                    one: One
                    two: Two
                    three: Three
            """;
        CommandsContainer commandsContainer = //
            DtoCommandsYaml.read(testCase, CommandsContainer.class);

        List<Command> commands = commandsContainer.getCommands();
        assertThat(commands, is(notNullValue()));
        assertThat(commands, hasSize(1));
        Command command = commands.get(0);
        assertThat(command.getId(), is("command_id"));
        assertThat(command.getName(), is("command name"));
        List<Argument> arguments = command.getArguments();
        assertThat(arguments, is(notNullValue()));
        assertThat(arguments, hasSize(1));
        Argument argument = arguments.get(0);
        assertThat(argument.getId(), is("argument_id"));
        assertThat(argument.getName(), is("argument name"));
        assertThat(argument.isRequired(), is(true));
        assertThat(argument.getType(), is(ArgumentType.CHOICE));

        Map<String, String> variants = argument.getVariants();
        assertThat(variants, is(notNullValue()));
        assertThat(variants.keySet(), hasSize(3));
        assertThat(variants.get("one"), is("One"));
        assertThat(variants.get("two"), is("Two"));
        assertThat(variants.get("three"), is("Three"));
    }
}
