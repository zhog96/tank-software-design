package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.MovementProgress;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;

import static com.badlogic.gdx.Input.Keys.*;

public class Player extends GameObject {
    private static final float MOVEMENT_SPEED = 0.4f;

    private final TileMovement tileMovement;
    private final MovementProgress movementProgress;
    private final GridPoint2 previousCoordinates;

    public Player(TiledMapTileLayer groundLayer, Texture texture, GridPoint2 coordinates, float rotation, TileMovement tileMovement) {
        super(groundLayer, texture, coordinates, rotation);
        this.tileMovement = tileMovement;
        movementProgress = new MovementProgress(MOVEMENT_SPEED);
        previousCoordinates = new GridPoint2(coordinates);

    }

    private void checkAndSetupMove(Direction direction, ArrayList<GameObject> gameObjects) {
        if (movementProgress.finishedMoving()) {
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
            movementProgress.reset();
        }
    }

    public void move(float deltaTime, ArrayList<GameObject> gameObjects) {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            checkAndSetupMove(new Direction(0, 1), gameObjects);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            checkAndSetupMove(new Direction(-1, 0), gameObjects);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            checkAndSetupMove(new Direction(0, -1), gameObjects);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            checkAndSetupMove(new Direction(1, 0), gameObjects);
        }

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(this.getBounding(), previousCoordinates, coordinates, movementProgress.getProgress());

        movementProgress.update(deltaTime);
        if (movementProgress.finishedMoving()) {
            // record that the player has reached his/her destination
            previousCoordinates.set(coordinates);
        }
    }
}
