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

import ru.mipt.bit.platformer.controllers.AIRandom;
import ru.mipt.bit.platformer.controllers.CommandsCenter;
import ru.mipt.bit.platformer.controllers.Player;
import ru.mipt.bit.platformer.input.directions.libgdx.LibGdxDirectionsListener;
import ru.mipt.bit.platformer.input.shootlistener.libgdx.LibGdxShootListener;
import ru.mipt.bit.platformer.input.togglelistener.ToggleListener;
import ru.mipt.bit.platformer.input.togglelistener.libgdx.LibGdxToggleListener;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.level.generator.LevelRandomGenerator;
import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.renderer.LevelRenderer;
import ru.mipt.bit.platformer.renderer.libgdx.LibGdxMapGraphics;
import ru.mipt.bit.platformer.util.TileUtils;
import ru.mipt.bit.platformer.util.GdxUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private TiledMap levelBackground;
    private Level level;
    private LevelRenderer levelRenderer;
    private CommandsCenter commandsCenter;
    private TileUtils tileUtils;

    private void setupLevelRenderer() {
        batch = new SpriteBatch();
        ToggleListener toggleListener = new LibGdxToggleListener();
        levelRenderer = new LevelRenderer(new LibGdxMapGraphics(levelBackground, batch), batch, toggleListener);
        level.subscribe(levelRenderer);
    }

    private void initMap() {
        levelBackground = new TmxMapLoader().load("levelBackground.tmx");
        TiledMapTileLayer groundLayer = GdxUtils.getSingleLayer(levelBackground);
        tileUtils = new TileUtils(new GridPoint2(groundLayer.getTileWidth(), groundLayer.getTileHeight()));
    }

    private void initLevel() {
        level = new LevelRandomGenerator(8, 10, tileUtils).generate();
    }

    private void initControllers() {
        commandsCenter = new CommandsCenter();
        Tank playerTank = level.getPlayerTank();
        List<Tank> enemyTanks = level.getTanks().stream().filter(tank -> tank != playerTank).collect(Collectors.toList());
        commandsCenter.addController(new AIRandom(enemyTanks));
        commandsCenter.addController(new Player(playerTank, new LibGdxDirectionsListener(), new LibGdxShootListener()));
    }

    @Override
    public void create() {
        initMap();
        initLevel();
        setupLevelRenderer();
        initControllers();
    }

    private void cleanScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        commandsCenter.update(deltaTime);
        level.update(deltaTime);
    }

    @Override
    public void render() {
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
