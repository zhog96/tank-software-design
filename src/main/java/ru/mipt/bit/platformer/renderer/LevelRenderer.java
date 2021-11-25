package ru.mipt.bit.platformer.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.input.togglelistener.ToggleListener;

import java.util.ArrayList;
import java.util.List;

public class LevelRenderer {
    private final Batch batch;
    private final Graphics mapGraphics;
    private final List<Graphics> graphicsList;
    private final ToggleListener toggleListener;

    public LevelRenderer(Graphics mapGraphics, Batch batch, ToggleListener toggleListener) {
        this.mapGraphics = mapGraphics;
        this.batch = batch;
        this.toggleListener = toggleListener;
        graphicsList = new ArrayList<>();
    }

    public void addGraphics(Graphics obstacleGraphics) {
        graphicsList.add(obstacleGraphics);
    }

    public void levelRender() {
        toggleListener.update();
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
