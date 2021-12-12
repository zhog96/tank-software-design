package ru.mipt.bit.platformer.controllers.commands;

import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.util.Direction;

public class MoveCommand implements  ActionCommand {
    private final Tank targetTank;
    private final Direction direction;

    public MoveCommand(Tank targetTank, Direction direction) {
        this.targetTank = targetTank;
        this.direction = direction;
    }

    @Override
    public void act() {
        targetTank.tryMove(direction);
    }
}
