package ru.mipt.bit.platformer.renderer.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.Player;
import ru.mipt.bit.platformer.renderer.Graphics;

public class LibGdxPlayerGraphics implements Graphics {
    private final LibGdxRenderer renderer;
    private final Player player;

    public LibGdxPlayerGraphics(Player player, Batch batch) {
        renderer = new LibGdxRenderer(
                batch,
                new Texture("images/tank_blue.png"),
                0.0f
        );
        this.player = player;
    }

    @Override
    public void render() {
        this.renderer.setRotation(player.getRotation());
        this.renderer.setCenter(player.getPosition());
        this.renderer.render();
    }

    @Override
    public void delete() {
        this.renderer.dispose();
    }
}
