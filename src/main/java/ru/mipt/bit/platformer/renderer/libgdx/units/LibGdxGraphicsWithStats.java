package ru.mipt.bit.platformer.renderer.libgdx.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.input.togglelistener.ToggleListener;
import ru.mipt.bit.platformer.renderer.UnitGraphics;
import ru.mipt.bit.platformer.util.GdxUtils;
import ru.mipt.bit.platformer.util.TileUtils;

/**
 * Адаптер
 */

public class LibGdxGraphicsWithStats implements UnitGraphics {
    private final UnitGraphics unit;
    private final Batch batch;
    private final TileUtils tileUtils;
    private final ToggleListener toggleListener;
    private final TextureRegion healthTextureRegion;
    private final Rectangle healthBounding;

    public LibGdxGraphicsWithStats(UnitGraphics unit, ToggleListener toggleListener, Batch batch, TileUtils tileUtils) {
        this.unit = unit;
        this.tileUtils = tileUtils;
        this.batch = batch;
        this.toggleListener = toggleListener;
        this.healthTextureRegion = new TextureRegion(new Texture("images/health_bar.png"));
        this.healthBounding = GdxUtils.createBoundingRectangle(healthTextureRegion);
    }

    @Override
    public void render() {
        unit.render();
        if (toggleListener.toggled()) {
            Vector2 center = new Vector2();
            unit.getUnitBounding().getCenter(center);
            GridPoint2 tileSize = tileUtils.getTileSize();
            healthBounding.setCenter(
                    new Vector2(
                            center.x + tileSize.x / 2.0f - healthBounding.getWidth() / 2,
                            center.y + tileSize.y / 2.0f - healthBounding.getHeight() / 2
                    )
            );
            healthTextureRegion.setV2(unit.getHealth());
            GdxUtils.drawTextureRegionUnscaled(batch, healthTextureRegion, healthBounding, 0.0f);
        }
    }

    @Override
    public void delete() {
        unit.delete();
    }

    @Override
    public Rectangle getUnitBounding() {
        return unit.getUnitBounding();
    }

    @Override
    public float getHealth() {
        return unit.getHealth();
    }
}
