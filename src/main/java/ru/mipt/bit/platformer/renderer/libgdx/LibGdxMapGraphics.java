package ru.mipt.bit.platformer.renderer.libgdx;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import ru.mipt.bit.platformer.renderer.Graphics;
import ru.mipt.bit.platformer.util.GdxUtils;

public class LibGdxMapGraphics implements Graphics {
    private final MapRenderer mapRenderer;
    private final Batch batch;

    public LibGdxMapGraphics(TiledMap tiledMap, Batch batch) {
        TiledMapTileLayer tileLayer = GdxUtils.getSingleLayer(tiledMap);
        float viewWidth = tileLayer.getWidth() * tileLayer.getTileWidth();
        float viewHeight = tileLayer.getHeight() * tileLayer.getTileHeight();

        OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);
        mapRenderer.getViewBounds().set(0f, 0f, viewWidth, viewHeight);

        this.mapRenderer = mapRenderer;
        this.batch = batch;
    }

    @Override
    public void render() {
        if (batch.isDrawing()) batch.end();
        this.mapRenderer.render();
    }

    @Override
    public void delete() {}
}
