package ru.mipt.bit.platformer.actors;

import ru.mipt.bit.platformer.input.directions.Directions;
import ru.mipt.bit.platformer.input.directions.DirectionsListener;
import ru.mipt.bit.platformer.input.shootlistener.ShootListener;
import ru.mipt.bit.platformer.objects.tank.Tank;

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
    public void act() {
        Directions direction = directionsListener.pressed();
        if (direction != null) {
            targetTank.tryMove(direction.getDirection());
        }
        if (shootListener.shootPressed()) {
            targetTank.tryShoot();
        }
    }
}
