package ru.mipt.bit.platformer.actors;

import ru.mipt.bit.platformer.actors.commands.ActionCommand;
import ru.mipt.bit.platformer.actors.commands.MoveCommand;
import ru.mipt.bit.platformer.actors.commands.ShootCommand;
import ru.mipt.bit.platformer.input.directions.Directions;
import ru.mipt.bit.platformer.input.directions.DirectionsListener;
import ru.mipt.bit.platformer.input.shootlistener.ShootListener;
import ru.mipt.bit.platformer.objects.tank.Tank;

import java.util.ArrayList;
import java.util.List;

public class Player implements Actor {
    private final Tank targetTank;
    private final DirectionsListener directionsListener;
    private final ShootListener shootListener;

    public Player(Tank targetTank, DirectionsListener directionsListener, ShootListener shootListener) {
        this.targetTank = targetTank;
        this.directionsListener = directionsListener;
        this.shootListener = shootListener;
    }

    @Override
    public List<ActionCommand> getCommands(float deltaTime) {
        List<ActionCommand> commands = new ArrayList<>();
        Directions direction = directionsListener.pressed();
        if (direction != null) {
            commands.add(new MoveCommand(targetTank, direction.getDirection()));
        }
        if (shootListener.shootPressed()) {
            commands.add(new ShootCommand(targetTank));
        }
        return commands;
    }
}
