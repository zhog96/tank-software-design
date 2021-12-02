package ru.mipt.bit.platformer.renderer.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.renderer.Graphics;
import ru.mipt.bit.platformer.util.GdxUtils;

public class LibGdxObstacleGraphics implements Graphics {
    private final Rectangle bounding;
    private final Batch batch;
    private final TextureRegion textureRegion;
    private final Obstacle obstacle;

    public LibGdxObstacleGraphics(Obstacle obstacle, Batch batch) {
        this.textureRegion = new TextureRegion(new Texture("images/greenTree.png"));
        this.bounding = GdxUtils.createBoundingRectangle(textureRegion);
        this.obstacle = obstacle;
        this.batch = batch;
    }

    @Override
    public void render() {
        bounding.setCenter(obstacle.getPosition());
        GdxUtils.drawTextureRegionUnscaled(batch, textureRegion, bounding, 0.0f);
    }

    @Override
    public void delete() {
        textureRegion.getTexture().dispose();
    }
}
