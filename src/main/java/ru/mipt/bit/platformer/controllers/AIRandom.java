package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.controllers.commands.ActionCommand;
import ru.mipt.bit.platformer.controllers.commands.MoveCommand;
import ru.mipt.bit.platformer.controllers.commands.ShootCommand;
import ru.mipt.bit.platformer.input.directions.Directions;
import ru.mipt.bit.platformer.objects.tank.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIRandom implements Controller {
    private final List<Tank> targetTanks;

    public AIRandom(List<Tank> targetTanks) {
        this.targetTanks = targetTanks;
    }

    @Override
    public List<ActionCommand> getCommands(float deltaTime) {
        Directions[] directions = Directions.values();
        Random random = new Random();
        int idx = random.nextInt(directions.length);
        List<ActionCommand> commands = new ArrayList<>();
        targetTanks.forEach(tank -> commands.add(new MoveCommand(tank, directions[idx].getDirection())));
        targetTanks.forEach(tank -> commands.add(new ShootCommand(tank)));
        return commands;
    }
}
