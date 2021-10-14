package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.util.TileUtils;

import static org.junit.jupiter.api.Assertions.*;

class ColliderManagerTest {

    @Test
    void canMove() {
        TileUtils.setTileSize(new GridPoint2(10, 10));
        ColliderManager.addObstacle(new Obstacle(new GridPoint2(2, 3)));
        ColliderManager.addObstacle(new Obstacle(new GridPoint2(3, 2)));
        Player player = new Player(new GridPoint2(3, 3), 0.2f);
        assertTrue(ColliderManager.canMove(player, new GridPoint2(0, 1)));
        assertTrue(ColliderManager.canMove(player, new GridPoint2(1, 0)));
        assertFalse(ColliderManager.canMove(player, new GridPoint2(0, -1)));
        assertFalse(ColliderManager.canMove(player, new GridPoint2(-1, 0)));
    }
}
