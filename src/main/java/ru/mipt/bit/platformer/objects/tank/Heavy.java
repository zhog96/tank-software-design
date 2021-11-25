package ru.mipt.bit.platformer.objects.tank;

import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.util.Direction;

public class Heavy implements TankState {
    private Tank tank;

    public Heavy(Tank tank) {
        this.tank = tank;
    }

    public Heavy() {}

    @Override
    public void tryMove(Direction move) {
        tank.tryMoveWithSpeedScale(move, 3.0f);
    }

    @Override
    public void tryShoot() {}

    @Override
    public void takeDamage(Bullet bullet) {
        if (bullet.getDamage() > 0) {
            // destroy tank // goes with hw7
        }
    }

    @Override
    public float getHealthNormalized() {
        return 1.0f / 3;
    }

    @Override
    public void setTank(Tank tank) {
        this.tank = tank;
    }
}
