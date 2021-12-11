package ru.mipt.bit.platformer.actors;

import ru.mipt.bit.platformer.actors.commands.ActionCommand;

import java.util.List;

public interface Actor {
    List<ActionCommand> getCommands(float deltaTime);
}