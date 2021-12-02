package ru.mipt.bit.platformer.renderer.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.renderer.Graphics;
import ru.mipt.bit.platformer.util.GdxUtils;

/**
 * Порт
 */

public class LibGdxBulletGraphics implements Graphics {
    private final Rectangle bounding;
    private final Batch batch;
    private final TextureRegion textureRegion;
    private final Bullet bullet;

    public LibGdxBulletGraphics(Bullet bullet, Batch batch) {
        this.textureRegion = new TextureRegion(new Texture("images/bullet.png"));
        this.bounding = GdxUtils.createBoundingRectangle(textureRegion);
        this.bullet = bullet;
        this.batch = batch;
    }

    @Override
    public void render() {
        bounding.setCenter(bullet.getPosition());
        GdxUtils.drawTextureRegionUnscaled(batch, textureRegion, bounding, bullet.getRotation());
    }

    @Override
    public void delete() {
        textureRegion.getTexture().dispose();
    }
}
