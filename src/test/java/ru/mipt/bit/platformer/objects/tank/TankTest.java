package ru.mipt.bit.platformer.objects.tank;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.ColliderManager;
import ru.mipt.bit.platformer.input.directions.Directions;
import ru.mipt.bit.platformer.util.TileUtils;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @Test
    void tankInMoveCoversToPoints() {
        TileUtils tileUtils = new TileUtils(new GridPoint2(1, 1));
        ColliderManager colliderManager = new ColliderManager();
        Tank tank = new Tank(new GridPoint2(1, 0), 1.0f, tileUtils, new LightDamagedTank());
        tank.setColliderManager(colliderManager);
        colliderManager.addCollider(tank);
        tank.tryMove(Directions.UP.getDirection());
        tank.update(0.5f);
        assertAll(
                () -> assertNotNull(colliderManager.isTakenBy(new GridPoint2(1, 0))),
                () -> assertNotNull(colliderManager.isTakenBy(new GridPoint2(1, 1)))
        );
    }
}
