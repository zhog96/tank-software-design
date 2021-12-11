package ru.mipt.bit.platformer.controllers.commands;

import ru.mipt.bit.platformer.objects.tank.Tank;

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
