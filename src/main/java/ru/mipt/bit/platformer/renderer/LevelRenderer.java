package ru.mipt.bit.platformer.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.List;

public class LevelRenderer {
    private final Batch batch;
    private final Graphics mapGraphics;
    private final List<Graphics> graphicsList;

    public LevelRenderer(Graphics mapGraphics, Batch batch) {
        this.mapGraphics = mapGraphics;
        this.batch = batch;
        graphicsList = new ArrayList<>();
    }

    public void addGraphics(Graphics obstacleGraphics) {
        graphicsList.add(obstacleGraphics);
    }

    public void levelRender() {
        mapGraphics.render();
        batch.begin();
        for (var graphics : graphicsList) {
            graphics.render();
        }
        batch.end();
    }

    public void delete() {
        mapGraphics.delete();
        for (var graphics : graphicsList) {
            graphics.delete();
        }
    }
}
