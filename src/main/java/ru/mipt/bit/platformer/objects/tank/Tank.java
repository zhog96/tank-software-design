package ru.mipt.bit.platformer.objects.tank;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.ColliderManager;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.Collider;
import ru.mipt.bit.platformer.objects.guns.Gun;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.Movement;
import ru.mipt.bit.platformer.util.TileUtils;

public class Tank implements Collider {
    private final Vector2 position;
    private final GridPoint2 gridPosition;
    private float rotation;
    private Movement movement;
    private final float speed;
    private final ColliderManager colliderManager;
    private final TileUtils tileUtils;
    private TankState tankState;
    private Gun gun;

    public Tank(GridPoint2 gridPosition, float speed, ColliderManager colliderManager, TileUtils tileUtils, TankState initialTankState) {
        this.gridPosition = gridPosition;
        this.position = tileUtils.calculateTileCenter(gridPosition);
        this.rotation = 0.0f;
        this.movement = new Movement(gridPosition, gridPosition, speed, tileUtils);
        this.speed = speed;
        this.colliderManager = colliderManager;
        this.tileUtils = tileUtils;
        tankState = initialTankState;
        tankState.setTank(this);
    }

    public void addGun(Gun gun) {
        this.gun = gun;
    }

    public Vector2 getPosition() {
        return position;
    }

    public GridPoint2 getGridPosition() {
        return gridPosition;
    }

    public float getRotation() {
        return rotation;
    }

    public ColliderManager getColliderManager() {
        return colliderManager;
    }

    public TileUtils getTileUtils() {
        return tileUtils;
    }

    public void tryMove(Direction move) {
        tankState.tryMove(move);
    }

    protected void tryMoveWithSpeedScale(Direction move, float speedScale) {
        if (movement.finishedMoving()) {
            if (colliderManager.isTakenBy(gridPosition.cpy().add(move.getDeltaCoordinate())) == null) {
                GridPoint2 prevGridPosition = gridPosition.cpy();
                gridPosition.add(move.getDeltaCoordinate());
                movement = new Movement(prevGridPosition, gridPosition, speedScale * speed, tileUtils);
            }
            rotation = move.getAngle();
        }
    }

    public void tryShoot() {
        if (tankState.canShoot() && gun != null) {
            gun.tryShoot();
        }
    }

    public void takeDamage(Bullet bullet, Level level) {
        tankState.takeDamage(bullet, level);
    }

    public float getHealthNormalized() {
        return tankState.getHealthNormalized();
    }

    public void update(float deltaTime) {
        movement.update(deltaTime);
        position.set(movement.calculatePosition());
        if (gun != null) {
            gun.update(deltaTime);
        }
    }

    @Override
    public boolean collides(GridPoint2 targetGridPoint) {
        return movement.getTrajectoryPoints().contains(targetGridPoint);
    }

    public void changeState(TankState tankState) {
        this.tankState = tankState;
    }
}
