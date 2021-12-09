package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.util.TileUtils;

public class Obstacle implements Collider {
    private final Vector2 position;
    private final GridPoint2 gridPosition;

    public Obstacle (GridPoint2 gridPosition, TileUtils tileUtils) {
        this.gridPosition = gridPosition;
        this.position = tileUtils.calculateTileCenter(gridPosition);
    }

    public Vector2 getPosition() {
        return position;
    }

    public GridPoint2 getGridPosition() {
        return gridPosition;
    }

    @Override
    public boolean collides(GridPoint2 targetGridPoint) {
        return targetGridPoint.equals(gridPosition);
    }
}
