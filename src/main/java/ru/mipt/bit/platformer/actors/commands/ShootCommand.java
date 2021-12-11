package ru.mipt.bit.platformer.actors.commands;

import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.util.Direction;

public class ShootCommand implements  ActionCommand {
    private final Tank targetTank;

    public ShootCommand(Tank targetTank) {
        this.targetTank = targetTank;
    }

    @Override
    public void act() {
        targetTank.tryShoot();
    }
}
