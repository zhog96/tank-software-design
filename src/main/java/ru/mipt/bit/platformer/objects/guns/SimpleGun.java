package ru.mipt.bit.platformer.objects.guns;

import ru.mipt.bit.platformer.input.directions.Directions;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.util.Direction;

public class SimpleGun implements Gun {
    private final float coolDown;
    private final float bulletSpeed;
    private final int damage;
    private float timeCooled;
    private final Level level;
    private final Tank tank;

    public SimpleGun(float coolDown, int damage, float bulletSpeed, Level level, Tank tank) {
        this.coolDown = coolDown;
        timeCooled = coolDown;
        this.level = level;
        this.tank = tank;
        this.damage = damage;
        this.bulletSpeed = bulletSpeed;
    }

    @Override
    public void update(float deltaTime) {
        timeCooled += deltaTime;
    }

    @Override
    public void tryShoot() {
        if (timeCooled > coolDown) {
            timeCooled = 0;
            Direction direction = Directions.directionByAngle(tank.getRotation());
            if (direction != null) {
                level.addBullet(new Bullet(
                        tank.getGridPosition().cpy().add(direction.getDeltaCoordinate()),
                        direction,
                        damage,
                        bulletSpeed,
                        tank.getColliderManager(),
                        tank.getTileUtils(),
                        level)
                );
            }
        }
    }
}
