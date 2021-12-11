package ru.mipt.bit.platformer.objects.tank;

import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.util.Direction;

public class LightDamagedTank implements TankState {
    private Tank tank;

    public LightDamagedTank(Tank tank) {
        this.tank = tank;
    }

    public LightDamagedTank() {}

    @Override
    public void tryMove(Direction move) {
        tank.tryMoveWithSpeedScale(move, 1.0f);
    }

    @Override
    public boolean canShoot() {
        return true;
    }

    @Override
    public void takeDamage(Bullet bullet, Level level) {
        if (bullet.getDamage() > 2) {
            level.removeTank(tank);
        } else if (bullet.getDamage() == 2) {
            tank.changeState(new HeavyDamagedTank(tank));
        } else if (bullet.getDamage() == 1) {
            tank.changeState(new MiddleDamagedTank(tank));
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
