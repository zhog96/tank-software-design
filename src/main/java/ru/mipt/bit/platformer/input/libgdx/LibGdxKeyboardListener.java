package ru.mipt.bit.platformer.input.libgdx;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.input.KeyboardListener;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class LibGdxKeyboardListener implements KeyboardListener {
    @Override
    public INPUTS pressed() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return INPUTS.UP;
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return INPUTS.LEFT;
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return INPUTS.DOWN;
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return INPUTS.RIGHT;
        }
        return null;
    }
}
