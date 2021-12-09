package ru.mipt.bit.platformer.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.input.togglelistener.ToggleListener;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.tank.Tank;
import ru.mipt.bit.platformer.renderer.libgdx.LibGdxBulletGraphics;
import ru.mipt.bit.platformer.renderer.libgdx.LibGdxObstacleGraphics;
import ru.mipt.bit.platformer.renderer.libgdx.units.LibGdxGraphicsWithStats;
import ru.mipt.bit.platformer.renderer.libgdx.units.LibGdxTankGraphics;
import ru.mipt.bit.platformer.util.Observer;

import java.util.HashMap;
import java.util.Map;

/**
 * Infrastructure
 */

public class LevelRenderer implements Observer {
    private final Batch batch;
    private final Graphics mapGraphics;
    private final Map<Integer, Graphics> graphicsMap;
    private final ToggleListener toggleListener;

    public LevelRenderer(Graphics mapGraphics, Batch batch, ToggleListener toggleListener) {
        this.mapGraphics = mapGraphics;
        this.batch = batch;
        this.toggleListener = toggleListener;
        graphicsMap = new HashMap<>();
    }

    public void addGraphics(Graphics graphics, Object object) {
        graphicsMap.put(System.identityHashCode(object), graphics);
    }

    public void levelRender() {
        toggleListener.update();
        mapGraphics.render();
        batch.begin();
        for (var graphics : graphicsMap.values()) {
            graphics.render();
        }
        batch.end();
    }

    public void delete() {
        mapGraphics.delete();
        for (var graphics : graphicsMap.values()) {
            graphics.delete();
        }
    }

    @Override
    public void update(String message, Object context) {
        if (message.equals("add")) {
            if (context instanceof Bullet) {
                this.addGraphics(new LibGdxBulletGraphics((Bullet) context, batch), context);
            }
            if (context instanceof Tank) {
                this.addGraphics(
                        new LibGdxGraphicsWithStats(
                                new LibGdxTankGraphics((Tank) context, batch),
                                toggleListener,
                                batch,
                                ((Tank) context).getTileUtils()
                        ),
                        context
                );
            }
            if (context instanceof Obstacle) {
                this.addGraphics(new LibGdxObstacleGraphics((Obstacle) context, batch), context);
            }
        }
        if (message.equals("remove")) {
            this.graphicsMap.remove(System.identityHashCode(context));
        }
    }
}
