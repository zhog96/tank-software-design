package ru.mipt.bit.platformer.renderer.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.renderer.Graphics;

public class LibGdxObstacleGraphics implements Graphics {
    private final LibGdxRenderer renderer;
    private final Obstacle obstacle;

    public LibGdxObstacleGraphics(Obstacle obstacle, Batch batch) {
        renderer = new LibGdxRenderer(
                batch,
                // Параметризовать path все равно бессмысленно, потому что потом может будет анимация у куста
                new Texture("images/greenTree.png"),
                0.0f
        );
        this.obstacle = obstacle;
    }

    @Override
    public void render() {
        this.renderer.setCenter(obstacle.getPosition());
        this.renderer.render();
    }

    @Override
    public void delete() {
        this.renderer.dispose();
    }
}
