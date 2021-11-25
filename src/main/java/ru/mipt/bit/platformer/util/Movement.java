package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.List;

public class Movement {
    private final MovementProgress movementProgress;
    private final GridPoint2 toGridPosition;
    private final GridPoint2 fromGridPosition;
    private final TileUtils tileUtils;


    public Movement(GridPoint2 fromGridPosition, GridPoint2 toGridPosition, float speed, TileUtils tileUtils) {
        this.toGridPosition = toGridPosition;
        this.fromGridPosition = fromGridPosition;
        this.movementProgress = new MovementProgress(speed);
        this.tileUtils = tileUtils;
    }

    public void update(float deltaTime) {
        movementProgress.update(deltaTime);
        if (movementProgress.finishedMoving()) {
            fromGridPosition.set(toGridPosition);
        }
    }

    public boolean finishedMoving() {
        return toGridPosition.equals(fromGridPosition) || movementProgress.finishedMoving();
    }

    public Vector2 calculatePosition() {
        return tileUtils.calculatePositionBetween(fromGridPosition, toGridPosition, movementProgress.getProgress());
    }

    public List<GridPoint2> getTrajectoryPoints() {
        return Arrays.asList(fromGridPosition.cpy(), toGridPosition.cpy());
    }
}
