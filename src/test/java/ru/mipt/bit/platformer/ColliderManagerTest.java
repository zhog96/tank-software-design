package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.input.directions.Directions;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.tank.LightDamagedTank;
import ru.mipt.bit.platformer.objects.tank.Tank;
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
        Tank tank = new Tank(new GridPoint2(3, 3), 0.2f, tileUtils, new LightDamagedTank());
        tank.setColliderManager(colliderManager);
        Field gridPosition = tank.getClass().getDeclaredField("gridPosition");
        gridPosition.setAccessible(true);
        assertAll(
                () -> assertNull(colliderManager.isTakenBy(((GridPoint2) gridPosition.get(tank)).cpy().add(Directions.UP.getDirection().getDeltaCoordinate()))),
                () -> assertNull(colliderManager.isTakenBy(((GridPoint2) gridPosition.get(tank)).cpy().add(Directions.RIGHT.getDirection().getDeltaCoordinate()))),
                () -> assertNotNull(colliderManager.isTakenBy(((GridPoint2) gridPosition.get(tank)).cpy().add(Directions.DOWN.getDirection().getDeltaCoordinate()))),
                () -> assertNotNull(colliderManager.isTakenBy(((GridPoint2) gridPosition.get(tank)).cpy().add(Directions.LEFT.getDirection().getDeltaCoordinate())))
        );
    }
}
