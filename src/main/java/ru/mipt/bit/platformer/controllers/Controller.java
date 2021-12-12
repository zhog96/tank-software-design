package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.controllers.commands.ActionCommand;

import java.util.List;

public interface Controller {
    List<ActionCommand> getCommands(float deltaTime);
}