package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.ColliderManager;
import ru.mipt.bit.platformer.input.DIRECTIONS;
import ru.mipt.bit.platformer.util.TileUtils;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @Test
    void tankInMoveCoversToPoints() {
        TileUtils.setTileSize(new GridPoint2(1, 1));
        TileUtils.setInterpolation(Interpolation.smooth);
        ColliderManager colliderManager = new ColliderManager();
        Tank tank = new Tank(new GridPoint2(1, 0), 1.0f, colliderManager);
        colliderManager.addCollider(tank);
        tank.tryMove(DIRECTIONS.UP.getDirection());
        tank.update(0.5f);
        assertAll(
                () -> assertFalse(colliderManager.isFree(new GridPoint2(1, 0))),
                () -> assertFalse(colliderManager.isFree(new GridPoint2(1, 1)))
        );
    }
}