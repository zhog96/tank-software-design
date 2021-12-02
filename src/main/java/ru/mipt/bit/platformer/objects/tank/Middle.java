package ru.mipt.bit.platformer.objects.tank;

import ru.mipt.bit.platformer.level.Level;
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
    public boolean canShoot() {
        return true;
    }

    @Override
    public void takeDamage(Bullet bullet, Level level) {
        if (bullet.getDamage() > 1) {
            level.removeTank(tank);
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
