package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.ColliderManager;
import ru.mipt.bit.platformer.input.DIRECTIONS;
import ru.mipt.bit.platformer.util.TileUtils;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @Test
    void tankInMoveCoversToPoints() {
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        ColliderManager colliderManager = new ColliderManager();
        Tank tank = new Tank(new GridPoint2(1, 0), 1.0f, colliderManager, tileUtils);
        colliderManager.addCollider(tank);
        tank.tryMove(DIRECTIONS.UP.getDirection());
        tank.update(0.5f);
        assertAll(
                () -> assertFalse(colliderManager.isFree(new GridPoint2(1, 0))),
                () -> assertFalse(colliderManager.isFree(new GridPoint2(1, 1)))
        );
    }
}
