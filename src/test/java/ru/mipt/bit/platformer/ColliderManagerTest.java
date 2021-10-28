package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.input.DIRECTIONS;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.TileUtils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ColliderManagerTest {

    @Test
    void colliderTankMovementTest() throws NoSuchFieldException {
        TileUtils tileUtils = new TileUtils(new GridPoint2(10, 10));
        ColliderManager colliderManager = new ColliderManager();
        colliderManager.addCollider(new Obstacle(new GridPoint2(2, 3), tileUtils));
        colliderManager.addCollider(new Obstacle(new GridPoint2(3, 2), tileUtils));
        Tank tank = new Tank(new GridPoint2(3, 3), 0.2f, colliderManager, tileUtils);
        Field gridPosition = tank.getClass().getDeclaredField("gridPosition");
        gridPosition.setAccessible(true);
        assertAll(
                () -> assertTrue(colliderManager.isFree(((GridPoint2) gridPosition.get(tank)).cpy().add(DIRECTIONS.UP.getDirection().getDeltaCoordinate()))),
                () -> assertTrue(colliderManager.isFree(((GridPoint2) gridPosition.get(tank)).cpy().add(DIRECTIONS.RIGHT.getDirection().getDeltaCoordinate()))),
                () -> assertFalse(colliderManager.isFree(((GridPoint2) gridPosition.get(tank)).cpy().add(DIRECTIONS.DOWN.getDirection().getDeltaCoordinate()))),
                () -> assertFalse(colliderManager.isFree(((GridPoint2) gridPosition.get(tank)).cpy().add(DIRECTIONS.LEFT.getDirection().getDeltaCoordinate())))
        );
    }
}
