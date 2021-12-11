package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.controllers.commands.ActionCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandsCenter {
    private final List<Controller> controllers;

    public CommandsCenter() {
        this.controllers = new ArrayList<>();
    }

    public void addController(Controller controller) {
        controllers.add(controller);
    }

    public void update(float deltaTime) {
        List<ActionCommand> commands = new ArrayList<>();
        controllers.forEach(controller -> commands.addAll(controller.getCommands(deltaTime)));
        commands.forEach(ActionCommand::act);
    }
}
