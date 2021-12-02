package ru.mipt.bit.platformer.input.shootlistener.libgdx;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.input.shootlistener.ShootListener;

import static com.badlogic.gdx.Input.Keys.SPACE;

public class LibGdxShootListener implements ShootListener {
    @Override
    public boolean shootPressed() {
        return Gdx.input.isKeyPressed(SPACE);
    }
}
