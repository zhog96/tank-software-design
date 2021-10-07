package ru.mipt.bit.platformer.renderer.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class LibGdxRenderer implements Disposable {
    // Решил не выделять отдельный интерфейс, просто общий код выделил, чтобы в двух других не было копипасты много
    private final Batch batch;
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle bounding;
    private float rotation;

    public void setCenter(Vector2 center) {
        this.bounding.setCenter(center);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public LibGdxRenderer(Batch batch, Texture texture, float rotation) {
        this.batch = batch;
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
        this.bounding = createBoundingRectangle(textureRegion);
        this.rotation = rotation;
    }

    private static Rectangle createBoundingRectangle(TextureRegion region) {
        return new Rectangle()
                .setWidth(region.getRegionWidth())
                .setHeight(region.getRegionHeight());
    }

    private static void drawTextureRegionUnscaled(Batch batch, TextureRegion region, Rectangle rectangle, float rotation) {
        int regionWidth = region.getRegionWidth();
        int regionHeight = region.getRegionHeight();
        float regionOriginX = regionWidth / 2f;
        float regionOriginY = regionHeight / 2f;
        batch.draw(region, rectangle.x, rectangle.y, regionOriginX, regionOriginY, regionWidth, regionHeight, 1f, 1f, rotation);
    }

    public void render() {
        if (!batch.isDrawing()) batch.begin();
        drawTextureRegionUnscaled(batch, textureRegion, bounding, rotation);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
