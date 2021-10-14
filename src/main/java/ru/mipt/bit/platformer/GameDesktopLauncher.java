package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.input.libgdx.LibGdxKeyboardListener;
import ru.mipt.bit.platformer.level.Level;
// import ru.mipt.bit.platformer.level.LevelFromFileGenerator;
import ru.mipt.bit.platformer.level.LevelRandomGenerator;
import ru.mipt.bit.platformer.renderer.LevelRenderer;
import ru.mipt.bit.platformer.renderer.libgdx.LibGdxMapGraphics;
import ru.mipt.bit.platformer.renderer.libgdx.LibGdxObstacleGraphics;
import ru.mipt.bit.platformer.renderer.libgdx.LibGdxPlayerGraphics;
import ru.mipt.bit.platformer.util.TileUtils;
import ru.mipt.bit.platformer.util.GdxUtils;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private TiledMap levelBackground;
    private Player player;
    private ArrayList<Obstacle> obstacles;
    private LevelRenderer levelRenderer;

    private void initLevelRenderer() {
        batch = new SpriteBatch();
        levelRenderer = new LevelRenderer();
        levelRenderer.setMapGraphics(new LibGdxMapGraphics(levelBackground, batch));
        levelRenderer.setPlayerGraphics(new LibGdxPlayerGraphics(player, batch));
        for (var obstacle : obstacles) {
            levelRenderer.addObstacleGraphics(new LibGdxObstacleGraphics(obstacle, batch));
        }
    }

    private void initCollider() {
        for (var obstacle : obstacles) {
            ColliderManager.addObstacle(obstacle);
        }
    }

    private void initLevel() {
        // Level level = new Level(new LevelFromFileGenerator("src/main/resources/level.txt"));
        Level level = new Level(new LevelRandomGenerator(10, 7));
        level.initObjects();
        player = level.getPlayer();
        player.setKeyboardListener(new LibGdxKeyboardListener());
        obstacles = level.getObstacles();
    }

    private void initMap() {
        levelBackground = new TmxMapLoader().load("levelBackground.tmx");
        TiledMapTileLayer groundLayer = GdxUtils.getSingleLayer(levelBackground);
        TileUtils.setTileSize(new GridPoint2(groundLayer.getTileWidth(), groundLayer.getTileHeight()));
        TileUtils.setInterpolation(Interpolation.smooth);
    }

    @Override
    public void create() {
        initMap();
        initLevel();
        initCollider();
        initLevelRenderer();
    }

    private void cleanScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        player.update(deltaTime);
    }

    @Override
    public void render() {
        update();
        cleanScreen();
        levelRenderer.levelRender();
        if (batch.isDrawing()) batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        levelRenderer.delete();
        levelBackground.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
