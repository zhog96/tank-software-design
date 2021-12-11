package ru.mipt.bit.platformer.actors;

import ru.mipt.bit.platformer.actors.commands.ActionCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandsCenter {
    private final List<Actor> actors;

    public CommandsCenter() {
        this.actors = new ArrayList<>();
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void update(float deltaTime) {
        List<ActionCommand> commands = new ArrayList<>();
        actors.forEach(actor -> commands.addAll(actor.getCommands(deltaTime)));
        commands.forEach(ActionCommand::act);
    }
}
