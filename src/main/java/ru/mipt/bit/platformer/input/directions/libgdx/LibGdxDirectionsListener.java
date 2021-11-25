package ru.mipt.bit.platformer.input.directions.libgdx;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.input.directions.Directions;
import ru.mipt.bit.platformer.input.directions.DirectionsListener;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class LibGdxDirectionsListener implements DirectionsListener {
    @Override
    public Directions pressed() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return Directions.UP;
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return Directions.LEFT;
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return Directions.DOWN;
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return Directions.RIGHT;
        }
        return null;
    }
}
