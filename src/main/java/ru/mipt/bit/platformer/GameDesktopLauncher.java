package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import org.lwjgl.system.NonnullDefault;
import org.w3c.dom.Text;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.HashMap;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private TextureLoader textureLoader;

    private TiledMap level;
    private MapRenderer levelRenderer;

    private Player player;
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();

    @Override
    public void create() {
        batch = new SpriteBatch();
        textureLoader = new TextureLoader();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // load game objects
        player = new Player(groundLayer, textureLoader.load("images/tank_blue.png"), new GridPoint2(1, 1), 0f, tileMovement);
        gameObjects.add(player);
        gameObjects.add(new Obstacle(groundLayer, textureLoader.load("images/greenTree.png"), new GridPoint2(1, 3), 0f));
        gameObjects.add(new Obstacle(groundLayer, textureLoader.load("images/greenTree.png"), new GridPoint2(1, 5), 0f));
        gameObjects.add(new Obstacle(groundLayer, textureLoader.load("images/greenTree.png"), new GridPoint2(2, 3), 0f));
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();
        player.move(deltaTime, gameObjects);

        // render each tile of the level
        levelRenderer.render();
        // start recording all drawing commands
        batch.begin();
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(batch);
        }
        // submit all drawing requests
        batch.end();
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
        textureLoader.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}

final class TextureLoader implements Disposable {
    private final HashMap<String, Texture> textures = new HashMap<>();

    public Texture load(String path) {
        Texture texture = textures.get(path);
        if (texture == null) {
            texture = new Texture(path);
            textures.put(path, texture);
        }
        return texture;
    }

    @Override
    public void dispose() {
        textures.forEach((path, texture) -> texture.dispose());
    }
}

class GameObject {
    protected  GridPoint2 coordinates;
    private final TextureRegion region;
    private final Rectangle bounding;
    protected float rotation;

    public GameObject(TiledMapTileLayer groundLayer, Texture texture, GridPoint2 coordinates, float rotation) {
        this.coordinates = coordinates;
        this.region = new TextureRegion(texture);
        this.bounding = createBoundingRectangle(region);
        this.rotation = rotation;
        moveRectangleAtTileCenter(groundLayer, bounding, coordinates);
    }

    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, region, bounding, rotation);
    }

    public Rectangle getBounding() {
        return this.bounding;
    }

}

class Obstacle extends GameObject {

    public Obstacle(TiledMapTileLayer groundLayer, Texture texture, GridPoint2 coordinates, float rotation) {
        super(groundLayer, texture, coordinates, rotation);
    }
}

class Player extends GameObject {
    private static final float MOVEMENT_SPEED = 0.4f;

    private final TileMovement tileMovement;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;

    public Player(TiledMapTileLayer groundLayer, Texture texture, GridPoint2 coordinates, float rotation, TileMovement tileMovement) {
        super(groundLayer, texture, coordinates, rotation);
        this.tileMovement = tileMovement;
        this.destinationCoordinates = new GridPoint2(coordinates);
    }

    private void checkAndMove(GridPoint2 estimatedCoordinates, float rotation, ArrayList<GameObject> gameObjects) {
        if (isEqual(movementProgress, 1f)) {
            this.rotation = rotation;
            // check potential player destination for collision with obstacles
            for (GameObject anotherObject : gameObjects) {
                if (!(anotherObject instanceof Obstacle) || this.equals(anotherObject)) {
                    continue;
                }
                if (anotherObject.coordinates.equals(estimatedCoordinates)) {
                    return;
                }
            }
            destinationCoordinates = estimatedCoordinates;
            movementProgress = 0f;
        }
    }

    public void move(float deltaTime, ArrayList<GameObject> gameObjects) {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            checkAndMove(incrementedY(coordinates), 90f, gameObjects);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            checkAndMove(decrementedX(coordinates), -180f, gameObjects);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            checkAndMove(decrementedY(coordinates), -90f, gameObjects);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            checkAndMove(incrementedX(coordinates), 0f, gameObjects);
        }

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(this.getBounding(), coordinates, destinationCoordinates, movementProgress);

        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(movementProgress, 1f)) {
            // record that the player has reached his/her destination
            coordinates.set(destinationCoordinates);
        }
    }
}
