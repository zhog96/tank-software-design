package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.util.TileUtils;

public class Obstacle {
    private final Vector2 position;
    // Пока не до конца понятна логика столкновений у нас, так что использую положение в сетке
    private final GridPoint2 gridPosition;

    public Obstacle (GridPoint2 gridPosition) {
        this.gridPosition = gridPosition;
        this.position = TileUtils.calculateTileCenter(gridPosition);
    }

    public Vector2 getPosition() {
        return position;
    }

    public GridPoint2 getGridPosition() {
        return gridPosition;
    }

}
