package ru.mipt.bit.platformer.actors;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.ColliderManager;
import ru.mipt.bit.platformer.input.DIRECTIONS;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.TileUtils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class AIRandomTest {

    @Test
    void act() throws NoSuchFieldException, IllegalAccessException {
        TileUtils tileUtils = new TileUtils(new GridPoint2(10, 10));
        ColliderManager colliderManager = new ColliderManager();
        for (int i = 0; i < 100; i++) {
            Tank tank = new Tank(new GridPoint2(0, 0), 0.1f, colliderManager, tileUtils);
            AIRandom actor = new AIRandom(tank);
            actor.act();
            Field gridPosition = tank.getClass().getDeclaredField("gridPosition");
            gridPosition.setAccessible(true);
            assertTrue(
                    gridPosition.get(tank).equals(DIRECTIONS.UP.getDirection().getDeltaCoordinate())
                    || gridPosition.get(tank).equals(DIRECTIONS.DOWN.getDirection().getDeltaCoordinate())
                    || gridPosition.get(tank).equals(DIRECTIONS.LEFT.getDirection().getDeltaCoordinate())
                    || gridPosition.get(tank).equals(DIRECTIONS.RIGHT.getDirection().getDeltaCoordinate())
            );
        }
    }
}
