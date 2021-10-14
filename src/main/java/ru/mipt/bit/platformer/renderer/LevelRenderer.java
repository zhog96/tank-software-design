package ru.mipt.bit.platformer.renderer;

import java.util.ArrayList;

public class LevelRenderer {
    private Graphics mapGraphics;
    private Graphics playerGraphics;
    private final ArrayList<Graphics> obstacleGraphicsList;

    public LevelRenderer() {
        obstacleGraphicsList = new ArrayList<>();
    }

    public void setMapGraphics(Graphics mapGraphics) {
        this.mapGraphics = mapGraphics;
    }

    public void setPlayerGraphics(Graphics playerGraphics) {
        this.playerGraphics = playerGraphics;
    }

    public void addObstacleGraphics(Graphics obstacleGraphics) {
        obstacleGraphicsList.add(obstacleGraphics);
    }

    public void levelRender() {
        mapGraphics.render();
        playerGraphics.render();
        for (var obstacle : obstacleGraphicsList) {
            obstacle.render();
        }
    }

    // Поскольку тут нельзя трогать gdx, это позовет dispose но уже в глубине пакета libgdx
    // Кажется не зависимо от метода рисования такую функцию иметь надо
    public void delete() {
        mapGraphics.delete();
        playerGraphics.delete();
        for (var obstacle : obstacleGraphicsList) {
            obstacle.delete();
        }
    }
}
