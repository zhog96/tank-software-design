package ru.mipt.bit.platformer.renderer;

import java.util.ArrayList;

public class LevelRenderer {
    private MapGraphics mapGraphics;
    private PlayerGraphics playerGraphics;
    private final ArrayList<ObstacleGraphics> obstacleGraphicsList;

    public LevelRenderer() {
        obstacleGraphicsList = new ArrayList<>();
    }

    public void setMapGraphics(MapGraphics mapGraphics) {
        this.mapGraphics = mapGraphics;
    }

    public void setPlayerGraphics(PlayerGraphics playerGraphics) {
        this.playerGraphics = playerGraphics;
    }

    public void addObstacleGraphics(ObstacleGraphics obstacleGraphics) {
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
