package ru.mipt.bit.platformer.objects.tank;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.ColliderManager;
import ru.mipt.bit.platformer.input.directions.Directions;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.util.TileUtils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class LightDamagedTankTest {

    @Test
    void tryMove() {
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        ColliderManager colliderManager = new ColliderManager();
        Tank tank = new Tank(new GridPoint2(1, 0), 1.0f, colliderManager, tileUtils, new LightDamagedTank());
        colliderManager.addCollider(tank);
        tank.tryMove(Directions.UP.getDirection());
        assertAll(
                () -> {
                    tank.update(0.5f);
                    assertAll(
                            () -> assertNotNull(colliderManager.isTakenBy(new GridPoint2(1, 0))),
                            () -> assertNotNull(colliderManager.isTakenBy(new GridPoint2(1, 1)))
                    );
                },
                () -> {
                    tank.update(0.5f);
                    assertAll(
                            () -> assertNull(colliderManager.isTakenBy(new GridPoint2(1, 0))),
                            () -> assertNotNull(colliderManager.isTakenBy(new GridPoint2(1, 1)))
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
                    Tank tank = new Tank(new GridPoint2(1, 0), 1.0f, colliderManager, tileUtils, new LightDamagedTank());
                    tank.takeDamage(new Bullet(new GridPoint2(0, 0), Directions.UP.getDirection(), 0, 1.0f, colliderManager, tileUtils, null), null);
                    Field state = tank.getClass().getDeclaredField("tankState");
                    state.setAccessible(true);
                    assertTrue(state.get(tank) instanceof LightDamagedTank);
                },
                () -> {
                    Tank tank = new Tank(new GridPoint2(1, 0), 1.0f, colliderManager, tileUtils, new LightDamagedTank());
                    tank.takeDamage(new Bullet(new GridPoint2(0, 0), Directions.UP.getDirection(), 1, 1.0f, colliderManager, tileUtils, null), null);
                    Field state = tank.getClass().getDeclaredField("tankState");
                    state.setAccessible(true);
                    assertTrue(state.get(tank) instanceof MiddleDamagedTank);
                },
                () -> {
                    Tank tank = new Tank(new GridPoint2(1, 0), 1.0f, colliderManager, tileUtils, new LightDamagedTank());
                    tank.takeDamage(new Bullet(new GridPoint2(0, 0), Directions.UP.getDirection(), 2, 1.0f, colliderManager, tileUtils, null), null);
                    Field state = tank.getClass().getDeclaredField("tankState");
                    state.setAccessible(true);
                    assertTrue(state.get(tank) instanceof HeavyDamagedTank);
                }
        );
    }

    @Test
    void getHealthNormalized() {
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        ColliderManager colliderManager = new ColliderManager();
        Tank tank = new Tank(new GridPoint2(1, 0), 1.0f, colliderManager, tileUtils, new LightDamagedTank());
        assertEquals(1.0f, tank.getHealthNormalized(), 0.01f);
    }
}