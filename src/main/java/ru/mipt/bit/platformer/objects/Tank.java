package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.ColliderManager;
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

    public Tank(GridPoint2 gridPosition, float speed, ColliderManager colliderManager, TileUtils tileUtils) {
        this.gridPosition = gridPosition;
        this.position = tileUtils.calculateTileCenter(gridPosition);
        this.rotation = 0.0f;
        this.movement = new Movement(gridPosition, gridPosition, speed, tileUtils);
        this.speed = speed;
        this.colliderManager = colliderManager;
        this.tileUtils = tileUtils;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public void tryMove(Direction move) {
        if (movement.finishedMoving()) {
            if (colliderManager.isFree(gridPosition.cpy().add(move.getDeltaCoordinate()))) {
                GridPoint2 prevGridPosition = gridPosition.cpy();
                gridPosition.add(move.getDeltaCoordinate());
                movement = new Movement(prevGridPosition, gridPosition, speed, tileUtils);
            }
            rotation = move.getAngle();
        }
    }

    public void update(float deltaTime) {
        movement.update(deltaTime);
        position.set(movement.calculatePosition());
    }

    @Override
    public boolean collides(GridPoint2 targetGridPoint) {
        return movement.getTrajectoryPoints().contains(targetGridPoint);
    }
}
