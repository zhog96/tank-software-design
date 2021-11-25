package ru.mipt.bit.platformer.objects.tank;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.ColliderManager;
import ru.mipt.bit.platformer.input.directions.Directions;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.util.TileUtils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class MiddleTest {

    @Test
    void tryMove() {
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        ColliderManager colliderManager = new ColliderManager();
        Tank tank = new Tank(new GridPoint2(1, 0), 1.0f, colliderManager, tileUtils, new Middle());
        colliderManager.addCollider(tank);
        tank.tryMove(Directions.UP.getDirection());
        assertAll(
                () -> {
                    tank.update(1.0f);
                    assertAll(
                            () -> assertFalse(colliderManager.isFree(new GridPoint2(1, 0))),
                            () -> assertFalse(colliderManager.isFree(new GridPoint2(1, 1)))
                    );
                },
                () -> {
                    tank.update(1.0f);
                    assertAll(
                            () -> assertTrue(colliderManager.isFree(new GridPoint2(1, 0))),
                            () -> assertFalse(colliderManager.isFree(new GridPoint2(1, 1)))
                    );
                }
        );
    }

    @Test
    void takeDamage() {
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        ColliderManager colliderManager = new ColliderManager();
        assertAll(
                () -> {
                    Tank tank = new Tank(new GridPoint2(1, 0), 1.0f, colliderManager, tileUtils, new Middle());
                    tank.takeDamage(new Bullet(0));
                    Field state = tank.getClass().getDeclaredField("tankState");
                    state.setAccessible(true);
                    assertTrue(state.get(tank) instanceof Middle);
                },
                () -> {
                    Tank tank = new Tank(new GridPoint2(1, 0), 1.0f, colliderManager, tileUtils, new Middle());
                    tank.takeDamage(new Bullet(1));
                    Field state = tank.getClass().getDeclaredField("tankState");
                    state.setAccessible(true);
                    assertTrue(state.get(tank) instanceof Heavy);
                }
        );
    }

    @Test
    void getHealthNormalized() {
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        ColliderManager colliderManager = new ColliderManager();
        Tank tank = new Tank(new GridPoint2(1, 0), 1.0f, colliderManager, tileUtils, new Middle());
        assertEquals(2.0f / 3, tank.getHealthNormalized(), 0.01f);
    }
}