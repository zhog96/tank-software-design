package ru.mipt.bit.platformer.actors;

import ru.mipt.bit.platformer.input.Directions;
import ru.mipt.bit.platformer.input.keyboard.KeyboardListener;
import ru.mipt.bit.platformer.objects.Tank;

public class Player implements Actor {
    private final Tank targetTank;
    private final KeyboardListener keyboardListener;

    public Player(Tank targetTank, KeyboardListener keyboardListener) {
        this.targetTank = targetTank;
        this.keyboardListener = keyboardListener;
    }

    @Override
    public void act() {
        Directions direction = keyboardListener.pressed();
        if (direction != null) {
            targetTank.tryMove(direction.getDirection());
        }
    }
}
