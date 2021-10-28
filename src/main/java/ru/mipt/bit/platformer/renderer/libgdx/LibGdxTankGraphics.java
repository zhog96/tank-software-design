package ru.mipt.bit.platformer.renderer.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.renderer.Graphics;
import ru.mipt.bit.platformer.util.GdxUtils;

public class LibGdxTankGraphics implements Graphics {
    private final Rectangle bounding;
    private final Batch batch;
    private final TextureRegion textureRegion;
    private final Tank tank;

    public LibGdxTankGraphics(Tank tank, Batch batch) {
        this.textureRegion = new TextureRegion(new Texture("images/tank_blue.png"));
        this.bounding = GdxUtils.createBoundingRectangle(textureRegion);
        this.tank = tank;
        this.batch = batch;
    }

    @Override
    public void render() {
        bounding.setCenter(tank.getPosition());
        GdxUtils.drawTextureRegionUnscaled(batch, textureRegion, bounding, tank.getRotation());
    }

    @Override
    public void delete() {
        textureRegion.getTexture().dispose();
    }
}
