package ru.mipt.bit.platformer.objects.tank;

import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.util.Direction;

public interface TankState {
    void tryMove(Direction move);
    boolean canShoot();
    void takeDamage(Bullet bullet, Level levelObjects);
    float getHealthNormalized();
    void setTank(Tank tank);
}
