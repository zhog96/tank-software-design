package ru.mipt.bit.platformer.actors;

import ru.mipt.bit.platformer.input.DIRECTIONS;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.Random;

public class AIRandom implements Actor {
    private final Tank targetTank;

    public AIRandom(Tank targetTank) {
        this.targetTank = targetTank;
    }

    @Override
    public void act() {
        DIRECTIONS[] directions = DIRECTIONS.values();
        Random random = new Random();
        int idx = random.nextInt(directions.length);
        targetTank.tryMove(directions[idx].getDirection());
    }
}
