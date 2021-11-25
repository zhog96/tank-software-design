package ru.mipt.bit.platformer.objects.tank;

import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.util.Direction;

public class Light implements TankState {
    private Tank tank;

    public Light(Tank tank) {
        this.tank = tank;
    }

    public Light() {}

    @Override
    public void tryMove(Direction move) {
        tank.tryMoveWithSpeedScale(move, 1.0f);
    }

    @Override
    public void tryShoot() {
        // goes with hw7
    }

    @Override
    public void takeDamage(Bullet bullet) {
        if (bullet.getDamage() > 2) {
            // destroy tank // goes with hw7
        } else if (bullet.getDamage() == 2) {
            tank.changeState(new Heavy(tank));
        } else if (bullet.getDamage() == 1) {
            tank.changeState(new Middle(tank));
        }
    }

    @Override
    public float getHealthNormalized() {
        return 1.0f;
    }

    @Override
    public void setTank(Tank tank) {
        this.tank = tank;
    }
}
