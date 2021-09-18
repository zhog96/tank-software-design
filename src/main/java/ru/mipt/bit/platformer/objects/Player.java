package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Player extends GameObject {
    private static final float MOVEMENT_SPEED = 0.4f;

    private final TileMovement tileMovement;
    private final GridPoint2 previousCoordinates;
    private float movementProgress = 1f;

    public Player(TiledMapTileLayer groundLayer, Texture texture, GridPoint2 coordinates, float rotation, TileMovement tileMovement) {
        super(groundLayer, texture, coordinates, rotation);
        this.tileMovement = tileMovement;
        this.previousCoordinates = new GridPoint2(coordinates);
    }

    private void checkAndMove(Direction direction, ArrayList<GameObject> gameObjects) {
        if (isEqual(movementProgress, 1f)) {
            GridPoint2 estimatedCoordinates = new GridPoint2(coordinates);
            estimatedCoordinates.add(direction.getDeltaCoordinate());
            this.rotation = direction.getAngle();
            // check potential player destination for collision with obstacles
            for (GameObject anotherObject : gameObjects) {
                if (!(anotherObject instanceof Obstacle) || this.equals(anotherObject)) {
                    continue;
                }
                if (anotherObject.coordinates.equals(estimatedCoordinates)) {
                    return;
                }
            }
            coordinates = estimatedCoordinates;
            movementProgress = 0f;
        }
    }

    public void move(float deltaTime, ArrayList<GameObject> gameObjects) {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            checkAndMove(new Direction(0, 1), gameObjects);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            checkAndMove(new Direction(-1, 0), gameObjects);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            checkAndMove(new Direction(0, -1), gameObjects);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            checkAndMove(new Direction(1, 0), gameObjects);
        }

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(this.getBounding(), previousCoordinates, coordinates, movementProgress);

        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(movementProgress, 1f)) {
            // record that the player has reached his/her destination
            previousCoordinates.set(coordinates);
        }
    }
}
