package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.input.KeyboardListener;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.Movement;
import ru.mipt.bit.platformer.util.TileUtils;

public class Player {
    // Попадание возможно захотим по фактическому положению так что positions нужно
    private final Vector2 position;
    private final GridPoint2 gridPosition;
    private float rotation;
    private final KeyboardListener keyboardListener;
    private Movement movement;
    private final float speed;

    public Player(GridPoint2 gridPosition, float speed, KeyboardListener keyboardListener) {
        this.gridPosition = gridPosition;
        this.position = TileUtils.calculateTileCenter(gridPosition);
        this.keyboardListener = keyboardListener;
        this.rotation = 0.0f;
        this.movement = new Movement(gridPosition, gridPosition, speed);
        this.speed = speed;
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

    private void tryMove(Direction move) {
        if (movement.finishedMoving()) {
            if (ColliderManager.canMove(this, move.getDeltaCoordinate())) {
                GridPoint2 prevGridPosition = gridPosition.cpy();
                gridPosition.add(move.getDeltaCoordinate());
                movement = new Movement(prevGridPosition, gridPosition, speed);
            }
            rotation = move.getAngle();
        }
    }

    public void update(float deltaTime) {
        if (keyboardListener.pressed() == KeyboardListener.INPUTS.UP) {
            tryMove(new Direction(0, 1));
        }
        if (keyboardListener.pressed() == KeyboardListener.INPUTS.DOWN) {
            tryMove(new Direction(0, -1));
        }
        if (keyboardListener.pressed() == KeyboardListener.INPUTS.RIGHT) {
            tryMove(new Direction(1, 0));
        }
        if (keyboardListener.pressed() == KeyboardListener.INPUTS.LEFT) {
            tryMove(new Direction(-1, 0));
        }
        movement.update(deltaTime);
        position.set(movement.calculatePosition());
    }
}
