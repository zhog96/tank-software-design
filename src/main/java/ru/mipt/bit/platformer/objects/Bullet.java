package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.ColliderManager;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.Movement;
import ru.mipt.bit.platformer.util.TileUtils;

public class Bullet {
    private final int damage;
    private final Vector2 position;
    private final GridPoint2 gridPosition;
    private final float rotation;
    private final float speed;
    private Movement movement;
    private final ColliderManager colliderManager;
    private final TileUtils tileUtils;
    private final Direction direction;
    private final Level level;

    public Bullet(GridPoint2 gridPosition, Direction direction, int damage, float speed, ColliderManager colliderManager, TileUtils tileUtils, Level level) {
        this.damage = damage;
        this.gridPosition = gridPosition;
        this.position = tileUtils.calculateTileCenter(gridPosition);
        this.rotation = direction.getAngle();
        this.speed = speed;
        this.colliderManager = colliderManager;
        this.tileUtils = tileUtils;
        this.direction = direction;
        this.level = level;
    }

    public int getDamage() {
        return damage;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public void checkCollisions() {
        Collider collider = colliderManager.isTakenBy(gridPosition);
        if (collider != null) {
            level.removeBullet(this);
        }
        if (collider instanceof Tank) {
            ((Tank) collider).takeDamage(this, level);
        }
    }

    public void update(float deltaTime) {
        if (movement != null) {
            movement.update(deltaTime);
        }
        if (movement == null || movement.halfWayMoving()) {
            checkCollisions();
        }
        if (movement == null || movement.finishedMoving()) {
            GridPoint2 prevPosition = gridPosition.cpy();
            gridPosition.add(direction.getDeltaCoordinate());
            movement = new Movement(prevPosition, gridPosition, speed, tileUtils);
        }
        if (movement != null) {
            position.set(movement.calculatePosition());
        }
    }
}
