package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class TileUtils {
    private static GridPoint2 tileSize;
    private static Interpolation interpolation;

    public static void setTileSize(GridPoint2 tileSize) {
        TileUtils.tileSize = tileSize.cpy();
    }

    public static void setInterpolation(Interpolation interpolation) {
        TileUtils.interpolation = interpolation;
    }

    public static Vector2 calculateTileCenter(GridPoint2 tileCoordinates) {
        int tileWidth = tileSize.x;
        int tileHeight = tileSize.y;
        int tileBottomLeftCornerX = tileCoordinates.x * tileWidth;
        int tileBottomLeftCornerY = tileCoordinates.y * tileHeight;

        return new Rectangle()
                .setX(tileBottomLeftCornerX)
                .setY(tileBottomLeftCornerY)
                .setWidth(tileWidth)
                .setHeight(tileHeight)
                .getCenter(new Vector2());
    }

    public static Vector2 calculatePositionBetween(GridPoint2 fromTileCoordinates, GridPoint2 toTileCoordinates, float progress) {
        Vector2 fromTileCenter = calculateTileCenter(fromTileCoordinates);
        Vector2 toTileCenter = calculateTileCenter(toTileCoordinates);

        float intermediateX = interpolation.apply(fromTileCenter.x, toTileCenter.x, progress);
        float intermediateY = interpolation.apply(fromTileCenter.y, toTileCenter.y, progress);

        return new Vector2().set(intermediateX, intermediateY);
    }
}
