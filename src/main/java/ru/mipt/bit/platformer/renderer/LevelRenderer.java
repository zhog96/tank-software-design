package ru.mipt.bit.platformer.renderer;

import java.util.ArrayList;
import java.util.List;

public class LevelRenderer {
    private Graphics mapGraphics;
    private final List<Graphics> graphicsList;

    public LevelRenderer() {
        graphicsList = new ArrayList<>();
    }

    public void setMapGraphics(Graphics mapGraphics) {
        this.mapGraphics = mapGraphics;
    }

    public void addGraphics(Graphics obstacleGraphics) {
        graphicsList.add(obstacleGraphics);
    }

    public void levelRender() {
        mapGraphics.render();
        for (var graphics : graphicsList) {
            graphics.render();
        }
    }

    public void delete() {
        mapGraphics.delete();
        for (var graphics : graphicsList) {
            graphics.delete();
        }
    }
}
