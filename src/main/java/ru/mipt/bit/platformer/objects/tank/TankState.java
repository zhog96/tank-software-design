package ru.mipt.bit.platformer.objects.tank;

import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.util.Direction;

public interface TankState {
    void tryMove(Direction move);
    void tryShoot();
    void takeDamage(Bullet bullet);
    float getHealthNormalized();
    void setTank(Tank tank);
}
