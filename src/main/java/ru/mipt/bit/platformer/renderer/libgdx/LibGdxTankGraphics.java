package ru.mipt.bit.platformer.renderer.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.renderer.Graphics;

public class LibGdxTankGraphics implements Graphics {
    private final LibGdxRenderer renderer;
    private final Tank tank;

    public LibGdxTankGraphics(Tank tank, Batch batch) {
        renderer = new LibGdxRenderer(
                batch,
                new Texture("images/tank_blue.png"),
                0.0f
        );
        this.tank = tank;
    }

    @Override
    public void render() {
        this.renderer.setRotation(tank.getRotation());
        this.renderer.setCenter(tank.getPosition());
        this.renderer.render();
    }

    @Override
    public void delete() {
        this.renderer.dispose();
    }
}
