package me.balokir.yaml_with_nested_objects.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommandsContainer {
    private List<Command> commands;
}
