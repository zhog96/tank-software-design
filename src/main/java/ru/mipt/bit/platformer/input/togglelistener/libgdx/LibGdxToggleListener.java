package ru.mipt.bit.platformer.input.togglelistener.libgdx;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.input.togglelistener.ToggleListener;

import static com.badlogic.gdx.Input.Keys.L;

/**
 * Адаптер
 */

public class LibGdxToggleListener implements ToggleListener {
    private boolean toggle = false;
    private boolean waitRelaxation = false;

    @Override
    public boolean toggled() {
        return toggle;
    }

    @Override
    public void update() {
        if (!Gdx.input.isKeyPressed(L)) {
            waitRelaxation = false;
        }
        if (Gdx.input.isKeyPressed(L) && !waitRelaxation) {
            toggle = !toggle;
            waitRelaxation = true;
        }
    }
}
