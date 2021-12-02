package ru.mipt.bit.platformer.objects.tank;

import ru.mipt.bit.platformer.level.Level;
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
    public boolean canShoot() {
        return false;
    }

    @Override
    public void takeDamage(Bullet bullet, Level level) {
        if (bullet.getDamage() > 0) {
            level.removeTank(tank);
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
