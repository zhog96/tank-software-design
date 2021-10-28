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

import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.actors.AIAwesome;
import ru.mipt.bit.platformer.actors.Actor;
import ru.mipt.bit.platformer.actors.Player;
import ru.mipt.bit.platformer.input.keyboard.libgdx.LibGdxKeyboardListener;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.level.LevelRandomGenerator;
import ru.mipt.bit.platformer.renderer.LevelRenderer;
import ru.mipt.bit.platformer.renderer.libgdx.LibGdxMapGraphics;
import ru.mipt.bit.platformer.renderer.libgdx.LibGdxObstacleGraphics;
import ru.mipt.bit.platformer.renderer.libgdx.LibGdxTankGraphics;
import ru.mipt.bit.platformer.util.TileUtils;
import ru.mipt.bit.platformer.util.GdxUtils;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private TiledMap levelBackground;
    private Level level;
    private LevelRenderer levelRenderer;
    private ColliderManager colliderManager;
    private TileUtils tileUtils;
    private final List<Actor> actors = new ArrayList<>();

    private void initLevelRenderer() {
        batch = new SpriteBatch();
        levelRenderer = new LevelRenderer(new LibGdxMapGraphics(levelBackground, batch), batch);
        if (level.getPlayerTank() != null) {
            levelRenderer.addGraphics(new LibGdxTankGraphics(level.getPlayerTank(), batch));
        }
        for (var tank : level.getEnemyTanks()) {
            levelRenderer.addGraphics(new LibGdxTankGraphics(tank, batch));
        }
        for (var obstacle : level.getObstacles()) {
            levelRenderer.addGraphics(new LibGdxObstacleGraphics(obstacle, batch));
        }
    }

    private void initColliders() {
        if (level.getPlayerTank() != null) {
            colliderManager.addCollider(level.getPlayerTank());
        }
        for (var tank : level.getEnemyTanks()) {
            colliderManager.addCollider(tank);
        }
        for (var obstacle : level.getObstacles()) {
            colliderManager.addCollider(obstacle);
        }
    }

    private void initLevel() {
        level = new Level(new LevelRandomGenerator(10, 8));
        colliderManager = new ColliderManager();
        level.initObjects(colliderManager, tileUtils);
    }

    private void initMap() {
        levelBackground = new TmxMapLoader().load("levelBackground.tmx");
        TiledMapTileLayer groundLayer = GdxUtils.getSingleLayer(levelBackground);
        tileUtils = new TileUtils(new GridPoint2(groundLayer.getTileWidth(), groundLayer.getTileHeight()));
    }

    private void initActors() {
        if (level.getPlayerTank() != null) {
            actors.add(new Player(level.getPlayerTank(), new LibGdxKeyboardListener()));
        }
        actors.add(new AIAwesome(new NotRecommendingAI(), level));
    }

    @Override
    public void create() {
        initMap();
        initLevel();
        initColliders();
        initActors();
        initLevelRenderer();
    }

    private void cleanScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        for (var tank : level.getEnemyTanks()) {
            tank.update(deltaTime);
        }
        if (level.getPlayerTank() != null) {
            level.getPlayerTank().update(deltaTime);
        }
    }

    private void act() {
        for (var actor : actors) {
            actor.act();
        }
    }

    @Override
    public void render() {
        act();
        update();
        cleanScreen();
        levelRenderer.levelRender();
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
