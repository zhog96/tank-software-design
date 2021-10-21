package ru.mipt.bit.platformer.input.keyboard.libgdx;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.input.DIRECTIONS;
import ru.mipt.bit.platformer.input.keyboard.KeyboardListener;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class LibGdxKeyboardListener implements KeyboardListener {
    @Override
    public DIRECTIONS pressed() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return DIRECTIONS.UP;
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return DIRECTIONS.LEFT;
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return DIRECTIONS.DOWN;
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return DIRECTIONS.RIGHT;
        }
        return null;
    }
}
