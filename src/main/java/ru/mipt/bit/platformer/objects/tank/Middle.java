package ru.mipt.bit.platformer.objects.tank;

import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.util.Direction;

public class Middle implements TankState {
    private Tank tank;

    public Middle(Tank tank) {
        this.tank = tank;
    }

    public Middle() {}

    @Override
    public void tryMove(Direction move) {
        tank.tryMoveWithSpeedScale(move, 2.0f);
    }

    @Override
    public void tryShoot() {
        // goes with hw7
    }

    @Override
    public void takeDamage(Bullet bullet) {
        if (bullet.getDamage() > 1) {
            // destroy tank // goes with hw7
        } else if (bullet.getDamage() == 1) {
            tank.changeState(new Heavy(tank));
        }
    }

    @Override
    public float getHealthNormalized() {
        return 2.0f / 3;
    }

    @Override
    public void setTank(Tank tank) {
        this.tank = tank;
    }
}
